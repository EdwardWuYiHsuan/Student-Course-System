package com.poseitech.assignment.dao;

import java.util.List;

import com.poseitech.assignment.entity.Project;

public interface ProjectDao {

	public boolean isExist(Long id);
	
	public Project findById(Long id);
	
	public Project findByProjectName(String name);
	
	public List<Project> findAll();
	
	public Project saveOrUpdate(Project project);
	
	public void delete(Long id);
}
