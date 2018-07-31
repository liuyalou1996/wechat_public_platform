package com.iboxpay.web.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.disconf.core.common.utils.FileUtils;
import com.iboxpay.entity.Merchant;

@Controller
public class MerchantController {

	@RequestMapping("/getMerchant.json")
	@ResponseBody
	public Merchant response() {
		Merchant merchant = new Merchant();
		merchant.setMid(1);
		merchant.setMname("lyl");
		merchant.setMno("152164");
		return merchant;
	}

	@RequestMapping("/getMerchantMap.json")
	@ResponseBody
	public Map<String, Object> merchantInfo(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		return map;
	}

	@RequestMapping("/upload.json")
	@ResponseBody
	public Map<String, Object> uploadFile(MultipartFile portrait) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!portrait.isEmpty()) {
			map.put("resultCode", 1);
			map.put("fileName", portrait.getOriginalFilename());
			map.put("fieldName", portrait.getName());
		} else {
			map.put("resultCode", 0);
		}

		return map;
	}

	@RequestMapping("/download.json")
	public ResponseEntity<byte[]> downLoadFile(HttpServletResponse response) throws Exception {
		File file = new File("C:\\Users\\Administrator\\Pictures\\a.jpg");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
}
