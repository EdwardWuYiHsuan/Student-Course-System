package com.poseitech.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.mvc.controller.AssignmentController;
import com.poseitech.test.AbstractTest;

@Transactional
public class AssignmentControllerTest extends AbstractTest {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private GradeDao gradeDao;
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;
	@Autowired
	private AssignmentController assignmentController;
	private long studentId;
	private long projectId;
	private Date birthday;
	private Date registerDate;
	
	@Before
	public void setUp() throws Exception 
	{
		birthday = AssignmentController.dateFormat.parse("20070312");
		registerDate = AssignmentController.dateFormat.parse("20170312");
		
		Student student = new Student();
		student.setName("student");
		student.setRemark("remark");
		student.setBirthday(birthday);
		student.setRegisterDate(registerDate);
		Student studentRes = studentDao.saveOrUpdate(student);
		studentId = studentRes.getId();
		
		Project project = new Project();
		project.setName("project");
		project.setRemark("remark");
		Project projectRes = projectDao.saveOrUpdate(project);
		projectId = projectRes.getId();
		
		Grade grade = new Grade();
		grade.setLevel('A');
		grade.setRemark("remark");
		Grade gradeRes = gradeDao.saveOrUpdate(grade);
		
		studentProjectGradeDao.markGradeToStudentProject(studentRes, projectRes, gradeRes);
	}
	
	@Test
	public void getStudentById() throws Exception 
	{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(get("/assignments/api/v1/students/" + studentId));
		
		result.andExpect(status().isOk())
			  .andExpect(content().contentType("application/json;charset=UTF-8"))
			  .andExpect(jsonPath("result").value("ok"))
		 	  .andExpect(jsonPath("data.id").value(Long.valueOf(studentId).intValue()))
		 	  .andExpect(jsonPath("data.name").value("student"))
		 	  .andExpect(jsonPath("data.birthday").value(birthday.getTime()))
		 	  .andExpect(jsonPath("data.registerDate").value(registerDate.getTime()))
		 	  .andExpect(jsonPath("data.remark").value("remark"));
	}
	
	@Test
	public void getStudentByCondition() throws Exception
	{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(post("/assignments/api/v1/students")
									  .param("method", "r").param("name", "student"));
		
		result.andExpect(status().isOk())
		  	  .andExpect(content().contentType("application/json;charset=UTF-8"))
		  	  .andExpect(jsonPath("result").value("ok"))
		  	  .andExpect(jsonPath("data[0].id").value(Long.valueOf(studentId).intValue()))
		  	  .andExpect(jsonPath("data[0].name").value("student"))
		  	  .andExpect(jsonPath("data[0].birthday").value(birthday.getTime()))
		  	  .andExpect(jsonPath("data[0].registerDate").value(registerDate.getTime()))
		  	  .andExpect(jsonPath("data[0].remark").value("remark"));
	}
	
	@Test
	public void getAllStudent_Get() throws Exception
	{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(get("/assignments/api/v1/students").param("start", "0")
				  																  .param("limit", "1"));
		
		result.andExpect(status().isOk())
			  .andExpect(content().contentType("application/json;charset=UTF-8"))
			  .andExpect(jsonPath("result").value("ok"))
			  .andExpect(jsonPath("data[0].id").value(Long.valueOf(studentId).intValue()))
	  	  	  .andExpect(jsonPath("data[0].name").value("student"))
	  	  	  .andExpect(jsonPath("data[0].birthday").value(birthday.getTime()))
	  	  	  .andExpect(jsonPath("data[0].registerDate").value(registerDate.getTime()))
	  	  	  .andExpect(jsonPath("data[0].remark").value("remark"));
	}
	
	@Test
	public void getAllStudent_Post() throws Exception
	{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(post("/assignments/api/v1/students").param("start", "0")
				  																   .param("limit", "1"));
		
		result.andExpect(status().isOk())
		  	  .andExpect(content().contentType("application/json;charset=UTF-8"))
		  	  .andExpect(jsonPath("result").value("ok"))
		  	  .andExpect(jsonPath("data[0].id").value(Long.valueOf(studentId).intValue()))
		  	  .andExpect(jsonPath("data[0].name").value("student"))
		  	  .andExpect(jsonPath("data[0].birthday").value(birthday.getTime()))
		  	  .andExpect(jsonPath("data[0].registerDate").value(registerDate.getTime()))
		  	  .andExpect(jsonPath("data[0].remark").value("remark"));
	}
	
	@Test
	public void createStudent() throws Exception
	{
		String name = "myTestStudent", remark = "myTestRemark";
		Date _birthday = AssignmentController.dateFormat.parse("20000101"),
			 _register = AssignmentController.dateFormat.parse("20120915");
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(post("/assignments/api/v1/students").param("method", "c")
									  .param("name", "myTestStudent").param("birthday", "20000101")
									  .param("registerDate", "20120915").param("remark", "myTestRemark"));
		
		result.andExpect(status().isOk())
		  	  .andExpect(content().contentType("application/json;charset=UTF-8"))
		  	  .andExpect(jsonPath("result").value("ok"))
		  	  .andExpect(jsonPath("data.name").value("myTestStudent"))
		  	  .andExpect(jsonPath("data.birthday").value(_birthday.getTime()))
		  	  .andExpect(jsonPath("data.registerDate").value(_register.getTime()))
		  	  .andExpect(jsonPath("data.remark").value("myTestRemark"));
	}
	
	@Test
	public void inquireStudentCountByGrade() throws Exception
	{
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.assignmentController).build();
		ResultActions result = mockMvc.perform(get("/assignments/api/v1/students/grades"));
		
		result.andExpect(status().isOk());
	}
	
}
















