package com.tripleaims.exhibition.dto;

import java.util.Date;

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
	private String mainImageName;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private Date startDate;
	private String openYn;
	private String showYn;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	private int hit;
	private String artworkTitle;
	private int orderNo; // exhibition_artwork : ORDER_NO
}
