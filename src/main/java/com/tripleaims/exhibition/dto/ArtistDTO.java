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
public class ArtistDTO {

	private String artistNo;
	private String nameKor;
	private String nameEng;
	private String profile;
	private String birthday;
	private String phones;
	private String emails;
	private String introduce;
	private String history;
	private String openYn;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	private long hit;
	
}
