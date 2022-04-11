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
import com.tripleaims.exhibition.dto.PagingParam;
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

	@RequestMapping(value={"exhibitionInfo","exhibitionInfo.do"})
	private ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		
		System.out.println("ExhibitionController exhibitionInfo() : exhibitionNo=" + exhibitionNo);
		ExhibitionDTO dto = service.exhibitionInfo(exhibitionNo);

		return dto;
	}
	
	@RequestMapping(value = "/crrentList", method = {RequestMethod.GET, RequestMethod.POST})
	private List<ExhibitionDTO> crrentList(PagingParam dto) {
		
		System.out.println("ExhibitionController crrentList()");
		System.out.println(dto.toString());
		
		int cp = dto.getPage() ;
		int start = cp * 10 + 1;
		int end = (cp + 1) * 9;	

		dto.setStart(start);
		dto.setEnd(end);
		
		System.out.println("페이지:"+ cp + "처음:" + start + "끝: " + end);

		List<ExhibitionDTO> List = service.crrentList(dto);
		
		return List;
	}
	
	
	@RequestMapping(value = "/currentCount", method = RequestMethod.GET)
	private int currentCount(PagingParam pram) {
		System.out.println("ExhibitionController currentCount()");
		System.out.println("검색데이터 확인:" + pram.toString());
		
		int count  = service.currentCount(pram);
		System.out.println("현재 오픈된 총 전시회 수 : " + count);

		int pagenum = count/10;
		if((count %10)>0) {
			pagenum = pagenum + 1;
		}
		System.out.println("페이지수 : " + pagenum);
		return pagenum;

	}
	
	@RequestMapping(value = "/pastList", method = {RequestMethod.GET, RequestMethod.POST})
	private List<ExhibitionDTO> pastList(PagingParam dto) {
		
		System.out.println("ExhibitionController pastList()");
		System.out.println(dto.toString());
		
		int cp = dto.getPage() ;
		int start = cp * 10 + 1;
		int end = (cp + 1) * 9;	
		
		dto.setStart(start);
		dto.setEnd(end);
		
		System.out.println("페이지:"+ cp + "처음:" + start + "끝: " + end);
		
		List<ExhibitionDTO> List = service.pastList(dto);
		
		return List;
	}
	
	
	@RequestMapping(value = "/pastCount", method = RequestMethod.GET)
	private int pastCount(PagingParam pram) {
		System.out.println("ExhibitionController pastCount()");
		System.out.println("검색데이터 확인:" + pram.toString());
		
		int count  = service.pastCount(pram);
		System.out.println("현재 오픈된 총 전시회 수 : " + count);
		
		int pagenum = count/10;
		if((count %10)>0) {
			pagenum = pagenum + 1;
		}
		System.out.println("페이지수 : " + pagenum);
		return pagenum;
		
	}
	
	@RequestMapping(value = "/exArtwowrk", method = {RequestMethod.GET, RequestMethod.POST})
	private List<ArtworkDTO> exArtwowrk(String exhibitionNo) {
		
		System.out.println("ExhibitionController exArtwowrk()");
		List<ArtworkDTO> List = service.exArtwowrk(exhibitionNo);
		
		return List;
	}
	
	
	
	
	
	
}
