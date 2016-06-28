package com.ast.ast1949.xiazai.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.analysis.AnalysisXiaZaiKeywordsService;
import com.ast.ast1949.service.download.DownloadInfoService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class XiazaiController extends BaseController {

	@Resource
	private DownloadInfoService downloadInfoService;
	@Resource
	private PriceService priceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private AnalysisXiaZaiKeywordsService  analysisXiaZaiKeywordsService;

	@RequestMapping
	public void index(Map<String, Object> out, PageDto<DownloadInfo> page,
			DownloadInfo downloadInfo) {
		// 所有文档数
		out.put("totalFile", downloadInfoService.countAllFile());
		
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("id");
		}

		// 首页封面9条数据
		page.setPageSize(9);
		out
				.put("newestList", downloadInfoService.queryList(downloadInfo,
						page));

		page.setPageSize(6);
		downloadInfo.setCode("200510001000");
		out.put("zcList", downloadInfoService.queryList(downloadInfo, page));

		downloadInfo.setCode("200510001001");
		out.put("wbList", downloadInfoService.queryList(downloadInfo, page));

		downloadInfo.setCode("200510001002");
		out.put("zbList", downloadInfoService.queryList(downloadInfo, page));

		// 最新报价
		PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
		pagePrice.setPageSize(4);
		out.put("priceList", priceService.pagePriceBySearchEngine("",
				new PriceDO(), pagePrice).getRecords());

		// 最新供求
		PageDto<ProductsDto> pageProduct = new PageDto<ProductsDto>();
		pageProduct.setPageSize(4);
		out.put("productList", productsService.pageLHProductsBySearchEngine(
				new ProductsDO(), null, null, pageProduct).getRecords());

		SeoUtil.getInstance().buildSeo(out);
		
		// 所有文档数
		out.put("totalFile", downloadInfoService.countAllFile());
		
	}

	@RequestMapping
	public void list(Map<String, Object> out, DownloadInfo downloadInfo,
			PageDto<DownloadInfo> page , String kw) throws UnsupportedEncodingException {
		// 所有文档数
		out.put("totalFile", downloadInfoService.countAllFile());
		
		String seoKey = "最新文档下载";

		// 最新文档
		PageDto<DownloadInfo> newPage = new PageDto<DownloadInfo>();
		newPage.setPageSize(10);
		newPage.setSort("gmt_created");
		newPage.setDir("desc");
		out.put("newestList", downloadInfoService.queryList(null, newPage));

		// 最热文档
		PageDto<DownloadInfo> hotPage = new PageDto<DownloadInfo>();
		hotPage.setPageSize(6);
		hotPage.setSort("download_count");
		out.put("hotList", downloadInfoService.queryList(null, newPage));

		// 最新报价
		PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
		pagePrice.setPageSize(6);
		out.put("priceList", priceService.pagePriceBySearchEngine("",
				new PriceDO(), pagePrice).getRecords());

		// 最新供求
		PageDto<ProductsDto> pageProduct = new PageDto<ProductsDto>();
		pageProduct.setPageSize(4);
		out.put("productList", productsService.pageLHProductsBySearchEngine(
				new ProductsDO(), null, null, pageProduct).getRecords());
		
		//关键字搜索
		if (StringUtils.isNotEmpty(kw)) {
		    Date start;
	        Date end;
		    List<AnalysisXiaZaiKeywords> list;
		    int num = 1;
            try {
                start = DateUtil.getDate(new Date(), "yyyy-MM-dd");
                end = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), 1);
                list = analysisXiaZaiKeywordsService.queryKeywords(kw, start, end);
                if (list.size() != 0) {
                    AnalysisXiaZaiKeywords analysisXiaZaiKeywords = list.get(0);
                    num = analysisXiaZaiKeywords.getNum() + 1;
                    analysisXiaZaiKeywords.setNum(num);
                    analysisXiaZaiKeywordsService.updateKeywordOfNum(analysisXiaZaiKeywords);
                } else {
                    analysisXiaZaiKeywordsService.insertKeyword(kw, num);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
		
		if (StringUtils.isNotEmpty(downloadInfo.getTitle())) {
			seoKey = downloadInfo.getTitle();
			out.put("titleEncode", URLEncoder.encode(downloadInfo.getTitle(),
					HttpUtils.CHARSET_UTF8));
		}

		if (StringUtils.isNotEmpty(downloadInfo.getCode())) {
			seoKey = CategoryFacade.getInstance().getValue(
					downloadInfo.getCode());
			out.put("code", downloadInfo.getCode());
		}

		out.put("page", downloadInfoService.pageList(downloadInfo, page));
		out.put("label", seoKey);

		// seo
		SeoUtil.getInstance().buildSeo("list", new String[] { seoKey },
				new String[] { seoKey }, new String[] { seoKey }, out);
		
	}

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out, Integer id)
			throws HttpException, IOException {
		// 所有文档数
		out.put("totalFile", downloadInfoService.countAllFile());
		// 浏览次数 + 1
		downloadInfoService.updateViewCountByClick(id);
		do {
			if (id == null) {
				break;
			}
			DownloadInfo obj = downloadInfoService.queryById(id);
			if (obj == null) {
				break;
			}
			obj.setLabel(CategoryFacade.getInstance().getValue(obj.getCode()));
			out.put("downloadInfo", obj);

			// 相关文件推荐
			DownloadInfo downloadInfo = new DownloadInfo();
			PageDto<DownloadInfo> page = new PageDto<DownloadInfo>();
			page.setPageSize(8);
			page.setSort("gmt_created");
			page.setDir("desc");
			downloadInfo.setCode(obj.getCode());
			out.put("similarList", downloadInfoService.queryList(downloadInfo,page));

			// 最新报价
			PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
			pagePrice.setPageSize(6);
			out.put("priceList", priceService.pagePriceBySearchEngine("",
					new PriceDO(), pagePrice).getRecords());

			// 最新供求
			PageDto<ProductsDto> pageProduct = new PageDto<ProductsDto>();
			pageProduct.setPageSize(4);
			out.put("productList", productsService
					.pageLHProductsBySearchEngine(new ProductsDO(), null, null,
							pageProduct).getRecords());

			// 获取文件 pdf 文件地址
			String fileUrl = "http://img1.zz91.com"
					+ obj.getFileUrl().replaceAll(".pdf", "") + "/";
			// String fileUrl =
			// "http://img1.zz91.com/xiazai/2013/6/21/c1743299-5566-425b-90e2-4affdfac8cd2/";
			out.put("fileUrl", fileUrl);
			int i = 1;
			do {
				String url = HttpUtils.getInstance().httpGet(
						fileUrl + i + ".swf", HttpUtils.CHARSET_UTF8);
				if (StringUtils.isEmpty(url)) {
					out.put("size", i-1);
					break;
				}
				i++;
			} while (true);
			SeoUtil.getInstance().buildSeo("detail",
					new String[] { obj.getTitle() }, null, null, out);
			return new ModelAndView();
		} while (false);
		return new ModelAndView("index");
	}

	@RequestMapping
	public void downloadFile(String fileUrl, HttpServletResponse response)
			throws IOException {
		do {

			DownloadInfo obj = downloadInfoService.queryByFileUrl(fileUrl);
			if(obj==null){
				break;
			}
			// 下载次数 +1
			downloadInfoService.updateDownloadCountByClick(obj.getId());

			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("application/pdf");
			// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ new Date().getTime() + ".pdf");
			ServletOutputStream out;
			// 通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
			File file = new File("/usr/data/resources" + fileUrl);

			try {
				FileInputStream inputStream = new FileInputStream(file);

				// 3.通过response获取ServletOutputStream对象(out)
				out = response.getOutputStream();

				int b = 0;
				byte[] buffer = new byte[512];
				while (b != -1) {
					b = inputStream.read(buffer);
					// 4.写到输出流(out)中
					out.write(buffer, 0, b);
				}
				inputStream.close();
				out.close();
				out.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (false);
	}

}
