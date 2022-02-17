package com.tripleaims.exhibition.dao;

import java.util.List;

import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;

public interface ArtworkCategoryDAO {

	public List<ArtworkCategoryDTO> selectListArtworkCategory();
	
}
