package com.tripleaims.exhibition.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	private String memberno;
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String email;
	private Date birthday;
	private String address1;
	private String address2;
	private String mvpYn;
	private String managerYn;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	
}
