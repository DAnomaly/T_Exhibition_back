package com.tripleaims.exhibition.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.MemberDAO;
import com.tripleaims.exhibition.dto.MemberDTO;
import com.tripleaims.exhibition.util.SecurityUtil;

@Service
@Transactional
public class MemberService {

	@Autowired
	MemberDAO dao;
	@Autowired
	SecurityUtil util;
	
	public MemberDTO login(MemberDTO dto) {	
		MemberDTO mem = dao.login(dto);
		
		if(mem != null && util.isSamePassword(dto.getPw(), mem.getPw())) {
			return mem;
		} else {
			return null;
		}
	}
	
	
	public Boolean  idchek(String id) {
		int n = dao.idcheck(id);
		return n>0?true:false;
	}
	
	
	public boolean signup(MemberDTO dto) {
		int add = dao.signup(dto);
		return add>0?true:false;
	}
	
	
	public MemberDTO  getMember(MemberDTO dto) {
		MemberDTO mem  = dao.getMember(dto);
		return mem;
	}
	
	public boolean personalUpdate(MemberDTO dto) {
		int n = dao.personalUpdate(dto);
		return n>0?true:false;
	}
	
	
	public boolean addressUpdate(MemberDTO dto) {
		int n = dao.addressUpdate(dto);
		return n>0?true:false;
	}
	
	public boolean passUpdate(MemberDTO dto) {
		int n = dao.passUpdate(dto);
		return n>0?true:false;
	}
	
	public Map<String, Object> loginAdmin(MemberDTO dto) {
		
		MemberDTO managerDto = dao.selectOneManager(dto);
		Map<String, Object> resultMap = new HashMap<>();
		if(managerDto != null && util.isSamePassword(dto.getPw(), managerDto.getPw())) {
			resultMap.put("isSuccess", true);
			resultMap.put("memberDto", managerDto);
		} else {
			resultMap.put("isSuccess", false);
			resultMap.put("memberDto", null);
		}
		
		return resultMap;
	}
	
}
