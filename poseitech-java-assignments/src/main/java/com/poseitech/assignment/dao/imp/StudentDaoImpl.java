package com.poseitech.assignment.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.entity.Student;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Student saveOrUpdate(Student pStudent) throws Exception 
	{
		getSession().saveOrUpdate(pStudent);
		
		return pStudent;
	}

	@Override
	public Student findById(Integer pStudentId) throws Exception 
	{
		return (Student) getSession().get(Student.class, pStudentId.longValue());
	}

	@Override
	public List<Student> findStudentByProjectId(Integer pProjectId) throws Exception 
	{
		return null;
	}

	@Override
	public void delete(Integer pStudentId) throws Exception 
	{
		Object student = getSession().get(Student.class, pStudentId.longValue());
		if (null != student) {
			getSession().delete(student);
		}
	}

	@Override
	public List<Student> findAllStudents(int pStartRowNumber, int pFectchSize) throws Exception 
	{
		return null;
	}

	@Override
	public List<Student> findByHql(String pHql, int pStartRowNumber, int pFectchSize, Object... pValues) throws Exception 
	{
		return null;
	}

	@Override
	public List<Student> findByNamedQuery(String pNameQuery, Class<?> clazz, Object[] params, int pStartRowNumber, int pFectchSize) throws Exception 
	{
		return null;
	}
	
	@Override
	public boolean isExist(Long id) 
	{
		return null != getSession().get(Student.class, id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> getList()
	{
		Criteria criteria = getSession().createCriteria(Student.class);
		
		return (List<Student>) criteria.list();
	}
	
	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
	
}
