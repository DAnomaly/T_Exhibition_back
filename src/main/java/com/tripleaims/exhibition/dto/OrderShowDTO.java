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
public class OrderShowDTO {

	private String orderNo;
	private String artworkNo;
	private String artworkTitle;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private String mainImageNo;
	private String mainImageName;
	private String mainThum1Name;
	private String mainThum2Name;
	private String memberNo;
	private String showPlaceNo;
	private String address1;
	private String address2;
	private String comment;
	private String name;
	private String phone;
	private String email;
	private long price;
	private String status;
	private String showYn;
	private Date requiredDate;
	private Date showDate;
	private Date cancelDate;
	
}
