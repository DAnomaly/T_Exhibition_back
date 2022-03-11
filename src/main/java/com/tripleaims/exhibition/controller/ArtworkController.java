package com.tripleaims.exhibition.controller;

import java.sql.Date;
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
import com.tripleaims.exhibition.service.ArtworkService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("artwork")
@AllArgsConstructor
@CrossOrigin("*")
public class ArtworkController {

	private ArtworkService service;
	
	@GetMapping("selectArtwork.do")
	public Map<String, Object> selectArtwork(String artworkName, String artistName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("artworkName", artworkName);
		paramMap.put("artistName", artistName);
		return service.selectAllArtwork(paramMap);
	}
	
	@GetMapping("getAllCategory.do")
	public Map<String, Object> selectAll() {
		return service.selectAllArtworkCategory();
	}
	
	@PostMapping("insertArtwork.do")
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
	
	@PostMapping("selectOneArtwork.do") 
	public Map<String, Object> selectOneArtwork(String artworkNo) {
		return service.selectOneArtwork(artworkNo);
	}
	
	@PostMapping("updateArtworkConfig.do")
	public String updateArtworkConfig(ArtworkDTO artworkDTO, @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date madenDate, boolean openY) {

		if(madenDate != null) {
			artworkDTO.setArtworkDate(new Date(madenDate.getTime()));
		}
		artworkDTO.setShowYn(openY ? "Y" : "N");
		
		return service.updateArtwork(artworkDTO);
	}
	
	
}
