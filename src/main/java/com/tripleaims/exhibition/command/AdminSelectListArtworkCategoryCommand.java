package com.tripleaims.exhibition.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.tripleaims.exhibition.dao.ArtworkCategoryDAO;
import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;

@Component
public class AdminSelectListArtworkCategoryCommand implements CommonCommand {

	@Override
	public Map<String, Object> execute(SqlSession sqlSession, Model model) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<ArtworkCategoryDTO> list = null;		
		try {
			ArtworkCategoryDAO dao = sqlSession.getMapper(ArtworkCategoryDAO.class);
			list = dao.selectListArtworkCategory();
		} finally {
			if(list != null) {
				returnMap.put("isSuccess", true);
				returnMap.put("result", list);
			} else {
				returnMap.put("isSuccess", false);
				returnMap.put("result", null);
			}
		}
		
		return returnMap;
	}
	
}
