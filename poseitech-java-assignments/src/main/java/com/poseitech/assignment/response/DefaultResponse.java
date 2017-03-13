package com.poseitech.assignment.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poseitech.assignment.enumeration.APICode;
import com.poseitech.assignment.exception.ApiException;

@JsonInclude(Include.NON_NULL)
public class DefaultResponse {

	@JsonProperty("result")
	private Result result;
	
	@JsonProperty("data")
	private Object data;
	
	@JsonProperty("code")
	private int code;
	
	@JsonProperty("reason")
	private String reason;
	
	
	public DefaultResponse()
	{
		this.result = Result.ok;
		this.code = APICode.OK.getCode();
	}
	
	public DefaultResponse(Result result, Throwable causeEx)
	{
		this.result = result;
		this.reason = causeEx.getMessage();
		
		if (causeEx instanceof ApiException) 
			this.code = ((ApiException) causeEx).getCode();
		else
			this.code = APICode.Other.getCode();
			
	}
	
	public DefaultResponse setData(Object data)
	{
		this.data = data;
		return this;
	}
	
	
}
