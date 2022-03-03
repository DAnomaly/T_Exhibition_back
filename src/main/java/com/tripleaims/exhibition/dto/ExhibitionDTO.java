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
public class ExhibitionDTO {

	private String exhibitionNo;
	private String title;
	private String content;
	private String mainArtworkNo;
	private String mainImageTitle;
	private String mainImageNo;
	private String mainImageName;
	private String mainThum1Name;
	private String mainThum2Name;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private Date startDate;
	private String openYn;
	private String showYn;
	private String deleteYn;
	private Date regDate;
	private Date eidtDate;
	private int hit;
	
}
