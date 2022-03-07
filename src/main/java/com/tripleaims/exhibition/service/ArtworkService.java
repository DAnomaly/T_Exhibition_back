package com.tripleaims.exhibition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.ArtworkDAO;
import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;
import com.tripleaims.exhibition.dto.ArtworkDTO;

@Service
@Transactional
public class ArtworkService {

	@Autowired
	ArtworkDAO dao;
	
	public Map<String, Object> selectAllArtwork() {
		Map<String, Object> resultMap = new HashMap<>();

		List<ArtworkDTO> list = dao.selectAllArtwork();
		resultMap.put("result", list);
		
		return resultMap;
	}
	
	public Map<String, Object> selectAllArtworkCategory() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ArtworkCategoryDTO> list = dao.selectAllArtworkCategroy();
		resultMap.put("result", list);
		
		return resultMap;
	}

	public Map<String, Object> insertArtwork(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		
		
		
		return null;
	}
	
}
