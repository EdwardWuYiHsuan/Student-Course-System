package com.poseitech.assignment.mvc.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.poseitech.assignment.exception.ApiException;
import com.poseitech.assignment.response.DefaultResponse;
import com.poseitech.assignment.response.Result;

public class DefaultController {

	@Autowired
	private HttpServletRequest request;
	
	
	public DefaultResponse renderErrorResponse(Throwable causeEx)
	{
		String apiName = request.getRequestURL().toString(),
			   logCode = UUID.randomUUID().toString().split("-")[0],
			   reason = causeEx.getMessage();
		
		System.out.printf("API [%s],\tLog Code: %s,\tError: %s\n", apiName, logCode, reason);
		System.out.println(ExceptionUtils.getStackTrace(causeEx));
		
		if ((causeEx instanceof ApiException) == false) {
			String convertMsg = String.format("Internal Server Error, Please seek Admin to find root cause in log by log-code '%s'", logCode);
			causeEx = new Throwable(convertMsg, causeEx);
		}
		
		return new DefaultResponse(Result.error, causeEx);
	}
}
