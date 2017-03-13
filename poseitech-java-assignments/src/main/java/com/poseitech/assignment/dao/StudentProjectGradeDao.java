package com.poseitech.assignment.dao;

import java.util.Set;

import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;

public interface StudentProjectGradeDao {

	public void addProjectToStudent(Student student, Set<Project> projects) throws Exception;
}
