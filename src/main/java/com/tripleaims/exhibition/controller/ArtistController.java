package com.tripleaims.exhibition.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping("insert.do")
	public String insert(
				  String nameKor
				, String nameEng
				, @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date birthday
				, MultipartFile profile
				, String[] phone
				, String[] addr_1
				, String[] addr_2
				, String[] addr_3
				, String[] addr_4
				, String[] addr_5
				, String[] email
				, String introduce
				, String history
				, boolean openY
			) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nameKor", nameKor);
		paramMap.put("nameEng", nameEng);
		if(birthday != null) paramMap.put("birthday", new Date(birthday.getTime()));
		paramMap.put("profile", profile);
		paramMap.put("introduce", introduce);
		paramMap.put("history", history);		
		paramMap.put("phone", phone);
		paramMap.put("email", email);
		paramMap.put("openY", openY);
		
		List<String[]> addrList = new ArrayList<>();
		if(addr_1 != null && addr_1.length != 0)
			if(addr_1[1] != null && !addr_1[1].isBlank())
				addrList.add(addr_1);
		if(addr_2 != null && addr_2.length != 0)
			if(addr_2[1] != null && !addr_2[1].isBlank())
				addrList.add(addr_2);
		if(addr_3 != null && addr_3.length != 0)
			if(addr_3[1] != null && !addr_3[1].isBlank())
				addrList.add(addr_3);
		if(addr_4 != null && addr_4.length != 0)
			if(addr_4[1] != null && !addr_4[1].isBlank())
				addrList.add(addr_4);
		if(addr_5 != null && addr_5.length != 0)
			if(addr_5[1] != null && !addr_5[1].isBlank())
				addrList.add(addr_5);
		paramMap.put("addrList", addrList);

		return service.insert(paramMap);
	}
	
	@PostMapping("selectList.do")
	public Map<String, Object> selectList(ArtistDTO dto) {
		return service.selectList(dto);
	}
	
	
}

