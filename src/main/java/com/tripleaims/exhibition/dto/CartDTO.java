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
public class CartDTO {

	private int cartNo;
	private String memberNo;
	private String artworkNo;
	private String artworkTitle;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private long price;
	private Date regDate;
	
}
