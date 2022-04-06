package com.tripleaims.exhibition.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;
import com.tripleaims.exhibition.service.ExhibitionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("exhibition")
@AllArgsConstructor
@CrossOrigin("*")
public class ExhibitionController {

	private ExhibitionService service;
	
	@RequestMapping("selectExhibition.do")
	public Map<String, Object> selectExhibition(String title, @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		paramMap.put("toDate", toDate);
		paramMap.put("endDate", endDate);
		
		return service.selectExhibition(paramMap);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("addExhibition.do")
	public String addExhibition(ExhibitionDTO dto, @DateTimeFormat(pattern="yyyy-MM-dd") Date sDate, int sHours, int sMins, String artworks) {
		sDate.setHours(sHours);
		sDate.setMinutes(sMins);
		dto.setStartDate(sDate);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("exhibition", dto);
		paramMap.put("artworks", artworks);
		return service.addExhibition(paramMap);
	}

	@RequestMapping(value = "/exhibitionInfo", method = {RequestMethod.GET, RequestMethod.POST})
	private ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		
		System.out.println("ArtistController exhibitionInfo()");
		ExhibitionDTO dto = service.exhibitionInfo(exhibitionNo);

		return dto;
	}
	
	@RequestMapping(value = "/exhibitionList", method = {RequestMethod.GET, RequestMethod.POST})
	private List<ExhibitionDTO> exhibitionList(ExhibitionDTO dto) {
		
		System.out.println("ArtistController exhibitionList()");
		List<ExhibitionDTO> List = service.exhibitionList(dto);
		
		return List;
	}
	
	
	@RequestMapping(value = "/exArtwowrk", method = {RequestMethod.GET, RequestMethod.POST})
	private List<ArtworkDTO> exArtwowrk(String exhibitionNo) {
		
		System.out.println("ArtistController exArtwowrk()");
		List<ArtworkDTO> List = service.exArtwowrk(exhibitionNo);
		
		return List;
	}
	
	
	
	
	
}
