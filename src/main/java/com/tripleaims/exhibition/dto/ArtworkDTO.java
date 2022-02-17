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
public class ArtworkDTO {

	private String artworkNo;
	private String title;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private String categoryNo;
	private String categoryName;
	private String media;
	private String explain;
	private Date artworkDate;
	private String appraiseNo;
	private String appraiseName;
	private String appraiseComment;
	private long appraiseValue;
	private long sellValue;
	private String orderYn;
	private String orderNo;
	private String showYn;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	private long hit;
	
}
