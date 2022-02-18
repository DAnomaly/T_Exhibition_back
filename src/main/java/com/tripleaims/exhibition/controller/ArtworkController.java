package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripleaims.exhibition.service.ArtworkCategoryService;
import com.tripleaims.exhibition.service.ArtworkService;
import com.tripleaims.exhibition.util.SecurityUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("artwork")
@AllArgsConstructor
@CrossOrigin("*")
public class ArtworkController {

	ArtworkService service;
	ArtworkCategoryService categryService;
	SecurityUtil securityUtil;
	
	@GetMapping("getAllArtwork.do")
	public Map<String, Object> getAllArtwork() {
		return service.selectAllArtwork();
	}
	
	@GetMapping("getCategory.do")
	public Map<String, Object> selectAll() {
		return categryService.selectAllArtworkCategory();
	}
	
	@GetMapping("test.do")
	public String test(String password) {
		return securityUtil.encodePassword(password);
	}
	
	
}