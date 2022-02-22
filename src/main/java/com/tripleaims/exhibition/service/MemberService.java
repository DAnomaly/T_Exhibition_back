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
		return mem;
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
