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
@NoArgsConstructor
@AllArgsConstructor
public class AppraiseDTO {

	private String appraiseNo;
	private String name;
	private String phone;
	private String address1;
	private String address2;
	private String experience;
	private String appraiseGroupNo;
	private String company;
	private String deleteYn;
	private Date regDate;
	private Date editDate;

}
