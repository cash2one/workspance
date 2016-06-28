package test;

import org.apache.commons.httpclient.NameValuePair;

import com.zz91.util.http.HttpUtils;

public class CompAccountTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			HttpUtils ht=new HttpUtils();
			NameValuePair[] pas = new NameValuePair[]{
					new NameValuePair("table", "comp_account"),
			};
			ht.httpPost("http://localhost:8080/sync/MaxServlet",pas, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
