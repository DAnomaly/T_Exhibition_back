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
public class StorageDTO {

	private String storageNo;
	private String placeStorageNo;
	private String address1;
	private String address2;
	private String locationNo;
	private String storageYn;
	private String artworkNo;
	private String artworkTitle;
	private String artistNo;
	private String artistNameKor;
	private String artistNameEng;
	private Date regDate;
	private Date editDate;
	
}
