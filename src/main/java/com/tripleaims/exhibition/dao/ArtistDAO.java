package com.tripleaims.exhibition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.tripleaims.exhibition.dto.ArtistAddressDTO;
import com.tripleaims.exhibition.dto.ArtistDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;
import com.tripleaims.exhibition.dto.PagingParam;

@Mapper
@Repository
public interface ArtistDAO {
	
	public List<ArtistDTO> selectListArtists(ArtistDTO artistDTO);
	public String selectMaxArtistNo();
	public ArtistDTO selectOneArtist(String artistNo);
	public List<ArtistAddressDTO> selectArtistAddress(String artistNo);
	public boolean insertArtist(ArtistDTO artistDTO);
	public boolean insertArtistAddress(ArtistAddressDTO artistAddressDTO);
	public boolean updateArtist(ArtistDTO artistDTO);
	public boolean deleteArtist(String artistNo);
	public boolean deleteArtistAddress(String artistAddressNo);
	
	
	
	public List<ArtistDTO> artistList(PagingParam dto);
	int artistCount(PagingParam pram);
	public ArtistDTO artistDetail(String artistNo);
	public List<ExhibitionDTO>  artExhibition(String artistNo);
	
	
	
}
