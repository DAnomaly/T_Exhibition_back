package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripleaims.exhibition.dto.ArtistDTO;
import com.tripleaims.exhibition.service.ArtistService;
import com.tripleaims.exhibition.util.ExhibitionConfig;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("artist")
@AllArgsConstructor
@CrossOrigin("*")
public class ArtistController {
	
	private ArtistService service;
	
	@GetMapping("write.do")
	public String writeArtist() {
		return "redirect:" + ExhibitionConfig.REFERER_URL + "/admin/artist-list.html";
	}
	
	@ResponseBody
	@PostMapping("selectList.do")
	public Map<String, Object> selectList(ArtistDTO dto) {
		return service.selectList(dto);
	}
	
}
