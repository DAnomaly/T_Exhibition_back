package com.tripleaims.exhibition.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	// 전시회 검색
	@RequestMapping(value={"selectExhibition","selectExhibition.do"})
	public Map<String, Object> selectExhibition(String title, @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		paramMap.put("toDate", toDate);
		paramMap.put("endDate", endDate);
		
		return service.selectExhibition(paramMap);
	}
	
	// 전시회 추가
	@SuppressWarnings("deprecation")
	@RequestMapping(value={"addExhibition","addExhibition.do"})
	public String addExhibition(ExhibitionDTO dto, @DateTimeFormat(pattern="yyyy-MM-dd") Date sDate, int sHours, int sMins, String artworks) {
		sDate.setHours(sHours);
		sDate.setMinutes(sMins);
		dto.setStartDate(sDate);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("exhibition", dto);
		paramMap.put("artworks", artworks);
		return service.addExhibition(paramMap);
	}

	// 전시회 수정
	@SuppressWarnings("deprecation")
	@PostMapping(value={"editExhibition","editExhibition.do"})
	public Map<String, Object> editExhibition(ExhibitionDTO dto, @DateTimeFormat(pattern="yyyy-MM-dd") Date sDate, int sHours, int sMins, String statusSelect) {
		sDate.setHours(sHours);
		sDate.setMinutes(sMins);
		dto.setStartDate(sDate);
		
		if(statusSelect.equals("status3")) {
			dto.setOpenYn("Y");
			dto.setShowYn("Y");
		} else if(statusSelect.equals("status2")) {
			dto.setOpenYn("N");
			dto.setShowYn("Y");
		} else {
			dto.setOpenYn("N");
			dto.setShowYn("N");
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("exhibition", dto);
		
		return service.editExhibition(paramMap);
	}
	
	// 전시회 대표 이미지 수정
	@PostMapping(value={"changeExhibitionImage","changeExhibitionImage.do"})
	public Map<String, Object> changeExhibitionImage(String exhibitionNo, MultipartFile mainImageName) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exhibitionNo", exhibitionNo);
		paramMap.put("mainImage", mainImageName);
		
		return service.changeExhibitionImage(paramMap);
	}
	
	/*
	 * 전시회 작품 조회
	 * status == 'true'이면 전시회에 등록된 작품들을 조회
	 * 아니면 해당 작가의 해당 전시회에 등록되지 않은 작품들을 조회한다.
	 */
	@GetMapping(value= {"artworkList","artworkList.do"})
	public Map<String, Object> artworkList(String exhibitionNo, String artistNo, String status) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exhibitionNo", exhibitionNo);
		paramMap.put("artistNo", artistNo);
		paramMap.put("status", status);
		
		return service.artworkList(paramMap);
	}
	
	@RequestMapping(value={"exhibitionInfo","exhibitionInfo.do"})
	private ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		
		System.out.println("ExhibitionController exhibitionInfo() : exhibitionNo=" + exhibitionNo);
		ExhibitionDTO dto = service.exhibitionInfo(exhibitionNo);

		return dto;
	}
	
	@RequestMapping(value={"crrentList","crrentList.do"})
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
	
	@GetMapping(value={"currentCount","currentCount.do"})
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
	
	@RequestMapping(value={"pastList","pastList.do"})
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
	
	
	@GetMapping(value={"pastCount","pastCount.do"})
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
	
	@RequestMapping(value={"exArtwork","exArtwork.do"})
	private List<ArtworkDTO> exArtwork(String exhibitionNo) {
		
		System.out.println("ExhibitionController exArtwowrk()");
		List<ArtworkDTO> List = service.exArtwowrk(exhibitionNo);
		
		return List;
	}
	
	
	
	
	
	
}
