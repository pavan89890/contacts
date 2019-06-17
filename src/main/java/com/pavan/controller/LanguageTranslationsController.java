package com.pavan.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

	private String errorMessage = "";

	@RequestMapping(method = RequestMethod.GET, value = "sortJsonFile")
	public String sortJsonFile() {
		return "sortJsonFile";
	}

	@PostMapping("sortJsonFile")
	@ResponseBody
	public ApiResponse sortJsonFile(@RequestParam(value = "jsonData", required = true) String jsonStr) {

		ApiResponse response = new ApiResponse(HttpStatus.OK, "Success", null);

		if (jsonStr == null || jsonStr.isEmpty()) {
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR-Please enter json data", null);
			return response;
		}

		Map<String, String> jsonMap = new TreeMap<String, String>();

		try {
			String jsonStr2 = jsonStr.replace("{", "").replace("}", "").trim();
			for (String line : jsonStr2.split("\n")) {
				line = line.replaceAll("\"", "").replaceAll(",", "");
				String key = line.split(":")[0].trim();
				String value = line.split(":")[1].trim();
				jsonMap.put(key, value);
			}
		} catch (Exception e) {
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR-" + e.getMessage(), null);
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

		boolean isValidJson = validateJsonFile(labels, translations);

		if (!isValidJson) {
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR-" + errorMessage, null);
			return response;
		}

		Map<String, String> jsonMap = new TreeMap<String, String>();

		try {
			for (int i = 0; i < labels.length; i++) {
				jsonMap.put(labels[i], translations[i]);
			}
		} catch (Exception e) {
			response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR-" + e.getMessage(), null);
		}
		response = new ApiResponse(HttpStatus.OK, "Success", jsonMap);
		return response;
	}

	public boolean validateJsonFile(String[] labels, String[] translations) {

		if (labels == null || labels.length == 0) {
			errorMessage = "Please Enter Labels";
			return false;
		} else if (translations == null || translations.length == 0) {
			errorMessage = "Please Enter Translations";
			return false;
		} else if (labels.length != translations.length) {
			errorMessage = "Lebels count must be equal to Translations count";
			return false;
		}
		return true;
	}
}
