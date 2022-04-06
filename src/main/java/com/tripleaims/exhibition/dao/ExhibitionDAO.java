package com.tripleaims.exhibition.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;

@Mapper
@Repository
public interface ExhibitionDAO {

	public List<ExhibitionDTO> selectExhibition(Map<String, Object> paramMap);
	public String selectMaxExhibitionNo();
	public boolean insertExhibition(ExhibitionDTO dto);
	public boolean insertExhibitionArtwork(ExhibitionArtworkDTO dto);
	
	public ExhibitionDTO exhibitionInfo(String exhibitionNo);
	public List<ExhibitionDTO> exhibitionList(ExhibitionDTO dto);
	public List<ArtworkDTO> exArtwowrk(String exhibitionNo);
	
}
