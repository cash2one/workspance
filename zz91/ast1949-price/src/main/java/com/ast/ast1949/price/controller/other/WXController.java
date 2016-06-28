package com.ast.ast1949.price.controller.other;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.price.controller.BaseController;

/**
 * author:kongsj date:2013-5-22
 */
@Controller
public class WXController extends BaseController {

	private static final String TOKEN = "weixin";

	// private static final String AppId = "wx2891ef70c5a770d6";
	//
	// private static final String AppSecret =
	// "d3f9436cfc50cd9e4f62f96893a1ee0c";

	// http客户端
	public static DefaultHttpClient httpclient;

	@RequestMapping
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 验证
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		PrintWriter out = response.getWriter();
		if (checkSignature(signature, timestamp, nonce)) {
			out.print(request.getParameter("echostr"));
		}
		out.close();
		out = null;
		// 验证结束

	}

	private static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	// 将字节转换为十六进制字符串
	private static String byteToHexStr(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		String s = new String(ob);
		return s;
	}

	// 将字节数组转换为十六进制字符串
	private static String byteToStr(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexStr(bytearray[i]);
		}
		return strDigest;
	}

}
