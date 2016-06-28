package com.ast.ast1949.spot.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.spot.SpotDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.ast.ast1949.service.spot.SpotAuctionLogService;
import com.ast.ast1949.service.spot.SpotAuctionService;
import com.ast.ast1949.service.spot.SpotInfoService;
import com.ast.ast1949.service.spot.SpotPromotionsService;
import com.ast.ast1949.service.spot.SpotService;
import com.ast.ast1949.service.spot.SpotTrustService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class SpotController extends BaseController{
	@Resource
	private SpotPromotionsService spotPromotionsService;
	@Resource
	private SpotAuctionService spotAuctionService;
	@Resource
	private ProductsSpotService productsSpotService;
	@Resource
	private SpotService spotService;
	@Resource
	private ProductsService productsService;
	@Resource
	private SpotInfoService spotInfoService;
	@Resource
	private CreditFileService creditFileService; 
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private SpotAuctionLogService spotAuctionLogService;
	@Resource
	private SpotTrustService spotTrustService;
	
	@RequestMapping
	public ModelAndView index(Map<String,Object> out,PageDto<ProductsDto> page) throws ParseException{
		// 伪造的前台数据
		// 三条促销
		
		//productsSpotService.buildBaseData(out);
		
		
		// 三条促销
	     // Long proStartTime=new Date().getTime();
	   out.put("promotionList", spotPromotionsService.queryPromotionsBySize(3));
	    //  Long proEndTime=new Date().getTime()-proStartTime;
	    //  out.put("proEndTime", proEndTime);
		// 三条竞拍
	     // Long aucStartTime=new Date().getTime();
		out.put("auctionList", spotAuctionService.queryAuctionBySize(3));
		 //Long aucEndTime=new Date().getTime()-aucStartTime;
	    //  out.put("aucEndTime",aucEndTime);
		
	   //  Long zjhStartTime=new Date().getTime();
		 page.setPageSize(3);
		// 1f江浙沪等地区 推荐信息以及最新三条供求
		 out.put("jzhTJ", spotService.getAreaSpotByDataIndex("101010011000", 6));
		// Long zjEndTime=new Date().getTime()-zjhStartTime;
	   //  out.put("zjEndTime",zjEndTime);
	     
	     //Long zjhtStartTime=new Date().getTime();
		out.put("jzhTrade", productsService.pageProductsBySearchEngine(new ProductsDO(), "浙江|江苏|上海", null, page).getRecords());
		// Long zjtEndTime=new Date().getTime()-zjhtStartTime;
	    // out.put("zjtEndTime",zjtEndTime);
	     
	    // Long gdsStartTime=new Date().getTime();
		// 2f广东山东
		out.put("gdsdTJ", spotService.getAreaSpotByDataIndex("101010011001", 6));
		// Long gdEndTime=new Date().getTime()-gdsStartTime;
	   //  out.put("gdEndTime",gdEndTime);
	   //  Long gdstStartTime=new Date().getTime();
		out.put("gdsdTrade", productsService.pageProductsBySearchEngine(new ProductsDO(), "广东|山东", null, page).getRecords());
	// Long gdtEndTime=new Date().getTime()-gdstStartTime;
	  //   out.put("gdtEndTime",gdtEndTime);
	     
	 
	   //  Long jjtStartTime=new Date().getTime();
		// 3f京津唐
		out.put("jjtTJ", spotService.getAreaSpotByDataIndex("101010011002", 6));
		// Long jjtEndTime=new Date().getTime()-jjtStartTime;
	   //  out.put("jjtEndTime",jjtEndTime);
	   //  Long jjttStartTime=new Date().getTime();
		out.put("jjtTrade", productsService.pageProductsBySearchEngine(new ProductsDO(), "北京|天津|唐山", null, page).getRecords());
	//	 Long jjttEndTime=new Date().getTime()-jjttStartTime;
	  //   out.put("jjttEndTime",jjttEndTime);
	   
		
	  //   Long qtStartTime=new Date().getTime();
		// 4f其他地区
		out.put("qtTJ", spotService.getAreaSpotByDataIndex("101010011003", 6));
	//	 Long qtEndTime=new Date().getTime()-qtStartTime;
	  //   out.put("qtEndTime",qtEndTime);
	 //    Long qttStartTime=new Date().getTime();
		// 目前采用穷举法
		out.put("qtTrade",
				productsService
				.pageProductsBySearchEngine(
				new ProductsDO(),
				"河北|山西|内蒙古|辽宁|吉林|黑龙江|上海|安徽|福建|江西|河南|湖北|湖南|广西|海南|重庆|四川|贵州|云南|西藏|陕西|甘肃|青海|宁夏|新疆|台湾|香港|澳门",
				null, page).getRecords());
		
	//	 Long qttEndTime=new Date().getTime()-qttStartTime;
	  //   out.put("qttEndTime",qttEndTime);
		// seo
		SeoUtil.getInstance().buildSeo(out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView cxList(Map<String, Object>out){
		// 最新促销 9条
		out.put("list", spotPromotionsService.queryPromotionsBySize(9));
		// 过期促销 6条
		out.put("expiredList", spotPromotionsService.queryExpiredPromotionsBySize(6));
		//seo
		SeoUtil.getInstance().buildSeo("cx", out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView jpList(Map<String, Object>out){
		// 最新促销 9条
		out.put("list", spotAuctionService.queryAuctionBySize(9));
		// 过期促销 6条
		out.put("expiredList", spotAuctionService.queryExpiredAuctionBySize(6));
		//seo
		SeoUtil.getInstance().buildSeo("jp", out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,Integer spotId) throws IOException{
		SpotDto dto = new SpotDto();
		ProductsSpot ps = new ProductsSpot();
		ProductsDO productsDO = new ProductsDO();
		String title ="";
		String keywords = "";
		String description ="";
		do{
			ps = productsSpotService.queryById(spotId);
			if(spotId ==null||ps==null){
				return new ModelAndView("redirect:index.htm");
			}
			productsDO = productsService.queryProductsById(ps.getProductId());
			if(productsDO==null){
				return new ModelAndView("redirect:index.htm");
			}
			String seoPtype = CategoryFacade.getInstance().getValue(productsDO.getProductsTypeCode());
			String seoPtitle = productsDO.getTitle();
			String seoPtag = productsDO.getTags();
			
			// 普通现货tkd
			title =seoPtype+seoPtitle + "行情 报价 价格 评价";
			keywords =seoPtype + seoPtag + "报价";
			String detail = Jsoup.clean(productsDO.getDetails(), Whitelist.none());
			if(detail.length()>72){
				detail = detail.substring(0, 72);
			}
			description = seoPtype + detail;
			Date date = new Date();
			// 检查是否促销
			SpotPromotions sp = spotPromotionsService.queryOnePromotions(spotId);
			if(sp!=null){
				dto.setSpotPromotions(sp);
				if(sp.getExpiredTime().getTime()-date.getTime()<0){
					out.put("isExpired", true);
				}
				out.put("detailType", "cx");
				title =seoPtype+seoPtitle + "-促销区";
				keywords =seoPtype + seoPtag + "促销价,促销,促销区,现货商城";
				description = "商城促销区为您提供"+seoPtitle+"促销服务。要促销，就到现货促销区！";
				break;
			}
			// 是否竞拍
			SpotAuction sa = spotAuctionService.queryBySpotId(spotId);
			if(sa!=null){
				dto.setSpotAuction(sa);
				if(sa.getExpiredTime().getTime()-date.getTime()<0){
					out.put("isExpired", true);
				}
				out.put("detailType", "jp");
				out.put("auctionLogCount", spotAuctionLogService.queryCountByAuctionId(sa.getId()));
				out.put("auctionLogList", spotAuctionLogService.queryByAuctionIdAndSize(sa.getId(), 2));
				title =seoPtype+seoPtitle + "-竞拍区";
				keywords =seoPtype + seoPtag + "竞拍价,竞拍,竞拍区,现货商城";
				description = "商城竞拍区为您提供"+seoPtitle+"竞拍服务。要竞拍，就到现货竞拍区！";
				break;
			}
		}while(false);
		// 现货信息
		dto.setProductsSpot(ps);
		// 现货详细信息
		dto.setSpotInfo(spotInfoService.queryOneSpotInfo(spotId));
		// 数据装载
		out.put("dto", dto);
		//同类产品推荐
		productsDO.setManufacture(CategoryFacade.getInstance().getValue(productsDO.getManufacture()));
		dto.setProduct(productsDO);
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		page.setPageSize(3);
		ProductsDO obj = new ProductsDO();
		obj.setTitle(CategoryProductsFacade.getInstance().getValue(productsDO.getCategoryProductsMainCode()));
		out.put("similarList", spotService.pageSpotBySearchEngine(obj, null, null, page).getRecords());
		// 企业资质
		out.put("creditFileList", creditFileService.queryFileByCompany(productsDO.getCompanyId()));
		// 公司信息
		out.put("companyDto", companyService.queryCompanyDetailById(productsDO.getCompanyId()));
		// 供求图片 list
		out.put("picList", productsPicService.queryProductPicInfoByProductsId(productsDO.getId()));
		// seo
		SeoUtil.getInstance().buildSeo("detail", new String[]{title}, new String[]{keywords}, new String[]{description}, out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView spot(Map<String, Object>out,PageDto<ProductsDto> page,ProductsDO product) throws UnsupportedEncodingException{
		String keywords= "ABS,PP,PET,PC,PE,PA,PMMA,EVA,EPS,PVC,HDPE,LDPE";
		// 求购
		PageDto<ProductsDto> pageBuyList = new PageDto<ProductsDto>();
		pageBuyList.setPageSize(8);
		ProductsDO proDO = new ProductsDO();
		proDO.setProductsTypeCode(ProductsService.PRODUCTS_TYPE_BUY);
		out.put("buyList", productsService.pageProductsBySearchEngine(proDO,null, null, pageBuyList).getRecords());
		
		// 列表
		if(page.getStartIndex()<=0){
			// 第一页
			List<ProductsDto> list = new ArrayList<ProductsDto>();
			list.addAll(productsService.queryProductsForSpotByCondition("1",null,null,1));
			list.addAll(productsService.queryProductsForSpotByCondition(null,"1",null,1));
			list.addAll(productsService.queryProductsForSpotByCondition(null,null,"1",2));
			out.put("speList", list);
		}
		// 中文转码
		if(StringUtils.isNotEmpty(product.getTitle())){
			product.setTitle(StringUtils.decryptUrlParameter(product.getTitle())); // 关键字
			keywords = product.getTitle();
		}
		if(StringUtils.isNotEmpty(product.getLocation())){
			product.setLocation(StringUtils.decryptUrlParameter(product.getLocation())); // 地区
		}
		page.setPageSize(10);
		out.put("page", spotService.pageSpotBySearchEngine(product, product.getLocation(), null, page));
		out.put("product", product);
		
		// seo
		SeoUtil.getInstance().buildSeo("spot",new String[]{keywords},new String[]{keywords},new String[]{keywords}, out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}
	
	@RequestMapping
	public ModelAndView success(Map<String, Object>out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView help(Map<String, Object>out){
		//seo 
		SeoUtil.getInstance().buildSeo("help", out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView order(Map<String, Object>out){

		// 委托找货列表
		out.put("list",spotTrustService.queryListForFront(0,10));

		// seo tkd
		SeoUtil.getInstance().buildSeo("order", out);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView moreOrder(Map<String, Object>out,Integer page){
		do {
			// 委托找货列表
			if(page==null){
				break;
			}
			page = page/12;
			Integer start = 24*page +10;
			out.put("list",spotTrustService.queryListForFront(start,24));
		} while (false);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView doTrust(SpotTrust spotTrust){
		spotTrust.setIsChecked(SpotTrustService.CHECK_WAIT);
		spotTrustService.insert(spotTrust);
		return new ModelAndView("redirect:order.htm");
	}
	
	@RequestMapping
	public ModelAndView orderAd(Map<String, Object>out){
		out.put("list",spotTrustService.queryListForFront(0,8));
		SeoUtil.getInstance().buildSeo("orderAd", out);
		return new ModelAndView();
	}
	
	/**
	 * 现货详细页面点击累加
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView countDetail(Integer id,Map<String, Object>out) throws IOException{
		// 浏览次数 加 一
		productsSpotService.updateViewCountById(id);
		return printJson(id, out);
	}
	
	@RequestMapping
	public ModelAndView xianhuo_buy(Map<String, Object> out) {
		String str = "	01,PVB塑胶半成品,浙江信威塑胶有限公司,http://xinweipvb.zz91.com/|"
			+ "		02,灰色PC/ABS面板料韧性好,广东汕头（陈楚俊）,http://ccj.zz91.com/|"
			+ "		03,黑色PC/ABS面板料韧性好,广东汕头（陈楚俊）,http://ccj.zz91.com/|"
			+ "		04,白丙60目注塑使用PP颗粒,吴川浩允塑料制品有限公司,http://liyh.zz91.com/|"
			+ "		05,ps颗粒发泡成型板专用颗粒,宁波慈溪（叶贺琼）,http://yhq.zz91.com/|"
			+ "		06,供应PU颗粒（黑色）,福建晋江新兴日用塑料厂,http://zhan.zz91.com/|"
			+ "		07,供应PU颗粒（米黄色）,福建晋江新兴日用塑料厂,http://zhan.zz91.com/|"
			+ "		08,PP颗粒奶白色高耐冲环保级,东莞市锦亿抽粒厂,http://jinyi.zz91.com/|"
			+ "		09,PP颗粒瓷白色高耐冲全浮水环保,东莞市锦亿抽粒厂,http://jinyi.zz91.com/|"
			+ "		10,PP颗粒半透明黄色耐冲环保级,东莞市锦亿抽粒厂,http://jinyi.zz91.com/|"
			+ "		11,PP颗粒半透明红色耐冲环保级,东莞市锦亿抽粒厂,http://jinyi.zz91.com/|"
			+ "		12,PP颗粒灰白色,江苏泰州（徐增森）,http://xzs.zz91.com/|"
			+ "		13,PVC胶头料灰黑色,广东清远（龚杰锋）,http://gtgong.zz91.com/|"
			+ "		14,EVA+PP混合颗粒蓝色,青岛伊托斯塑料环保有限公司,http://yituosi.zz91.com/|"
			+ "		15,EVA+LDPE混合颗粒蓝色,青岛伊托斯塑料环保有限公司,http://yituosi.zz91.com/|"
			+ "		16,ABS黑色颗粒,汕头市伯文塑胶公司,http://bowen.zz91.com/|"
			+ "		17,PP打包带黑色颗粒,福州市福星塑料厂,http://fzfx.zz91.com/|"
			+ "		18,PP打包带灰色颗粒,福州市福星塑料厂,http://fzfx.zz91.com/|"
			+ "		19,一级PE（高压/保鲜）,广东惠州（陈先生）,http://mrchen.zz91.com/|"
			+ "		20,三级PE（高压）,广东惠州（陈先生）,http://mrchen.zz91.com/|"
			+ "		21,二级PE（高压）,广东惠州（陈先生）,http://mrchen.zz91.com/|"
			+ "		22,白广告布吹毛料,浙江东阳宇航塑胶有限公司,http://dyyh.zz91.com/|"
			+ "		23, PP聚丙料黑色现货,江苏徐州（黄先生）,http://gtjyhzc.zz91.com/|"
			+ "		24,HDPE黑色颗粒,浙江台州（陶女士）,http://taons.zz91.com/|"
			+ "		25,蓝HIPS改性,浙江台州（陶女士）,http://taons.zz91.com/|"
			+ "		26,LDPE黑色颗粒电缆造,浙江台州（陶女士）,http://taons.zz91.com/|"
			+ "		27,PP-R破碎料,陕西西安（李国太）,http://pprkeli.zz91.com/|"
			+ "		28,PP黑色颗粒,山东滨州（王先生）,http://gtjywfc.zz91.com/|"
			+ "		29,PA6纺丝注塑阻燃颗粒,余姚塑料城（徐东帅）,http://xuzih.zz91.com/|"
			+ "		30,HDPE牛奶瓶颗粒,汨罗市精诚塑业有限公司,http://jingcsl.zz91.com/|"
			+ "		31,洗衣机桶料PP颗粒白色,汨罗市精诚塑业有限公司,http://jingcsl.zz91.com/|"
			+ "		32,ABS+PC褪镀汽车把手,广东美之达铜镍科技有限公司,http://lianghait.zz91.com/|"
			+ "		33,本色料ABS破碎料,广东美之达铜镍科技有限公司,http://lianghait.zz91.com/|"
			+ "		34,EVA再生颗粒,河北廊坊（林百利）,http://linbl.zz91.com/|"
			+ "		35,PE中空颗粒,上海万里再生塑料造粒厂,http://shliqiang.zz91.com/|"
			+ "		36,透明PP颗粒,浙江金华（卓建）,http://zhuojian.zz91.com/|"
			+ "		37,淡黄PP颗粒,浙江金华（卓建）,http://zhuojian.zz91.com/|"
			+ "		38,PA6一级白色亮光,海门市鑫泽源塑料粒子厂,http://xzy.zz91.com/|"
			+ "		39,PA6尼龙膜颗粒,海门市鑫泽源塑料粒子厂,http://xzy.zz91.com/|"
			+ "		40,PA6浇注尼龙颗粒,海门市鑫泽源塑料粒子厂,http://xzy.zz91.com/|"
			+ "		41,软PP料颗粒 ,余姚昕蕾塑料厂,http://xinlei.zz91.com/|"
			+ "		42,PP白色粉碎料,上海市思成塑料制品经营部,http://scsl.zz91.com/|"
			+ "		43,PA6颗粒尼龙薄膜,宁波余姚丁水波,http://dsb.zz91.com/|"
			+ "		44,1号中软PVC再生颗粒75°C,慈溪市横河镇永胜塑料制品厂,http://kangjj.zz91.com/|"
			+ "		45,ABS黑色颗粒,河北保定韩森,http://hansen.zz91.com/|"
			+ "		46,PVC开包料白色一级料,河南安阳(吕天利),http://lvtianli.zz91.com/|"
			+ "		47,PVC落地粉三级料,河南安阳(吕天利),http://lvtianli.zz91.com/|"
			+ "		48,特级HDPE破碎料哇哈哈奶瓶纯白,江西上饶（卢福春）,http://lfc.zz91.com/|"
			+ "		49,特级PP破碎料纯吊瓶料,江西上饶（卢福春）,http://lfc.zz91.com/|"
			+ "		50,PVC破碎料,浙江慈溪（李玉,http://liyu.zz91.com/|"
			+ "		51,pp吨包颗粒灰白色,南城县富林实业有限公司,http://fulin.zz91.com/|"
			+ "		52,pp吨包颗粒黄色,南城县富林实业有限公司,http://fulin.zz91.com/|"
			+ "		53,PP过滤板粉碎料,浙江台州（孙天炯）,http://suntianjiong.zz91.com/|"
			+ "		54,PP无纺布黑色,潍坊市学刚无纺布材料销售处,http://xgwfb.zz91.com/index.htm|"
			+ "		55,PP无纺布白色,潍坊市学刚无纺布材料销售处,http://xgwfb.zz91.com/index.htm|"
			+ "		56,PVC黑色颗粒,汕头市伯文塑胶公司,http://bowen.zz91.com/|"
			+ "		57,EPS颗粒日本进口eps热熔块抽粒,佛山焕发塑胶原料有限公司,http://huanfa.zz91.com/|"
			+ "		58,ABS颗粒淡褐色可定制相近颜色,浙江宁波（余益峰）,http://delisuye.zz91.com/|"
			+ "		59,青丙PP80目可拉丝及注塑使用,吴川市浩允塑料制品有限公司,http://liyh.zz91.com/";
		String[] arrayStr = str.split("\\|");
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		for(String s :arrayStr){
			Map<String, String> nmap =new HashMap<String, String>();
			String[] arrayS = s.split(",");
			nmap.put("id", arrayS[0]);
			nmap.put("title", arrayS[1]);
			nmap.put("name", arrayS[2]);
			nmap.put("url", arrayS[3]);
			map.put(arrayS[0], nmap);
		}
		out.put("data", map);
		// seo
		SeoUtil.getInstance().buildSeo("xianhuoBuy", out);
		return null;
	}

}