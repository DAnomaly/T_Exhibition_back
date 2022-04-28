package com.tripleaims.exhibition.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ArtworkImageDTO;
import com.tripleaims.exhibition.service.ArtworkService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("artwork")
@AllArgsConstructor
@CrossOrigin("*")
public class ArtworkController {

	private ArtworkService service;
	
	@RequestMapping(value={"selectArtwork","selectArtwork.do"})
	public Map<String, Object> selectArtwork(String artworkName, String artistName, String artistNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("artworkName", artworkName);
		paramMap.put("artistName", artistName);
		paramMap.put("artistNo", artistNo);
		return service.selectAllArtwork(paramMap);
	}
	
	@RequestMapping(value={"getAllCategory","getAllCategory.do"})
	public Map<String, Object> selectAll() {
		return service.selectAllArtworkCategory();
	}
	
	@PostMapping(value={"insertArtwork","insertArtwork.do"})
	public String insertArtwork(ArtworkDTO artworkDTO, @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date madenDate, boolean openY, List<MultipartFile> images) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(madenDate != null) {
			artworkDTO.setArtworkDate(new Date(madenDate.getTime()));
		}
		artworkDTO.setShowYn(openY ? "Y" : "N");
		paramMap.put("artworkDTO", artworkDTO);
		paramMap.put("images", images);
		
		return service.insertArtwork(paramMap);
	}

	@PostMapping(value={"selectOneArtwork","selectOneArtwork.do"}) 
	public Map<String, Object> selectOneArtwork(String artworkNo) {
		return service.selectOneArtwork(artworkNo);
	}
	
	@RequestMapping(value={"selectArtworkFromArr","selectArtworkFromArr.do"})
	public Map<String, Object> selectArtworkFromArr(String[] arr) {
		return service.selectArtworkFromArr(arr);
	}
	
	@PostMapping(value={"updateArtworkConfig","updateArtworkConfig.do"})
	public String updateArtworkConfig(ArtworkDTO artworkDTO, @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date madenDate, boolean openY) {

		if(madenDate != null) {
			artworkDTO.setArtworkDate(new Date(madenDate.getTime()));
		}
		artworkDTO.setShowYn(openY ? "Y" : "N");
		
		return service.updateArtwork(artworkDTO);
	}
	
	@RequestMapping(value={"artistArtwork","artistArtwork.do"})
	public List<ArtworkDTO> artistArtwork(String artistNo) {
		System.out.println("ArtworkController artistArtwork()");
		List<ArtworkDTO> list = service.artistArtwork(artistNo);
		return list;
	}
	
	@RequestMapping(value={"artworkImage","artworkImage.do"})
	public List<ArtworkImageDTO> artworkImage(String artworkNo) {
		System.out.println("ArtworkController artworkImage()");
		
		List<ArtworkImageDTO> list = service.artworkImage(artworkNo);
		return list;
		
	}
	
	@RequestMapping(value= {"artworkInfo","artworkInfo.do"})
	public ArtworkDTO artworkInfo(String artworkNo) {
		System.out.println("ArtworkController artworkInfo()");
		
		ArtworkDTO dto  = service.artworkInfo(artworkNo);
		return dto;
	}
	
	
	
}
