package com.zz91.ep.admin.controller.news;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.common.HideInfoService;
import com.zz91.ep.admin.service.common.VideoService;
import com.zz91.ep.admin.service.news.NewsCategoryService;
import com.zz91.ep.admin.service.news.NewsService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.domain.common.HideInfo;
import com.zz91.ep.domain.common.Video;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

/**
 * 新闻咨询管理controller
 * 
 * @author Leon 2011.10.20
 * 
 */
@Controller
public class NewsController extends BaseController {

	public static final String[] WHITE_DOC = { ".doc", ".docx", ".xls", ".pdf",
			".jpg", ".jpeg", ".gif", ".bmp", ".png" };
	public static final String[] BLOCK_ANY = { ".bat", ".sh", ".exe" };
	final static String URL_FOAMAT = "http://www.huanbao.com/news/details";
	final static String URL_FIX = ".htm";
	
	@Resource
	private NewsService newsService;
	@Resource
	private PhotoService photoService;
	@Resource
	private ParamService paramService;
	@Resource
	private VideoService videoService;
	@Resource
	private HideInfoService hideInfoService;
	@Resource
	private NewsCategoryService newsCategoryService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		Map<String, String> map = new HashMap<String, String>();
		List<Param> params = paramService
				.queryParamByType("news_recommend_type");
		for (Param param : params) {
			map.put(param.getKey(), param.getValue());
		}
		out.put("recommendType", map);
		return null;
	}

	@RequestMapping
	public ModelAndView details(HttpServletRequest request,
			Map<String, Object> out, Integer id, String code,String copy) {
		out.put("id", id);
		out.put("code", code);
		out.put("copy", copy);
		List<Param> list = paramService.queryParamByType("news_source_type");
		String strMap = "";
		for(Param obj:list){
			if(StringUtils.isEmpty(strMap)){
				strMap = obj.getName()+":"+obj.getName();
			}else{
				strMap += ","+obj.getName()+":"+obj.getName();
			}
		}
		out.put("strMap",strMap);
		return null;
	}

	/**
	 * 新闻列表分页
	 */
	@RequestMapping
	public ModelAndView queryNews(HttpServletRequest request,
			Map<String, Object> out, String categoryCode, String title,
			Integer pauseStatus, String recommendCode, PageDto<NewsDto> page) {
		if (page.getSort() == null) {
			page.setSort("gmt_publish");
			page.setDir("desc");
		}
		page = newsService.pageNewsByAdmin(categoryCode, title, pauseStatus,
				recommendCode, page);
		for (NewsDto obj : page.getRecords()) {
			HideInfo hieInfo = hideInfoService.queryHideInfoByIdAndType(obj
					.getNews().getId(), "0");
			if (hieInfo != null) {
				obj.getNews().setTitle("(已隐藏)" + obj.getNews().getTitle());
			}
		}

		return printJson(page, out);
	}

	/**
	 * 创建新闻
	 */
	@RequestMapping
	public ModelAndView createNews(HttpServletRequest request,
			Map<String, Object> out, News news, String videoUrl, Integer id,String photoCover,Integer flag)
			throws IOException {
		ExtResult result = new ExtResult();
		
		//flag用于是否是第一次添加资讯的的标记
			
			Integer countValue = newsService.countByTitle(news.getTitle());
	
			if (countValue != 0) {
				result.setSuccess(false);
			} else {
				Integer i = newsService.createNewsByAdmin(news, getCachedUser(
						request).getAccount());
				if (i != null && i.intValue() > 0) {
					videoService.insert(i, VideoService.TYPE_NEWS, videoUrl,photoCover);
					result.setSuccess(true);
					result.setData(i);
					news.setId(i);
					// 绑定新上传图片
					photoService.updateNewsPhotoByTargetId(i);
				}
			} 
		return printJson(result, out);
	}

	/**
	 * 更新新闻资讯
	 * 
	 * @param request
	 * @param news
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateNews(HttpServletRequest request, News news,
			Map<String, Object> out, String videoUrl,String photoCover) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = newsService.updateNews(news);
		if (i != null && i.intValue() > 0) {
			Video video = videoService.queryByTypeAndId(news.getId(),
					VideoService.TYPE_NEWS);
			if (video != null) {
				video.setContent(videoUrl);
				video.setPhotoCover(photoCover);
				videoService.update(video);
			} else {
				videoService.insert(news.getId(), VideoService.TYPE_NEWS,
						videoUrl,photoCover);
			}
			result.setSuccess(true);
			result.setData(news.getId());
		}
		return printJson(result, out);
	}

	/**
	 * 上传文档(供下载)
	 * 
	 * @param out
	 * @param request
	 * @param targetId
	 * @return
	 */
	@RequestMapping
	public ModelAndView uploadFile(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		String filename = String.valueOf(System.currentTimeMillis());
		String path = buildPath("news");
		String uploadedFile = null;
		try {
			uploadedFile = MvcUpload.localUpload(request, UPLOAD_ROOT + path,
					filename, DEFAULT_FIELD, WHITE_DOC, BLOCK_ANY, 20 * 1024);// 最大上传20M
		} catch (Exception e) {
			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
		}
		if (StringUtils.isNotEmpty(uploadedFile)) {
			Photo photo = new Photo();
			photo.setUid(0);
			photo.setCid(0);
			photo.setPhotoAlbumId(0);
			photo.setPath(path + "/" + uploadedFile);
			photo.setTargetId(id);
			photo.setTargetType("news");
			Integer i = photoService.createPhoto(photo);
			if (i != null && i.intValue() > 0) {
				out.put("success", true);
				out.put("data", path + "/" + uploadedFile);
			}
		}
		return printJson(out, out);
	}

	/**
	 * 资讯上传文件查询
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryPhotoList(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		List<Photo> list = photoService.queryPhotoByTargetType("news", id);
		return printJson(list, out);
	}

	/**
	 * 删除上传文件
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param path
	 * @return
	 */
	@RequestMapping
	public ModelAndView deleteUploadFile(HttpServletRequest request,
			Map<String, Object> out, Integer id, String path) {
		ExtResult result = new ExtResult();
		Integer i = photoService.deletePhotoById(id);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 删除新闻资讯
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView deleteNews(HttpServletRequest request, Integer id,
			Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = newsService.deleteNewsById(id);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updatePauseStatus(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer pauseStatus) {

		Integer i = newsService.updateStatusOfNews(id, pauseStatus);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		// LogUtil.getInstance().log(1000, getCachedUser(request).getAccount(),
		// i, null, "{'pauseStatus':"+pauseStatus+"}");
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryOneNews(HttpServletRequest request,
			Map<String, Object> out, Integer id, String code,String copy) {

		List<NewsDto> list = new ArrayList<NewsDto>();

		if (id != null) {
			News news = newsService.queryNewDetailsById(id);
			NewsDto dto = new NewsDto();
			if (news != null) {
				dto
						.setCategoryName(CodeCachedUtil.getValue(
								CodeCachedUtil.CACHE_TYPE_NEWS, news
										.getCategoryCode()));
				Video video = videoService.queryByTypeAndId(id,
						VideoService.TYPE_NEWS);
				 if(video!=null){
					dto.setVideoUrl(video.getContent());
					dto.setPhotoCover(video.getPhotoCover());
				 }
			}
			// 拷贝资讯
			if("1".equals(copy)){
				news.setId(0);
			}
			dto.setNews(news);
			list.add(dto);
		} else if (StringUtils.isNotEmpty(code)) {
			NewsDto dto = new NewsDto();
			News news = new News();
			news.setPauseStatus(1);
			news.setCategoryCode(code);
			dto.setNews(news);
			dto.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, code));
			list.add(dto);
		}
		return printJson(list, out);
	}

	/**
	 * 推荐新闻
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping
	public ModelAndView recommendNews(HttpServletRequest request,
			Map<String, Object> out, Integer id, String type) {
		Integer i = newsService.updateNewsRecommend(id, type);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 取消推荐
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelRecommendNews(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer rid) {
		Integer i = newsService.cancelRecommendNews(id, rid);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 隐藏资讯
	 * 
	 * @param request
	 * @param id
	 * @param tagertType
	 * @return
	 */
	@RequestMapping
	public ModelAndView inserthideInfo(HttpServletRequest request,
			Map<String, Object> out, Integer id, String targetType) {
		HideInfo hideInfo = new HideInfo();
		hideInfo.setTargetId(id);
		hideInfo.setTargetType(targetType);
		Integer i = hideInfoService.insert(hideInfo);
		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 显示资讯
	 * 
	 * @param request
	 * @param id
	 * @param tagertType
	 * @return
	 */
	@RequestMapping
	public ModelAndView deleteHideInfo(HttpServletRequest request,
			Map<String, Object> out, Integer id, String targetType) {

		Integer i = hideInfoService.delete(id, targetType);

		ExtResult result = new ExtResult();
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 报价导出为excel
	 * @param request
	 * @param response
	 * @param from
	 * @param to
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	@RequestMapping
	public ModelAndView exportData(HttpServletRequest request,HttpServletResponse response,String from ,String to ) throws IOException, RowsExceededException, WriteException{
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		// 检索所有list
		List<News> list = newsService.queryListByFromTo(from, to);
		
		
		ws.addCell(new Label(0,0,"标题"));
		ws.addCell(new Label(1,0,"网址"));
		ws.addCell(new Label(2,0,"主类别"));
		ws.addCell(new Label(3,0,"流量"));
		int i=1;
		for(News obj:list){
			ws.addCell(new Label(0,i,obj.getTitle()));
			ws.addCell(new Label(1,i,URL_FOAMAT+obj.getId()+URL_FIX));
			ws.addCell(new Label(2,i,newsCategoryService.queryNewsCategoryByCode(obj.getCategoryCode()).getName()));
			ws.addCell(new Label(3,i,""+obj.getViewCount()));
			i++;
		}
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容   
//		ws.addCell(labelCF);//将Label写入sheet中   
		//Label的构造函数Label(int x, int y,String aString)  xy意同读的时候的xy,aString是写入的内容.   
//		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);//设置写入字体   
//		WritableCellFormat wcfF = new WritableCellFormat(wf);//设置CellFormat   
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置,内容和格式   
		//Label的另一构造函数Label(int c, int r, String cont, CellFormat st)可以对写入内容进行格式化,设置字体及其它的属性.   
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}

}
