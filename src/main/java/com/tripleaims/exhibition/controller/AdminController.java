package com.tripleaims.exhibition.controller;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tripleaims.exhibition.command.AdminSelectListArtworkCategoryCommand;
import com.tripleaims.exhibition.command.AdminSelectListArtworkCommand;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("admin")
@ResponseBody
@AllArgsConstructor
public class AdminController {
	
	// SqlSession
	private SqlSession sqlSession;
	
	// Command
	private AdminSelectListArtworkCategoryCommand adminSelectListArtworkCategoryCommand;
	private AdminSelectListArtworkCommand adminSelectListArtworkCommand;

	@GetMapping("test.do")
	public String test() {
		return "<script>alert('HELLO! BACK WORLD.');</script>";
	}
	
	@GetMapping("getCategories.do")
	public Map<String, Object> getCategories(Model model) {
		Map<String, Object> returnMessage = adminSelectListArtworkCategoryCommand.execute(sqlSession, model);
		return returnMessage;
	}
	
}
