package com.tripleaims.exhibition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.ArtistDAO;
import com.tripleaims.exhibition.dto.ArtistDTO;

@Service
@Transactional
public class ArtistService {
	
	@Autowired
	ArtistDAO dao;
	
	public Map<String, Object> selectList(ArtistDTO dto) {
		List<ArtistDTO> resultList = dao.selectListArtists(dto);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", resultList);
		return resultMap;
	}
	
}
