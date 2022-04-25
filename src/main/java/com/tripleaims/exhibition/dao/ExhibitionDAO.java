package com.tripleaims.exhibition.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;
import com.tripleaims.exhibition.dto.PagingParam;

@Mapper
@Repository
public interface ExhibitionDAO {

	public List<ExhibitionDTO> selectExhibition(Map<String, Object> paramMap);
	public String selectMaxExhibitionNo();
	public boolean insertExhibition(ExhibitionDTO dto);
	public boolean insertExhibitionArtwork(ExhibitionArtworkDTO dto);
	public boolean updateExhibition(ExhibitionDTO dto);
	public boolean updateExhibitionMainImage(String mainImageName, String exhibitionNo);
	
	public ExhibitionDTO exhibitionInfo(String exhibitionNo);
	
	public List<ExhibitionDTO> crrentList(PagingParam dto);
	int currentCount(PagingParam pram);
	
	public List<ExhibitionDTO> pastList(PagingParam dto);
	int pastCount(PagingParam pram);
	
	public List<ArtworkDTO> exArtwowrk(String exhibitionNo);
	
	// 해당 전시회의 작품목록
	public List<ArtworkDTO> selectExhibitionArtworks(String exhibitionNo, String artistNo);
	// 해당 작가의 해당 전시회에 등록되지 않은 작품목록
	public List<ArtworkDTO> selectNotExhibitionArtworks(String exhibitionNo, String artistNo);
	
}
