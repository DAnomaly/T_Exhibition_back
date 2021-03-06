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
public class ArtistAddressDTO {

	private int artistAddressNo;
	private String artistNo;
	private String addressName;
	private String addressn;
	private String address1;
	private String address2;
	private Date regDate;
	private Date editDate;
	
}
