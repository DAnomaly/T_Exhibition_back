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
public class ArtworkImageDTO {

	private String artworkImageNo;
	private String artworkNo;
	private int groupOrder;
	private String filename;
	private String thum1Name;
	private String thum2Name;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	
}
