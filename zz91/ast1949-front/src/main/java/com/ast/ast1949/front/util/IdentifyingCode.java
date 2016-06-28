/**
 * 
 */
package com.ast.ast1949.front.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class IdentifyingCode {

	/**
	 * 验证码长度（默认8个验证码的长度）
	 */
	int length = 8;

	/**
	 * 验证码字体大小（为了显示扭曲效果，默认50像素，可以自行修改）
	 */
	int fontSize = 50;

	/**
	 * 边框补（默认2像素）
	 */
	int padding = 2;

	/**
	 * 是否输出燥点（默认输出）
	 */
	boolean chaos = true;

	/**
	 * 输出燥点的颜色（默认灰色）
	 */
	Color chaosColor = Color.lightGray;

	/**
	 * 自定义背景色（默认白色）
	 */
	Color backgroundColor = Color.white;

	/**
	 * 自定义字体数组
	 */
	String[] fonts = { "Arial", "Georgia", "Times New Roman" };

	/**
	 * 自定义随机码字符串序列（使用逗号分隔）
	 */
	String codeSerial = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";

	/**
	 * 产生波形滤镜效果
	 */
	private final double PI = 3.1415926535897932384626433832799;// 此值越大，扭曲程度越大

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public boolean isChaos() {
		return chaos;
	}

	public void setChaos(boolean chaos) {
		this.chaos = chaos;
	}

	public Color getChaosColor() {
		return chaosColor;
	}

	public void setChaosColor(Color chaosColor) {
		this.chaosColor = chaosColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String[] getFonts() {
		return fonts;
	}

	public void setFonts(String[] fonts) {
		this.fonts = fonts;
	}

	public String getCodeSerial() {
		return codeSerial;
	}

	public void setCodeSerial(String codeSerial) {
		this.codeSerial = codeSerial;
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 正弦曲线Wave扭曲图片
	 * 
	 * @param srcBi
	 *            图片路径
	 * @param bXDir
	 *            如果扭曲则选择为true
	 * @param dMultValue
	 *            波形的幅度倍数，越大扭曲的程序越高，一般为3
	 * @param dPhase
	 *            波形的起始相位，取值区间（0-2＊PI）
	 * @return
	 */
	private BufferedImage TwistImage(BufferedImage srcBi, boolean bXDir,
			double dMultValue, double dPhase) {
		// 在内存中创建图象
		BufferedImage destBi = new BufferedImage(srcBi.getWidth(),
				srcBi.getHeight(), BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics graphics = destBi.getGraphics();

		// 将位图填充背景色，默认为黑色
		// graphics.setColor(this.backgroundColor);
		// graphics.fillRect(0, 0, destBi.getWidth(), destBi.getHeight());

		// 图象生效
		graphics.dispose();

		double dBaseAxisLen = bXDir ? (double) destBi.getHeight()
				: (double) destBi.getWidth();

		for (int i = 0; i < destBi.getWidth(); i++) {
			for (int j = 0; j < destBi.getHeight(); j++) {
				double dx = 0;
				dx = bXDir ? (PI * (double) j) / dBaseAxisLen
						: (PI * (double) i) / dBaseAxisLen;
				dx += dPhase;
				double dy = Math.sin(dx);

				// 取得当前点的颜色
				int nOldX = 0, nOldY = 0;
				nOldX = bXDir ? i + (int) (dy * dMultValue) : i;
				nOldY = bXDir ? j : j + (int) (dy * dMultValue);
				int rgb = srcBi.getRGB(i, j);
				if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0
						&& nOldY < destBi.getHeight()) {
					destBi.setRGB(nOldX, nOldY, rgb);
				}

			}
		}

		return destBi;
	}

	/**
	 * 生成校验码图片
	 * 
	 * @param code
	 * @return
	 */
	private BufferedImage CreateImageCode(String code) {
		int fWidth = this.fontSize + this.padding;

		// 图象的宽度
		int imageWidth = (int) (code.length() * fWidth) + 4 + this.padding * 2;
		// 图象的高度
		int imageHeight = this.fontSize * 2;

		// 在内存中创建图象
		BufferedImage bi = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);

		Graphics graphics = bi.getGraphics();

		// 将位图填充背景色
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		// 生成随机类
		Random random = new Random();

		// 给背景添加随机生成的燥点,使图象中的认证码不易被其它程序探测到
		if (this.chaos) {
			int c = this.length * 10;
			for (int i = 0; i < c; i++) {
				int x = random.nextInt(bi.getWidth());
				int y = random.nextInt(bi.getHeight());
				graphics.setColor(this.chaosColor);// 设置燥点的颜色
				graphics.drawRect(x, y, 1, 1);// 生成燥点
				// graphics.drawLine(x, y, 1, 1);//生成干扰线
			}
		}

		// 随机字体和颜色的验证码字符
		for (int i = 0; i < code.length(); i++) {

			// 设置字体属性
			int findex = random.nextInt(this.fonts.length);
			Font font = new Font(fonts[findex], Font.BOLD, this.fontSize);
			graphics.setFont(font);

			int top = (imageHeight + code.length() * 2) / 2;
			if (i % 2 != 1) {
				top = top - code.length();
			}
			int left = i * fWidth + code.length();
			graphics.setColor(this.getRandColor(1 + i, 250 - i));

			try {
				graphics.drawString(code.substring(i, i + 1), left, top);
			} catch (StringIndexOutOfBoundsException e) {
				System.out.print(e.toString());
			}
		}

		// 画一个边框，边框颜色为Color.gray
		graphics.setColor(Color.gray);
		graphics.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);

		// 图象生效
		graphics.dispose();

		// 产生波形
		bi = TwistImage(bi, true, 8, 4);

		return bi;
	}

	/**
	 * 将创建好的图片输出到页面
	 * 
	 * @param bi
	 * @param response
	 */
	public void CreateImageOnPage(String code, HttpServletResponse response) {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 获取验证码图片
		BufferedImage bi = this.CreateImageCode(code);
		try {
			// 输出图象到页面
			ImageIO.write(bi, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成随机字符码
	 * 
	 * @param codeLen
	 *            生成字符码的个数,0则默认的个数
	 * @return
	 */
	private String CreateVerifyCode(int codeLen) {
		if (codeLen == 0) {
			codeLen = this.length;
		}
		String[] arr = this.codeSerial.split(",");
		String code = "";
		int randValue = -1;
		Random random = new Random();
		for (int i = 0; i < codeLen; i++) {
			randValue = random.nextInt(arr.length - 1);
			code += arr[randValue];
		}
		return code;
	}

	/**
	 * 获取随机字符码
	 * 
	 * @return
	 */
	public String CreateVerifyCode() {
		return this.CreateVerifyCode(0);
	}

}
