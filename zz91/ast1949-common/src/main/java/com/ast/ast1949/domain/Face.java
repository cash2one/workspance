package com.ast.ast1949.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Face{

	private static final Map<String,String> faceMap=new HashMap<String, String>();
	
	static{
		faceMap.put("[哈哈]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/laugh.gif\" title=\"哈哈\"/>");
		faceMap.put("[太开心]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/mb_thumb.gif\" title=\"太开心\"/>");
		faceMap.put("[爱你]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/lovea_thumb.gif\" title=\"爱你\"/>");
		faceMap.put("[闭嘴]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/bz_thumb.gif\" title=\"闭嘴\"/>");
		faceMap.put("[右哼哼]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/yhh_thumb.gif\" title=\"右哼哼\"/>");
		faceMap.put("[泪]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/sada_thumb.gif\" title=\"泪\"/>");
		faceMap.put("[拜拜]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/88_thumb.gif\" title=\"拜拜\"/>");
		faceMap.put("[偷笑]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/heia_thumb.gif\" title=\"偷笑\"/>");
		faceMap.put("[惊讶]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/cj_thumb.gif\" title=\"惊讶\"/>");
		faceMap.put("[馋嘴]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/cza_thumb.gif\" title=\"馋嘴\"/>");
		faceMap.put("[衰]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/cry.gif\" title=\"衰\"/>");
		faceMap.put("[懒得理你]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/ldln_thumb.gif\" title=\"懒得理你\"/>");
		faceMap.put("[钱]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/money_thumb.gif\" title=\"钱\"/>");
		faceMap.put("[good]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/good_thumb.gif\" title=\"good\"/>");
		faceMap.put("[ok]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/ok_thumb.gif\" title=\"ok\"/>");
		faceMap.put("[耶]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/ye_thumb.gif\" title=\"耶\"/>");
		faceMap.put("[蛋糕]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/cake.gif\" title=\"蛋糕\"/>");
		faceMap.put("[怒骂]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/nm_thumb.gif\" title=\"怒骂\"/>");
		faceMap.put("[抓狂]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/crazya_thumb.gif\" title=\"抓狂\"/>");
		faceMap.put("[汗]", "<img src=\"http://img0.huanbao.com/huanbao/mblog/images/sweata_thumb.gif\" title=\"汗\"/>");
	}
	
	private final static List<String> faceList=new ArrayList<String>();
	
	static{
		faceList.add("[哈哈]");
		faceList.add("[太开心]");
		faceList.add("[爱你]");
		faceList.add("[闭嘴]");
		faceList.add("[右哼哼]");
		faceList.add("[泪]");
		faceList.add("[拜拜]");
		faceList.add("[偷笑]");
		faceList.add("[惊讶]");
		faceList.add("[馋嘴]");
		faceList.add("[衰]");
		faceList.add("[懒得理你]");
		faceList.add("[钱]");
		faceList.add("[good]");
		faceList.add("[ok]");
		faceList.add("[耶]");
		faceList.add("[蛋糕]");
		faceList.add("[怒骂]");
		faceList.add("[抓狂]");
		faceList.add("[汗]");
		
	}
	
	public static String getFace(String contents){
		for (int i = 0; i < faceList.size(); i++) {
			String string =faceList.get(i);
			contents=contents.replace(string, faceMap.get(string));
			
		}
		return contents;
	}
	
}
