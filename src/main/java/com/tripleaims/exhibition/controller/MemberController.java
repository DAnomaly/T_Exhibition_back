package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
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
	public MemberDTO login(String id, String pw) {
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
	
	
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	public String idcheck(String id) {
		System.out.println("MemberController idcheck()");
		boolean b = service.idchek(id);
		if(b ==  true) {
			return "N";
		}else {
			return "Y";
		}
	}
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(MemberDTO dto) {
		System.out.println("MemberController signup()");
		
		String password = dto.getPw();
		String pass = securityUtil.encodePassword(password);
		dto.setPw(pass);
		
		boolean b = service.signup(dto);
		if(b) {
			return "Y";
		}else {
			return "N";
		}
	}
	
	
	
	@RequestMapping(value = "/personalUpdate", method = RequestMethod.POST)
	public MemberDTO personalUpdate(MemberDTO dto) {
		
		System.out.println("MemberController personalUpdate()");
		MemberDTO mem = null;
		boolean b = service.personalUpdate(dto);
		if(b) {
			mem = service.getMember(dto);
		}
			return mem;
	}
	
	

	@RequestMapping(value = "/addressUpdate", method = RequestMethod.POST)
	public MemberDTO addressUpdate(MemberDTO dto) {
		
		System.out.println("MemberController addressUpdate()");
		MemberDTO mem = null;
		boolean b = service.addressUpdate(dto);
		if(b) {
			mem = service.getMember(dto);
		}
		return mem;
	}
	
	
	@RequestMapping(value = "/passUpdate", method = RequestMethod.POST)
	public MemberDTO passUpdate(MemberDTO dto) {
		
		System.out.println("MemberController passUpdate()");
		
		String password = dto.getPw();
		String pass = securityUtil.encodePassword(password);
		dto.setPw(pass);
		
		MemberDTO mem = null;
		boolean b = service.passUpdate(dto);
		if(b) {
			mem = service.getMember(dto);
		}
		return mem;
	}
	

}


