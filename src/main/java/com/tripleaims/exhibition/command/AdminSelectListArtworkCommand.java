package com.tripleaims.exhibition.command;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminSelectListArtworkCommand implements CommonCommand{

	@Override
	public Map<String, Object> execute(SqlSession sqlSession, Model model) {
		
		
		
		return null;
	}
	
}
