package com.poseitech.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.entity.Project;
import com.poseitech.test.AbstractTest;

@Transactional
public class ProjectDaoTest extends AbstractTest {
	 
	@Autowired
	private ProjectDao projectDao;
	private long projectId;
	
	
	@Before
	public void before() 
	{
		Project project = new Project();
		project.setName("porject");
		project.setRemark("remark");
		Project saveOrUpdate = projectDao.saveOrUpdate(project);
		projectId = saveOrUpdate.getId();
	}
	
	@Test
	public void findByAll() 
	{
		List<Project> projects = projectDao.findAll();
		
		assertFalse(projects.isEmpty());
		assertEquals(1, projects.size());
	}
	
	@Test
	public void findByProjectName() 
	{
		Project project = projectDao.findByProjectName("porject");
	 
		assertNotNull(project);
		assertEquals(project.getName(), "porject");
		assertEquals(project.getRemark(), "remark");
	}
 
	@Test
	public void findById() 
	{
		Project project = projectDao.findById(projectId);
	 
		assertNotNull(project);
		assertEquals(projectId, project.getId().longValue());
		assertEquals(project.getName(), "porject");
		assertEquals(project.getRemark(), "remark");
	}
 
	@Test
	public void saveOrUpdate()
	{
		Project project = new Project();
		project.setName("myTestProject");
		project.setRemark("myTestRemark");
		projectDao.saveOrUpdate(project);
		
		Project resultPrj = projectDao.findByProjectName("myTestProject");
		assertNotNull(resultPrj);
		assertEquals(resultPrj.getName(), "myTestProject");
		assertEquals(resultPrj.getRemark(), "myTestRemark");
	}
 
	@Test
	public void delete()
	{
		projectDao.delete(projectId);
		
		Project resultPrj = projectDao.findById(projectId);
		assertNull(resultPrj);
	}
	 
}
