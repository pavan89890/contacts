package com.pavan.google;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class GoogleAddress {

	private static final String YOUR_API_KEY = null;

	public static void main(String[] args) throws MalformedURLException {
		String searchString="restaurants+in+Sydney";
		String api = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+searchString+"&key="
				+ YOUR_API_KEY;
		StringBuffer content=new StringBuffer();
		try {
			URL url = new URL(api);
			URLConnection urlcon = url.openConnection();
			InputStream stream = urlcon.getInputStream();
			int i;
			while ((i = stream.read()) != -1) {
				content.append((char)i);
			}
			
			System.out.print(content);
			
			JSONObject jsonObj=new JSONObject(content.toString());
			System.out.println(jsonObj);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
