
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class CoronaBot {
	public static void main(String[] args) throws IOException,JsonProcessingException  {
		
		Document document = Jsoup.connect("https://3g.dxy.cn/newh5/view/pneumonia").get();
		//System.out.println(document.html());
		
		Elements elements = document.select("#getAreaStat");
		//System.out.println(elements.html());
		String datestr = elements.html().replace("try { window.getAreaStat = ", "");
	    datestr = datestr.replace("}catch(e){}", "");
	    //System.out.println(datestr);
		
	    
	    Object object=null; 
    	JsonArray arrayObj=null;
    	JsonParser jsonParser=new JsonParser();
    	object=jsonParser.parse(datestr);
    	//JSONObject dataObject = new JSONObject(jsonObject.get("data"));
    	arrayObj=(JsonArray) object;
    	
    	String jsStr = "[['都道府県', '感染者数'],";
    	for (int i = 0; i < arrayObj.size(); i++) {
    		JSONObject provinceObj = new JSONObject(arrayObj.get(i).toString());
    		
    		jsStr = jsStr+"['";
    		jsStr = jsStr+provinceObj.getString("provinceName")+"',";
    		jsStr = jsStr+provinceObj.getBigInteger("confirmedCount")+"],";
    		
    	}
    	jsStr = jsStr+"]";
    	jsStr = jsStr.replace("广西壮族自治区", "广西");
    	
    	System.out.println(jsStr);
    	
	}

}
