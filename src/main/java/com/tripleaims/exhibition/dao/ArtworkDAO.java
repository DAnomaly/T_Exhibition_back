package com.tripleaims.exhibition.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;
import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ArtworkImageDTO;

@Mapper
@Repository
public interface ArtworkDAO {

	public List<ArtworkDTO> selectAllArtwork(Map<String, Object> paramMap);  
	public ArtworkDTO selectOneArtwork(String artworkNo);
	public List<ArtworkCategoryDTO> selectAllArtworkCategroy();
	public List<ArtworkImageDTO> selectArtworkImage(String artworkNo);
	public String selectMaxArtworkNo();
	public String selectMaxArtworkImageNo();
	public boolean insertArtwork(ArtworkDTO artworkDTO);
	public boolean insertArtworkImage(ArtworkImageDTO artworkImageDTO);
	public boolean updateArtworkConfig(ArtworkDTO artworkDTO);
	
	
	public List<ArtworkDTO> artistArtwork(String artistNo);
	
	
	
}
