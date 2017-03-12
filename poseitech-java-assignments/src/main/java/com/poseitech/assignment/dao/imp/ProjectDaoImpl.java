package com.poseitech.assignment.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.entity.Project;

@Repository
@Transactional
public class ProjectDaoImpl implements ProjectDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Project findById(Long id) 
	{
		Object project = getSession().get(Project.class, id);
		if (null != project)
			return (Project) project;
		
		return null;
	}
	
	@Override
	public Project findByProjectName(String name) 
	{
		Criteria criteria = getSession().createCriteria(Project.class)
										.add(Restrictions.eq("name", name));
		
		Object project = criteria.uniqueResult();
		if (null != project) 
			return (Project) project;
		
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Project> findAll() 
	{
		Criteria criteria = getSession().createCriteria(Project.class);
		
		return (List<Project>) criteria.list();
	}

	@Override
	public Project saveOrUpdate(Project project) 
	{
		getSession().saveOrUpdate(project);
		
		return project;
	}

	@Override
	public void delete(Long id) 
	{
		Object project = getSession().get(Project.class, id);
		if (null != project) {
			getSession().delete(project);
		}
	}
	
	private Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}

}
