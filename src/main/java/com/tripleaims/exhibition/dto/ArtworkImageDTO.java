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
public class ArtworkImageDTO {

	private String artworkImageNo;
	private String artworkNo;
	private int groupOrder;
	private String fileName;
	private String thum1Name;
	private String thum2Name;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	
}
