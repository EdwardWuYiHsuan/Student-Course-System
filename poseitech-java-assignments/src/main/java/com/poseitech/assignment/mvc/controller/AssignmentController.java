package com.poseitech.assignment.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseitech.assignment.dto.InquireGradeDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.enumeration.APICode;
import com.poseitech.assignment.exception.ApiException;
import com.poseitech.assignment.response.DefaultResponse;
import com.poseitech.assignment.service.AssignmentService;

@RestController
@RequestMapping("/assignments/api/v1/students")
public class AssignmentController extends DefaultController {

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	private AssignmentService assignmentService;
	
	
	@RequestMapping(value = "{studentId}", method = RequestMethod.GET)
	public DefaultResponse getStudentById(@PathVariable("studentId") Integer studentId)
	{
		DefaultResponse response = new DefaultResponse();
		try {
			StudentDto studentDto = assignmentService.getStudentById(studentId);
			response.setData(studentDto);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(params = "method=r", method = RequestMethod.POST)
	public DefaultResponse getStudentByCondition(@RequestParam(value = "id", required = false) Integer id,
									  			 @RequestParam(value = "name", required = false) String name,
									  			 @RequestParam(value = "registerDate", required = false) String registerStr)
	{
		DefaultResponse response = new DefaultResponse();
		try {
			Date registerDate;
			try {
				registerDate = dateFormat.parse(registerStr);
			} catch (ParseException e) {
				throw new ApiException(APICode.InvalidParameter, "invalid-register-date-format");
			}
			
			List<StudentDto> studentList = assignmentService.getStudentByCondition(id, name, registerDate);
			response.setData(studentList);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public DefaultResponse getAllStudent(@RequestParam(value = "start") Integer start,
			  				  			 @RequestParam(value = "limit") Integer limit)
	{
		DefaultResponse response = new DefaultResponse();
		try {
			List<StudentDto> studentList = assignmentService.getAllStudents(start, limit);
			response.setData(studentList);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(params = "method=c", method = RequestMethod.POST)
	public DefaultResponse createStudent(@RequestParam(value = "name") String name,
			  				  			 @RequestParam(value = "birthday", required = false) String birthdayStr,
			  				  			 @RequestParam(value = "registerDate") String registerDateStr,
			  				  			 @RequestParam(value = "remark") String remark)
	{
		DefaultResponse response = new DefaultResponse();
		try {
			StudentDto studentDto = new StudentDto();
			studentDto.setName(name);
			studentDto.setRemark(remark);
			
			if (null != birthdayStr) {
				try {
					Date birthdayDate = dateFormat.parse(birthdayStr);
					studentDto.setBirthday(birthdayDate);
				} catch (ParseException e) {
					throw new ApiException(APICode.InvalidParameter, "invalid-birthday-format");
				}
			}
			
			try {
				Date registerDate = dateFormat.parse(registerDateStr);
				studentDto.setRegisterDate(registerDate);
			} catch (ParseException e) {
				throw new ApiException(APICode.InvalidParameter, "invalid-register-date-format");
			}
			
			studentDto = assignmentService.createStudent(studentDto);
			response.setData(studentDto);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "grades", method = RequestMethod.GET)
	public DefaultResponse inquireStudentCountByGrade()
	{
		DefaultResponse response = new DefaultResponse();
		try {
			List<InquireGradeDto> inquireResult = assignmentService.inquireStudentCountByGrade();
			response.setData(inquireResult);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
}
