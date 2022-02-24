package com.tripleaims.exhibition.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dto.ArtistDTO;
import com.tripleaims.exhibition.service.ArtistService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("artist")
@AllArgsConstructor
@CrossOrigin("*")
public class ArtistController {
	
	private ArtistService service;
	
	@PostMapping("add.do")
	public String add(
				  String nameKor
				, String nameEng
				, String birthday
				, MultipartFile profile
				, String[] phone
				, String[] addr_1
				, String[] addr_2
				, String[] addr_3
				, String[] addr_4
				, String[] addr_5
				, String introduce
				, String history
			) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nameKor", nameKor);
		paramMap.put("nameEng", nameEng);
		paramMap.put("birthday", birthday);
		paramMap.put("profile", profile);
		paramMap.put("introduce", introduce);
		paramMap.put("history", history);		
		paramMap.put("phone", phone);
		
		List<String[]> addrList = new ArrayList<>();
		addrList.add(addr_1);
		addrList.add(addr_2);
		addrList.add(addr_3);
		addrList.add(addr_4);
		addrList.add(addr_5);
		paramMap.put("addrList", addrList);

		System.out.println(paramMap);
		
		return service.add(paramMap);
	}
	
	@PostMapping("selectList.do")
	public Map<String, Object> selectList(ArtistDTO dto) {
		return service.selectList(dto);
	}
	
}

