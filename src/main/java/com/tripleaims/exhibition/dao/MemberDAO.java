package com.tripleaims.exhibition.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.MemberDTO;

@Mapper
@Repository
public interface MemberDAO {

	public MemberDTO login(MemberDTO dto);
	

	
	
	
	
}
