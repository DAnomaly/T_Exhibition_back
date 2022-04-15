package com.tripleaims.exhibition.dto;

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

public class PagingParam {

	private String search;
	private int page;
	private int start;
	private int end;
	
}
