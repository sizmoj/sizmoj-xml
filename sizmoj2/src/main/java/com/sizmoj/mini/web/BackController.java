package com.sizmoj.mini.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sizmoj.mini.service.AccountService;
import com.sizmoj.mini.service.PostService;

@Controller
@RequestMapping(value = "/back")
public class BackController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "checkLoginName")
	public String login() {
		System.out.println("dsadas");
		return "account/login";
	}
	@RequestMapping(value = "login")
	public String login1(String username, String password,  
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String user = accountService.getUser(username, password);
		if(StringUtils.isBlank(user))
			return "/login";
		request.getSession().setAttribute("isLogin", user);
		return "redirect:main";
	}	
}
