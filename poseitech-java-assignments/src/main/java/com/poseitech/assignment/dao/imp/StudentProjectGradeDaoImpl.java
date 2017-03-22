package com.poseitech.assignment.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;

@Repository
@Transactional
public class StudentProjectGradeDaoImpl implements StudentProjectGradeDao {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private GradeDao gradeDao;
	
	
	@Override
	public StudentProjectGrade addProjectToStudent(Student student, Project project) throws Exception 
	{
		if (null == student.getId())
			throw new IllegalArgumentException("invalid-student-id");
		if (null == project.getId())
			throw new IllegalArgumentException("invalid-project-id");
		
		StudentProjectGrade spg = new StudentProjectGrade();
		spg.setStudent(student);
		spg.setProject(project);
		
		getSession().saveOrUpdate(spg);
		
		student.addStudentProjectGrade(spg);
		studentDao.saveOrUpdate(student);
		
		project.addStudentProjectGrade(spg);
		projectDao.saveOrUpdate(project);

		return spg;
	}
	
	@Override
	public StudentProjectGrade markGradeToStudentProject(Student student, Project project, Grade grade) throws Exception 
	{
		if (null == student || null == student.getId())
			throw new IllegalArgumentException("invalid-student-id");
		if (null == project || null == project.getId())
			throw new IllegalArgumentException("invalid-project-id");
		if (null == grade || null == grade.getLevel())
			throw new IllegalArgumentException("invalid-grade-level");
		
		StudentProjectGrade spg = new StudentProjectGrade();
		spg.setStudent(student);
		spg.setProject(project);
		spg.setGrade(grade);
		
		getSession().saveOrUpdate(spg);
		
		student.addStudentProjectGrade(spg);
		studentDao.saveOrUpdate(student);
		
		project.addStudentProjectGrade(spg);
		projectDao.saveOrUpdate(project);
		
		grade.addStudentProjectGrade(spg);
		gradeDao.saveOrUpdate(grade);
		
		return spg;
	}
	
	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
	
}
