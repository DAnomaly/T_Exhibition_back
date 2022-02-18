package com.tripleaims.exhibition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtworkDTO;

@Mapper
@Repository
public interface ArtworkDAO {

	public List<ArtworkDTO> selectAllArtwork();  
	
	
}
