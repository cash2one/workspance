package com.ast.ast1949.sample.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
@Controller
public class ValidationCodeController extends BaseController{
	final static char[] CHAR_BASE={'a','b','c','d','e','f','g','h','i','j','k',
		'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
		'D','E','F','G','H','I','J','k','L','M','N','O','P','Q','R','S','T','U',
		'V','W','X','Y','Z','中','国','再','生','资','源','交','易','网',
		'废','料','金','属','塑','纸','橡','胶','报','价','互','助','的','有','铜',
		'铝','铁','锈','外','内','日','评','基','本','趋','势','走','周','一','二',
		'三','四','五','六','七','八','九','十','零','现','在','述','天','然','加',
		'减','用','户','第','北','东','南','西','义','杭','州','语','言','方','广',
		'度','乌','上','下','左','右','天','地','合','身','体','奶','乃','敢','与',
		'甲','乙','丙','丁','分','钟','手','机','械','制','造','业','企','银','铜',
		'渡','流','散','讼','要','数','理','易','程','时','间','立','产','秒','率'};
	
	final static char[] CHAR_BASE_BLANK={' '};
	
	@RequestMapping
	public ModelAndView cncode(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		Builder builder = new Captcha.Builder(160, 50);
		builder.addBorder();
		
//		builder.addNoise();
		builder.gimp(new BlockGimpyRenderer(3));
		
		List<Font> fontList = new ArrayList<Font>();
//		fontList.add(new Font("Purisa", Font.BOLD, 41));
		fontList.add(new Font("FZJingLeiS-R-GB", Font.ITALIC, 42));
//		fontList.add(new Font("Georgia", Font.BOLD, 45));
		List<Color> colorList=new ArrayList<Color>();
		colorList.add(Color.red);
		colorList.add(Color.blue);
		colorList.add(Color.darkGray);
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		DefaultWordRenderer dwr= new DefaultWordRenderer(colorList,fontList);
		
		WordRenderer wr=dwr;
		
//		builder.addText(wr);
//		builder.addText(new DefaultTextProducer(1, CHAR_BASE_BLANK),wr);
		builder.addText(new DefaultTextProducer(4, CHAR_BASE),wr);
		
		builder.addBackground(new FlatColorBackgroundProducer(getRandColor(100, 255)));
//		builder.gimp(new BlockGimpyRenderer(1));
		Captcha captcha =  builder .build();
		
//		Captcha captcha = new Captcha.Builder(160, 50)
//			.addText(textProducer)
//			.addBackground(new FlatColorBackgroundProducer(getRandColor(130, 250)))
////			.addNoise(noise)
//			.gimp(gimpy)
//			.addBorder()
//			.build();

		MemcachedUtils.getInstance().getClient().set(ts, 300000, captcha);
		CaptchaServletUtil.writeImage(response, captcha.getImage());
		
		return null;
		
//		Captcha captcha = new Captcha.Builder(160, 50)
//			.addText(new DefaultTextProducer(4, CHAR_BASE))
//			.addBackground(new FlatColorBackgroundProducer(getRandColor(130, 250)))
////			.addNoise(noise)
//			.gimp(new BlockGimpyRenderer(1))
//			.addBorder()
//			.build();
//
//		MemcachedUtils.getInstance().getClient().set(ts, 300000, captcha);
//		CaptchaServletUtil.writeImage(response, captcha.getImage());
//		
//		return null;
	}
	
	@RequestMapping
	public ModelAndView mathcode(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Builder builder = new Captcha.Builder(160, 50);
//		builder.addBorder();
		
//		builder.addNoise();
		builder.gimp(new BlockGimpyRenderer(3));
		
		List<Font> fontList = new ArrayList<Font>();
		fontList.add(new Font("FZJingLeiS-R-GB", Font.ITALIC, 42));
//		fontList.add(new Font("FZJingLeiS-R-GB", Font.BOLD, 32));
//		fontList.add(new Font("WishfulWaves", Font.ITALIC, 36));
//		fontList.add(new Font("WishfulWaves", Font.BOLD, 36));
//		fontList.add(new Font("Astarisborn", Font.BOLD, 36));
//		fontList.add(new Font("a_TrianglerCmUp", Font.ITALIC, 36));
		List<Color> colorList=new ArrayList<Color>();
		colorList.add(Color.red);
		colorList.add(Color.blue);
		colorList.add(Color.darkGray);
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		DefaultWordRenderer dwr= new DefaultWordRenderer(colorList,fontList);
		
		WordRenderer wr=dwr;
		
		//构造随机数字
		int a=(int)(Math.random()*10);
		int b=(int)(Math.random()*10);
		
		int math=(int) (Math.random()*3);
		String mathstr="加";
		int result=a+b;
		if(math==1){
			mathstr="减";
			result=a-b;
		}else if(math==2){
			mathstr="乘";
			result=a*b;
		}
		
//		System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>:"+result);	
//		if(result>10){
			builder.addText(new DefaultTextProducer(1, String.valueOf(a).toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, mathstr.toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, String.valueOf(b).toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'等'}), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'于'}), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'?'}), wr);
//		}else{
//			builder.addText(new DefaultTextProducer(1, new char[]{'?'}), wr);
//			builder.addText(new DefaultTextProducer(1, new char[]{'于'}), wr);
//			builder.addText(new DefaultTextProducer(1, new char[]{'等'}), wr);
//			builder.addText(new DefaultTextProducer(1, String.valueOf(b).toCharArray()), wr);
//			builder.addText(new DefaultTextProducer(1, mathstr.toCharArray()), wr);
//			builder.addText(new DefaultTextProducer(1, String.valueOf(a).toCharArray()), wr);
//		}
		
		builder.addBackground(new FlatColorBackgroundProducer(getRandColor(100, 255)));
		builder.gimp(new BlockGimpyRenderer(1));
		Captcha captcha =  builder.build();
		

		MemcachedUtils.getInstance().getClient().set(ts, 300000, result);
		
		CaptchaServletUtil.writeImage(response, captcha.getImage());
		
		return null;
	}
	
	
	public Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
//	private static Random r = new Random();
//	private static CurvesRippleFilterFactory crff = null;
//	private static MarbleRippleFilterFactory mrff = null;
//	private static DoubleRippleFilterFactory drff = null;
	private static WobbleRippleFilterFactory wrff = null;
//	private static DiffuseRippleFilterFactory dirff = null;
	
	private void init(){
		if(cs==null){
			cs = new ConfigurableCaptchaService();
		}
		if(cf==null){
			cf = new SingleColorFactory(new Color(25, 60, 170));
		}
		if(wf==null){
			wf = new RandomWordFactory();
		}
//		crff = new CurvesRippleFilterFactory(cs.getColorFactory());
//		drff = new DoubleRippleFilterFactory();
//		dirff = new DiffuseRippleFilterFactory();
//		mrff = new MarbleRippleFilterFactory();

		if(wrff==null){
			wrff = new WobbleRippleFilterFactory();
		}
		
		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(160);
		cs.setHeight(50);

	}
	
//	
	@RequestMapping
	public ModelAndView vcode(HttpServletRequest request, Map<String, Object> out, HttpServletResponse response) {
		
		
		response.setContentType("image/png"); 
		
		init();
		
		OutputStream os;
		try {

			os = response.getOutputStream();
			cs.setFilterFactory(wrff);
			
			org.patchca.service.Captcha captcha = cs.getCaptcha();
			SsoUtils.getInstance().setValue(request, response, AstConst.VALIDATE_CODE_KEY, captcha.getChallenge());
			ImageIO.write(captcha.getImage(), "png", os); 
			
			os.flush();
			os.close();
			
		} catch (IOException e) {
		}finally{
		}
		
		return null;
	}

}
