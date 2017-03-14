package com.poseitech.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.dto.InquireGradeDto;
import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;
import com.poseitech.assignment.mvc.controller.AssignmentController;
import com.poseitech.assignment.service.AssignmentService;
import com.poseitech.test.AbstractTest;

@Transactional
public class AssignmentServiceTest extends AbstractTest {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private GradeDao gradeDao;
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;
	@Autowired
	private AssignmentService assignmentService;
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
		StudentDto dto = assignmentService.getStudentById(Long.valueOf(studentId).intValue());
		
		assertNotNull(dto);
		assertEquals("student", dto.getName());
		assertEquals("remark", dto.getRemark());
		assertEquals(birthday, dto.getBirthday());
		assertEquals(registerDate, dto.getRegisterDate());
	}
	
	@Test
	public void getAllStudents()  throws Exception
	{
		List<StudentDto> students = assignmentService.getAllStudents(0, 1);
		assertFalse(students.isEmpty());
		
		StudentDto dto = students.get(0);
		assertNotNull(dto);
		assertEquals("student", dto.getName());
		assertEquals("remark", dto.getRemark());
		assertEquals(birthday, dto.getBirthday());
		assertEquals(registerDate, dto.getRegisterDate());
		
		students = assignmentService.getAllStudents(1, 1);
		assertTrue(students.isEmpty());
	}
	
	@Test
	public void getStudentByCondition() throws Exception
	{
		List<StudentDto> students = assignmentService.getStudentByCondition(Long.valueOf(studentId).intValue(), null, null);
		assertFalse(students.isEmpty());
		
		StudentDto dto = students.get(0);
		assertNotNull(dto);
		assertEquals("student", dto.getName());
		assertEquals("remark", dto.getRemark());
		assertEquals(birthday, dto.getBirthday());
		assertEquals(registerDate, dto.getRegisterDate());
		
		students = assignmentService.getStudentByCondition(null, "student", null);
		assertFalse(students.isEmpty());
		
		dto = students.get(0);
		assertNotNull(dto);
		assertEquals("student", dto.getName());
		assertEquals("remark", dto.getRemark());
		assertEquals(birthday, dto.getBirthday());
		assertEquals(registerDate, dto.getRegisterDate());
		
		students = assignmentService.getStudentByCondition(null, null, registerDate);
		assertFalse(students.isEmpty());
		
		dto = students.get(0);
		assertNotNull(dto);
		assertEquals("student", dto.getName());
		assertEquals("remark", dto.getRemark());
		assertEquals(birthday, dto.getBirthday());
		assertEquals(registerDate, dto.getRegisterDate());
	}
	
	@Test
	public void getInterestedProjects() throws Exception
	{
		StudentDto stuDto = new StudentDto();
		stuDto.setId(studentId);
		stuDto.setName("student");
		stuDto.setRemark("remark");
		stuDto.setBirthday(birthday);
		stuDto.setRegisterDate(registerDate);
		List<ProjectDto> projectList = assignmentService.getInterestedProjects(stuDto);
		
		assertFalse(projectList.isEmpty());
		
		ProjectDto project = projectList.get(0);
		assertEquals(projectId, project.getId());
		assertEquals("project", project.getName());
		assertEquals("remark", project.getRemark());
	}
	
	@Test
	public void inquireStudentCountByGrade() throws Exception
	{
		List<InquireGradeDto> inquireGrade = assignmentService.inquireStudentCountByGrade();
		assertFalse(inquireGrade.isEmpty());
		
		InquireGradeDto dto = inquireGrade.get(0);
		assertEquals(1, dto.getCount());
		assertEquals("A", dto.getLevel().name());
		assertEquals("project", dto.getProjectName());
	}
	
	@Test
	public void createStudent() throws Exception
	{
		StudentDto stuDto = new StudentDto();
		stuDto.setName("myTestStudent");
		stuDto.setRemark("myTestRemark");
		stuDto.setBirthday(birthday);
		stuDto.setRegisterDate(registerDate);
		stuDto = assignmentService.createStudent(stuDto);
		
		StudentDto studentRes = assignmentService.getStudentById(Long.valueOf(stuDto.getId()).intValue());
		assertNotNull(studentRes);
		assertEquals("myTestStudent", studentRes.getName());
		assertEquals("myTestRemark", studentRes.getRemark());
		assertEquals(birthday, studentRes.getBirthday());
		assertEquals(registerDate, studentRes.getRegisterDate());
	}

	@Test
	public void createProjects() throws Exception
	{
		ProjectDto prjDto = new ProjectDto();
		prjDto.setName("myTestProject");
		prjDto.setRemark("myTestRemark");
		
		List<ProjectDto> projectList = assignmentService.createProjects(new ArrayList<>(Arrays.asList(prjDto)));
		assertFalse(projectList.isEmpty());
		
		Project projectRes = projectDao.findByProjectName("myTestProject");
		assertNotNull(projectRes);
		assertEquals("myTestProject", projectRes.getName());
		assertEquals("myTestRemark", projectRes.getRemark());
	}
	
	@Test
	public void deleteStudent() throws Exception
	{
		StudentDto stuDto = new StudentDto();
		stuDto.setId(studentId);
		stuDto.setName("student");
		stuDto.setRemark("remark");
		stuDto.setBirthday(birthday);
		stuDto.setRegisterDate(registerDate);
		
		boolean b = assignmentService.deleteStudent(stuDto);
		
		Student studentRes = studentDao.findById(Long.valueOf(studentId).intValue());
		assertNull(studentRes);
	}
	
	@Test
	public void deleteProjects() throws Exception
	{
		ProjectDto prjDto = new ProjectDto();
		prjDto.setId(projectId);
		prjDto.setName("project");
		prjDto.setRemark("remark");
		
		boolean b = assignmentService.deleteProjects(new ArrayList<>(Arrays.asList(prjDto)));
		
		Project projectRes = projectDao.findById(projectId);
		assertNull(projectRes);
	}
	
	@Test
	public void registerProjects() throws Exception
	{
		StudentDto stuDto = new StudentDto();
		stuDto.setName("myTestStudent");
		stuDto.setRemark("myTestRemark");
		stuDto.setBirthday(birthday);
		stuDto.setRegisterDate(registerDate);
		stuDto = assignmentService.createStudent(stuDto);
		
		ProjectDto prjDto = new ProjectDto();
		prjDto.setName("myTestProject");
		prjDto.setRemark("myTestRemark");
		List<ProjectDto> projectList = assignmentService.createProjects(new ArrayList<>(Arrays.asList(prjDto)));
		
		
		StudentDto stu = assignmentService.registerProjects(Long.valueOf(stuDto.getId()).intValue(), projectList);
		List<ProjectDto> projectsRes = assignmentService.getInterestedProjects(stu);
		assertNotNull(projectsRes);
		assertFalse(projectsRes.isEmpty());
		ProjectDto actualPrj = projectsRes.get(0);
		assertEquals("myTestStudent", actualPrj.getName());
		assertEquals("myTestRemark", actualPrj.getRemark());
	}
}














