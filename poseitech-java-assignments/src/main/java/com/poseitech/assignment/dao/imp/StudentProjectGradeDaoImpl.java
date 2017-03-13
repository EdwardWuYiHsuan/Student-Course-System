package com.poseitech.assignment.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;

@Repository
@Transactional
public class StudentProjectGradeDaoImpl implements StudentProjectGradeDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public void addProjectToStudent(Student student, Project project) throws Exception 
	{
		StudentProjectGrade spg = new StudentProjectGrade();
		spg.setStudent(student);
		spg.setProject(project);
		
		getSession().saveOrUpdate(spg);
	}

	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
}
