package com.pavan.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExcelController {

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/download/excel-report" }, method = RequestMethod.GET)
	public HttpEntity<byte[]> downloadExcelReport() {

		/** assume that below line gives you file content in byte array **/
		byte[] excelContent = "pavan".getBytes();
		// prepare response
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my_file.txt");
		header.setContentLength(excelContent.length);

		return new HttpEntity<byte[]>(excelContent, header);
	}
}
