package com.tripleaims.exhibition.command;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

public interface CommonCommand {

	public Map<String, Object> execute(SqlSession sqlSession, Model model);
	
}
