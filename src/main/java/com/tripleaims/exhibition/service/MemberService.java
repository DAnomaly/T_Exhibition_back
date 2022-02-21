package com.tripleaims.exhibition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.MemberDAO;
import com.tripleaims.exhibition.dto.MemberDTO;


@Service
@Transactional
public class MemberService {

	@Autowired
	MemberDAO dao;
	
	public MemberDTO login(MemberDTO dto) {	
		MemberDTO mem = dao.login(dto);
		return mem;
	}
	
	
	
}
