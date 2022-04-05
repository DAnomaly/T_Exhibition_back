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
public class ExhibitionArtworkDTO {

	private String exhibitionArtworkNo;
	private String exhibitionNo;
	private String artworkNo;
	private int orderNo;
	private Date regDate;
	
}
