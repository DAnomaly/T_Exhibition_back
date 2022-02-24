package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tripleaims.exhibition.dto.MemberDTO;
import com.tripleaims.exhibition.service.MemberService;
import com.tripleaims.exhibition.util.SecurityUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("member")
@AllArgsConstructor
@CrossOrigin("*")
public class MemberController {

	MemberService service;
	SecurityUtil securityUtil;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private MemberDTO login(String id, String pw) {
		
		System.err.println(id+ "/" + pw);
		System.out.println("MemberController login()");		
		
		MemberDTO dto = service.login(new MemberDTO(null, id, pw, null, null, null, null, null, null, null, null, null, null, null));
		System.out.println("로그인 정보:" + dto.toString() );
		
		return dto;
	}
	
	
	@PostMapping("adminLogin.do")
	public Map<String, Object> adminLogin(MemberDTO memberDTO) {
		return service.loginAdmin(memberDTO);
	}
	
	@GetMapping("getPassword.do")
	public String getPassword() {
		return securityUtil.encodePassword("triple#7870");
	}
	
}
