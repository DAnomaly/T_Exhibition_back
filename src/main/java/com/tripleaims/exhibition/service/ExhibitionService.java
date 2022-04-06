package com.tripleaims.exhibition.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripleaims.exhibition.dao.ExhibitionDAO;
import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;
import com.tripleaims.exhibition.util.ExhibitionConfig;

@Service
@Transactional
public class ExhibitionService {

	@Autowired
	ExhibitionDAO dao;

	public Map<String, Object> selectExhibition(Map<String, Object> paramMap) {
		// Get Parameters
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", dao.selectExhibition(paramMap));
		
		return resultMap;
	}

	public String addExhibition(Map<String, Object> paramMap) {
		
		// Get now
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		
		// Get Parameters
		ExhibitionDTO dto = (ExhibitionDTO)paramMap.get("exhibition");
		String[] artworks = ((String)paramMap.get("artworks")).split(",");
		
		// Parameter Check
		/*
		System.out.println("dto: " + dto.toString());
		System.out.print("artworks: ");
		for (String str : artworks) {
			System.out.print(str + " ");
		}
		System.out.println();
		*/
		
		// Setting exhibitionNo 
		String maxExhibitionNo = dao.selectMaxExhibitionNo();
		String exhibitionNo = "";
		if(maxExhibitionNo == null || maxExhibitionNo.isEmpty()) {
			exhibitionNo += "110" + df.format(now) + "00001";
		} else {
			if(maxExhibitionNo.indexOf(df.format(now)) != -1) {
				exhibitionNo += (Long.parseLong(maxExhibitionNo) + 1); 
			} else {
				exhibitionNo += "110" + df.format(now) + "00001";
			}
		}
		dto.setExhibitionNo(exhibitionNo);
		
		// Insert Exhibition
		dao.insertExhibition(dto);
		
		// Insert ExhibitionArtwork
		for (int i = 0; i < artworks.length; i++) {
			String artworkNo = artworks[i];
			ExhibitionArtworkDTO tempDTO = new ExhibitionArtworkDTO();
			tempDTO.setExhibitionArtworkNo(String.format("%s%03d", exhibitionNo, i + 1));
			tempDTO.setExhibitionNo(exhibitionNo);
			tempDTO.setArtworkNo(artworkNo);
			tempDTO.setOrderNo(i + 1);
			
			dao.insertExhibitionArtwork(tempDTO);
		}
		
		// Redirect : exhibition-view.html
		StringBuilder returnSb = new StringBuilder();
		returnSb.append("<script>\n");
		returnSb.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/exhibition-view.html?exhibitionNo=" + exhibitionNo + "');");
		returnSb.append("</script>\n");
		
		return returnSb.toString();
	}
	
	
	public ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		return dao.exhibitionInfo(exhibitionNo);
	}
	
	public List<ExhibitionDTO> exhibitionList(ExhibitionDTO dto) {
		return dao.exhibitionList(dto);
	}
	
	
	public List<ArtworkDTO> exArtwowrk(String exhibitionNo) {
		return dao.exArtwowrk(exhibitionNo);
	}
	
	
	
	
}
