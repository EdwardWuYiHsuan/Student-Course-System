package com.poseitech.assignment.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poseitech.assignment.response.DefaultResponse;
import com.poseitech.assignment.response.Result;

public class DefaultController {

	@Autowired
	private HttpServletRequest request;
	
	
	public DefaultResponse renderErrorResponse(Throwable causeEx)
	{
		System.out.printf("API [%s] Error:\t%s\n", request.getRequestURL().toString(), causeEx.getMessage());
		System.out.println(ExceptionUtils.getStackTrace(causeEx));
		
		return new DefaultResponse(Result.error, causeEx);
	}
}
