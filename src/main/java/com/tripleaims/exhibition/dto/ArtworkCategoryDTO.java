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
public class ArtworkCategoryDTO {

	private String categoryNo;
	private String name;
	private String deleteYn;
	private Date regDate;
	
}
