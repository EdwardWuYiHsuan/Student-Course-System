package com.poseitech.assignment.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.entity.Grade;

@Repository
@Transactional
public class GradeDaoImpl implements GradeDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Grade findByLevel(Character level) 
	{
		Object grade = getSession().get(Grade.class, level);
		if (null != grade)
			return (Grade) grade;
		
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Grade> findAll() 
	{
		Criteria criteria = getSession().createCriteria(Grade.class);
		
		return (List<Grade>) criteria.list();
	}

	@Override
	public Grade saveOrUpdate(Grade grade) 
	{
		getSession().saveOrUpdate(grade);
		
		return grade;
	}

	@Override
	public void delete(Character level) 
	{
		Object grade = getSession().get(Grade.class, level);
		if (null != grade) {
			getSession().delete(grade);
		}

	}
	
	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}


}
