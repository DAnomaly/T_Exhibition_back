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
public class PlaceStorageDTO {

	private String placeStorageNo;
	private String name;
	private String address1;
	private String address2;
	private String maxArtwork;
	private String deleteYn;
	private Date regDate;
	private Date editDate;
	
}
