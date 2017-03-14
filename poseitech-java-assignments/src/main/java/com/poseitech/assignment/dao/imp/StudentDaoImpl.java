package com.poseitech.assignment.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ProjectDao projectDao;
	
	
	@Override
	public Student saveOrUpdate(Student pStudent) throws Exception 
	{
		getSession().saveOrUpdate(pStudent);
		
		return pStudent;
	}

	@Override
	public Student findById(Integer pStudentId) throws Exception 
	{
		Object student = getSession().get(Student.class, pStudentId.longValue());
		if (null != student)
			return (Student) student;
		
		return null;
	}

	@Override
	public List<Student> findStudentByProjectId(Integer pProjectId) throws Exception 
	{
		Project project = projectDao.findById(pProjectId.longValue());
		
		List<Student> studentList = new ArrayList<>();
		for (StudentProjectGrade scg : project.getStudentProjectGrade()) {
			studentList.add(scg.getStudent());
		}
		
		return studentList;
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
	@SuppressWarnings("unchecked")
	public List<Student> findAllStudents(int pStartRowNumber, int pFectchSize) throws Exception 
	{
//		Criteria criteria = getSession().createCriteria(Student.class);
		Query query = getSession().createQuery("from Student");
		
		if (pStartRowNumber >= 0)
			query.setFirstResult(pStartRowNumber);
		if (pFectchSize >= 0) 
			query.setMaxResults(pFectchSize);
		
		return (List<Student>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Student> findByHql(String pHql, int pStartRowNumber, int pFectchSize, Object... pValues) throws Exception 
	{
		Query query = getSession().createQuery(pHql);
		for (int i = 0; i < pValues.length; i++) {
			Object value = pValues[i];
			if (value instanceof Character) {
				query.setCharacter(i, (Character) value);
			} else if (value instanceof Boolean) {
				query.setBoolean(i, (Boolean) value);
			} else if (value instanceof Byte) {
				query.setByte(i, (Byte) value);
			} else if (value instanceof Short) {
				query.setShort(i, (Short) value);
			} else if (value instanceof Integer) {
				query.setInteger(i, (Integer) value);
			} else if (value instanceof Long) {
				query.setLong(i, (Long) value);
			} else if (value instanceof Float) {
				query.setFloat(i, (Float) value);
			} else if (value instanceof Double) {
				query.setDouble(i, (Double) value);
			} else if (value instanceof Date) {
				query.setDate(i, (Date) value);
			} else {
				query.setString(i, (String) value);
			}
		}
		
		if (pStartRowNumber >= 0)
			query.setFirstResult(pStartRowNumber);
		
		if (pFectchSize >= 0) 
			query.setMaxResults(pFectchSize);
		
		return (List<Student>) query.list();
	}

	@Override
	public List<Student> findByNamedQuery(String pNameQuery, Class<?> clazz, Object[] params, int pStartRowNumber, int pFectchSize) throws Exception 
	{
		// not clear what second parameter is, explain from feedback letter, this method requirement seem like above method.
		
		return this.findByHql(pNameQuery, pStartRowNumber, pFectchSize, params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> findAll()
	{
		Criteria criteria = getSession().createCriteria(Student.class);
		
		return (List<Student>) criteria.list();
	}
	
	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
	
}
