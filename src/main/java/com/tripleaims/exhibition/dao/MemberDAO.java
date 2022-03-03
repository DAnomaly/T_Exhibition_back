package com.tripleaims.exhibition.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.MemberDTO;


@Mapper
@Repository
public interface MemberDAO {

	public MemberDTO login(MemberDTO dto);
	public MemberDTO getMember(MemberDTO dto);
	
	public int  idcheck(String id);
	public int  signup(MemberDTO dto);
	
	public int personalUpdate(MemberDTO dto);
	public int addressUpdate(MemberDTO dto);
	public int passUpdate(MemberDTO dto);
	
	
	
	
}
