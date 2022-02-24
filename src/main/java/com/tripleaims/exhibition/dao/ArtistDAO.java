package com.tripleaims.exhibition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtistDTO;

@Mapper
@Repository
public interface ArtistDAO {
	
	public Integer countArtists(String param);
	public List<ArtistDTO> selectListArtists(ArtistDTO artistDTO);
	public void insertArtist(ArtistDTO artistDTO);
}
