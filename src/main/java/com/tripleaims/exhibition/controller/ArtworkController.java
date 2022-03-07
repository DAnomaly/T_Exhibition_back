package com.tripleaims.exhibition.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
	
	@GetMapping("getAllArtwork.do")
	public Map<String, Object> getAllArtwork() {
		return service.selectAllArtwork();
	}
	
	@GetMapping("getAllCategory.do")
	public Map<String, Object> selectAll() {
		return service.selectAllArtworkCategory();
	}
	
	@PostMapping("insertArtwork.do")
	public Map<String, Object> insertArtwork(ArtworkDTO artworkDTO, @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date madenDate, boolean openY, MultipartFile images) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(madenDate != null) {
			artworkDTO.setArtworkDate(new Date(madenDate.getTime()));
		}
		artworkDTO.setShowYn(openY ? "Y" : "N");
		paramMap.put("artworkDTO", artworkDTO);
		paramMap.put("images", images);
		// System.out.println(paramMap);
		
		return service.insertArtwork(paramMap);
	}
	
}
