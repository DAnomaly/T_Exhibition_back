package com.tripleaims.exhibition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.ArtworkCategoryDAO;
import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;

@Service
@Transactional
public class ArtworkCategoryService {
	
	@Autowired
	ArtworkCategoryDAO dao;
	
	// 모든 카테고리 불러오기
	public Map<String, Object> selectAllArtworkCategory() {
		Map<String, Object> resultMap = new HashMap<>();
		
		List<ArtworkCategoryDTO> list = dao.selectAllArtworkCategory();
		resultMap.put("result", list);
		
		return resultMap;
	}
	
	
}
