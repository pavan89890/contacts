package com.pavan.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoogleController {

	private static final String APP_ID = "LuHpi0NCpmHndcInyMW9";
	private static final String APP_CODE = "ns0lWe8_fwMaAq-RQJJL7A";

	@GetMapping("/geocoding")
	public String geocoding() {
		return "geocoding";
	}

	@PostMapping("/geocoding")
	@ResponseBody
	public String getGeoCoding(@RequestParam(value = "searchtext", required = true) String searchtext)
			throws UnsupportedEncodingException {
		String msg = "";
		if (searchtext != null && searchtext.length() > 8192) {
			msg = "Address can't be more than 8192 characters length";
			return msg;
		}

		String hereMapsApi = "https://geocoder.api.here.com/6.2/geocode.json?searchtext="
				+ URLEncoder.encode(searchtext, "UTF-8") + "&app_id=" + APP_ID + "&app_code=" + APP_CODE + "&gen=9";

		StringBuffer content = new StringBuffer();
		try {
			URL url = new URL(hereMapsApi);
			URLConnection urlcon = url.openConnection();
			InputStream stream = urlcon.getInputStream();
			int i;
			while ((i = stream.read()) != -1) {
				content.append((char) i);
			}

			System.out.print(content);

			JSONObject jsonObj = new JSONObject(content.toString());
			System.out.println(jsonObj);
			return jsonObj.toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
