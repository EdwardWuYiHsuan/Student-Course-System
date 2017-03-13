package com.poseitech.assignment.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.response.DefaultResponse;
import com.poseitech.assignment.service.AssignmentService;

@RestController
@RequestMapping("/assignments/api/v1/students")
public class AssignmentController extends DefaultController {

	@Autowired
	private AssignmentService assignmentService;
	
	
	@RequestMapping(value = "{studentId}", method = RequestMethod.GET)
	public DefaultResponse getStudentById(@PathVariable("studentId") Integer studentId)
	{
		DefaultResponse response;
		try {
			StudentDto studentDto = assignmentService.getStudentById(studentId);
			response = new DefaultResponse().setData(studentDto);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(params = "method=r", method = RequestMethod.POST)
	public void getStudentByCondition(@RequestParam(value = "id", required = false) Integer id,
									  @RequestParam(value = "name", required = false) String name,
									  @RequestParam(value = "registerDate", required = false) String registerDate)
	{
		System.out.println(id);
		System.out.println(name);
		System.out.println(registerDate);
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public void getAllStudent(@RequestParam(value = "start") Integer start,
			  				  @RequestParam(value = "limit") Integer limit)
	{
		System.out.println(start);
		System.out.println(limit);
	}
	
	@RequestMapping(params = "method=c", method = RequestMethod.POST)
	public void createStudent(@RequestParam(value = "name") String name,
			  				  @RequestParam(value = "birthday") String birthday,
			  				  @RequestParam(value = "remark") String remark)
	{
		System.out.println(name);
		System.out.println(birthday);
		System.out.println(remark);
	}
	
	@RequestMapping(value = "grades", method = RequestMethod.GET)
	public void getStudentCountByGrade()
	{
		System.out.println("grades");
	}
	
}
