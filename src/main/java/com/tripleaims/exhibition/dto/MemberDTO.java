package com.tripleaims.exhibition.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class MemberDTO {

	private String memberno;
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String email;
	private String address1;
	private String address2;
	private String mvpyn;
	private String manageryn;
	private String deleteyn;
	private Date regdate;
	private Date editdate;
	
}
