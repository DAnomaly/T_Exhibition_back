package com.tripleaims.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("exhibition")
@ResponseBody
public class ExhibitionController {

	@RequestMapping("test.do")
	public String test() {
		return "<script>alert('HELLO! BACK WORLD.');</script>";
	}
	
}
