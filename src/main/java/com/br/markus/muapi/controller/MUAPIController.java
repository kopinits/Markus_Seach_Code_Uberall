package com.br.markus.muapi.controller;

import com.br.markus.muapi.controller.form.SearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes("searchModel")
public class MUAPIController {

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
        model.addAttribute("searchModel", new SearchForm());
		return "index";
	}
}