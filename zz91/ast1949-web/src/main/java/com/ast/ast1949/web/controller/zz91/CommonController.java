/**
 * 
 */
package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.service.download.DownloadInfoService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.thread.PdfToSwfThread;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class CommonController extends BaseController {
	
	@Resource
	private CategoryService categoryService;
	
	@RequestMapping
	public ModelAndView categoryTreeNode(HttpServletRequest request, Map<String, Object> out, 
			String parentCode) throws IOException{

		List<ExtTreeDto> category=categoryService.child(parentCode);
		
		return printJson(category, out);
	}
	
	@RequestMapping
	public ModelAndView categoryCombo(String preCode, Map<String, Object> out) throws IOException {
//		List<CategoryDO> list=new ArrayList<CategoryDO>();
//		CategoryDO category=new CategoryDO();
//		category.setLabel("--请选择--");
//		list.add(category);
		List<CategoryDO> childList=categoryService.queryCategoriesByPreCode(preCode);
//		list.addAll(childList);

		return printJson(childList,out);
	}
	
	@RequestMapping
	public ModelAndView doUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	
	/**
	 * 下载中心上传文件
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doXiazaiDocUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.DOWNLOAD_URL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename,"uploadfile", MvcUpload.WHITE_DOC, MvcUpload.BLOCK_ANY, 5*1024);
			result.setSuccess(true);
			String fileUrl = path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname;
			result.setData(fileUrl);
			// 上传pdf成功 自动转换
			PdfToSwfThread.addfile(fileUrl);
			// 读取pdf文字
			PDDocument document = PDDocument.load(DownloadInfoService.DATA_URL+fileUrl);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setEndPage(1);
			String text = stripper.getText(document);
			text = Jsoup.clean(text, Whitelist.none());
			String[] content = text.split("市场行情简述");
			if(content.length==2&&StringUtils.isNotEmpty(content[1])){
				result.setData(result.getData()+"|"+content[1]);
			}
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	
	/**
	 * 下载中心上传图片
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doXiazaiPicUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.DOWNLOAD_URL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	
}
