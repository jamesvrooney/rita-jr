package com.dealchecker.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dealchecker.model.DeleteMeClass;

@Controller
public class HelloWorldController {
	
	@Autowired
	public DeleteMeClass injectedBean;

	@RequestMapping("/hello-world")
	public void helloWorld(HttpServletResponse response) throws IOException {
		response.getWriter().println("Hello World!");
		response.getWriter().println("Bean value is: " + injectedBean.getValue());
	}
}
