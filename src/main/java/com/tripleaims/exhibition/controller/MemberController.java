package com.tripleaims.exhibition.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tripleaims.exhibition.dto.MemberDTO;
import com.tripleaims.exhibition.service.MemberService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class MemberController {

	MemberService service;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private MemberDTO login(String id, String pw) {
		
		System.err.println(id+ "/" + pw);
		System.out.println("MemberController login()");		
		
		MemberDTO dto = service.login(new MemberDTO(null, id, pw, null, null, null, null, null, null, null, null, null, null));
		System.out.println("로그인 정보:" + dto.toString() );
		
		return dto;
	}
	
	
	
	
	
	
	
}