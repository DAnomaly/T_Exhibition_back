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
public class AppraiseGroupDTO {

	private String appraiseGoupNo;
	private String company;
	private String phone;
	private String address1;
	private String address2;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	
}
