package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripleaims.exhibition.service.ArtworkCategoryService;
import com.tripleaims.exhibition.service.ArtworkService;

import lombok.AllArgsConstructor;

@Controller
@ResponseBody
@RequestMapping("artwork")
@AllArgsConstructor
public class ArtworkController {

	ArtworkService service;
	ArtworkCategoryService categryService;
	
	@GetMapping("getAllArtwork.do")
	public Map<String, Object> getAllArtwork() {
		return service.selectAllArtwork();
	}
	
	@GetMapping("getCategory.do")
	public Map<String, Object> selectAll() {
		return categryService.selectAllArtworkCategory();
	}
	
}
