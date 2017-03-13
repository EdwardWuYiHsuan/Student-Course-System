package com.poseitech.assignment.dao.imp;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

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
	public void addProjectToStudent(Student student, Set<Project> projects) throws Exception 
	{
		for (Project proj : projects) {
			StudentProjectGrade spg = new StudentProjectGrade();
			spg.setStudent(student);
			spg.setProject(proj);
			
			getSession().saveOrUpdate(spg);
		}
	}

	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
}
