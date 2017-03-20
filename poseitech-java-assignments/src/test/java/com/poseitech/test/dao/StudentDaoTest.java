package com.poseitech.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentDaoTest extends AbstractTest {
	 
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private GradeDao gradeDao;
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;
	private long studentId;
	private long projectId;
	private Date birthday;
	private Date registerDate;
	
	
	@Before
	public void before() throws Exception 
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
		
		// [Bug] oneToMany return emtpy collection : http://stackoverflow.com/questions/22011757/hibernate-does-not-load-one-to-many-relationships-sets-even-with-eager-fetch
//		studentRes.addStudentProjectGrade(spg);
//		studentDao.saveOrUpdate(studentRes);
//		
//		projectRes.addStudentProjectGrade(spg);
//		projectDao.saveOrUpdate(projectRes);
//		
//		gradeRes.addStudentProjectGrade(spg);
//		gradeDao.saveOrUpdate(gradeRes);
	}
 
	@Test
	public void findByAll() 
	{
		List<Student> students = studentDao.findAll();
	 
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
	}
 
	@Test
	public void findById() throws Exception 
	{
		Student student = studentDao.findById(Long.valueOf(studentId).intValue());
	 
		assertNotNull(student);
		assertEquals(studentId, student.getId().longValue());
		assertEquals(student.getName(), "student");
		assertEquals(student.getRemark(), "remark");
	}
	
	@Test
	public void findAllStudents() throws Exception 
	{
		List<Student> students = studentDao.findAllStudents(0, 1);
		
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
		
		Student student = students.get(0);
		assertEquals(studentId, student.getId().longValue());
		assertEquals(student.getName(), "student");
		assertEquals(student.getRemark(), "remark");
	}
	
	@Test
	public void findStudentByProjectId() throws Exception 
	{
		List<Student> students = studentDao.findStudentByProjectId(Long.valueOf(projectId).intValue());
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
		
		Student studentRes = students.get(0);
		assertEquals(studentId, studentRes.getId().longValue());
		assertEquals(studentRes.getName(), "student");
		assertEquals(studentRes.getRemark(), "remark");
	}
	
	@Test
	public void findByHql() throws Exception
	{
		//from Student as student where student.name = ?
		List<Student> students = studentDao.findByHql("from Student as student where student.name = ?", 0, 1, new Object[]{"student"});
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
		
		Student student = students.get(0);
		assertEquals(studentId, student.getId().longValue());
		assertEquals(student.getName(), "student");
		assertEquals(student.getRemark(), "remark");
		
		students = studentDao.findByHql("from Student as student where student.registerDate = ?", 0, 1, new Object[]{registerDate});
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
		
		student = students.get(0);
		assertEquals(studentId, student.getId().longValue());
		assertEquals(student.getName(), "student");
		assertEquals(student.getRemark(), "remark");
		
		students = studentDao.findByHql("from Student as student where student.id = ?", 0, 1, new Object[]{studentId});
		Assert.assertFalse(students.isEmpty());
		assertEquals(1, students.size());
		
		student = students.get(0);
		assertEquals(studentId, student.getId().longValue());
		assertEquals(student.getName(), "student");
		assertEquals(student.getRemark(), "remark");
	}
	
	@Test
	public void saveOrUpdate() throws Exception
	{
		Date birth = AssignmentController.dateFormat.parse("20170101");
		Date register = AssignmentController.dateFormat.parse("20000101");
		
		Student student = new Student();
		student.setName("myTestProject");
		student.setRemark("myTestRemark");
		student.setBirthday(birth);
		student.setRegisterDate(register);
		student = studentDao.saveOrUpdate(student);
		
		Student studentRes = studentDao.findById(Long.valueOf(student.getId()).intValue());
		assertNotNull(studentRes);
		assertEquals(student.getId(), studentRes.getId());
		assertEquals(studentRes.getName(), "myTestProject");
		assertEquals(studentRes.getRemark(), "myTestRemark");
		assertEquals(studentRes.getBirthday(), birth);
		assertEquals(studentRes.getRegisterDate(), register);
	}
	
	@Test
	public void delete() throws Exception 
	{
		studentDao.delete(Long.valueOf(studentId).intValue());
		
		Student studentRes = studentDao.findById(Long.valueOf(studentId).intValue());
		assertNull(studentRes);
	}
	
}
