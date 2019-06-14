package com.pavan.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pavan.entity.bean.ApiResponse;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/language/")
public class LanguageTranslationsController {

	@RequestMapping(method = RequestMethod.GET, value = "sortJsonFile")
	public String sortJsonFile() {
		return "sortJsonFile";
	}

	@PostMapping("sortJsonFile")
	@ResponseBody
	public ApiResponse sortJsonFile(@RequestParam(value = "jsonData", required = true) String jsonStr) {

		ApiResponse response = new ApiResponse(HttpStatus.OK, "Success", null);
		String errorMessage = "";

		if (jsonStr == null) {
			errorMessage = "ERROR-Please enter json data";
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
			return response;
		}

		JSONObject jsonData;
		try {
			jsonData = new JSONObject(jsonStr);
		} catch (JSONException e1) {
			errorMessage = "ERROR-" + e1.getMessage();
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
			return response;
		}

		Map<String, String> jsonMap = new TreeMap<String, String>();

		try {
			@SuppressWarnings("unchecked")
			Iterator<String> itr = jsonData.keys();

			while (itr.hasNext()) {
				String next = itr.next();
				jsonMap.put(next, String.valueOf(jsonData.get(String.valueOf(next))));
			}

		} catch (Exception e) {
			errorMessage = "ERROR-" + e.getMessage();
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
			return response;
		}
		response = new ApiResponse(HttpStatus.OK, "Success", jsonMap);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "createJsonFile")
	public String createJsonFile() {
		return "createJsonFile";
	}

	@PostMapping("createJsonFile")
	@ResponseBody
	public ApiResponse createJsonFile(@RequestParam(value = "labels[]", required = true) String[] labels,
			@RequestParam(value = "translations[]", required = true) String[] translations) {
		ApiResponse response = new ApiResponse(HttpStatus.OK, "Success", null);

		String errorMessage = "";
		Map<Boolean, String> validation = validateJsonFile(labels, translations);

		if (validation != null && validation.containsKey(Boolean.FALSE)) {
			errorMessage = "ERROR-" + validation.get(Boolean.FALSE);
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
			return response;
		}

		Map<String, String> jsonMap = new TreeMap<String, String>();

		try {
			for (int i = 0; i < labels.length; i++) {
				jsonMap.put(labels[i], translations[i]);
			}
		} catch (Exception e) {
			errorMessage = "ERROR-" + e.getMessage();
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
		}
		response = new ApiResponse(HttpStatus.OK, "Success", jsonMap);
		return response;
	}

	public Map<Boolean, String> validateJsonFile(String[] labels, String[] translations) {

		Map<Boolean, String> value = new HashMap<>();

		if (labels == null || labels.length == 0) {
			value.put(Boolean.FALSE, "Please Enter Labels");
			return value;
		} else if (translations == null || translations.length == 0) {
			value.put(Boolean.FALSE, "Please Enter Translations");
			return value;
		} else if (labels.length != translations.length) {
			value.put(Boolean.FALSE, "Lebels count must be equal to Translations count");
			return value;
		}
		value.put(Boolean.TRUE, "Valid");
		return value;
	}
}
