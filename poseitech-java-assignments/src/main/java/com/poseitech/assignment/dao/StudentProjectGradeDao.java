package com.poseitech.assignment.dao;

import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;

public interface StudentProjectGradeDao {

	public void addProjectToStudent(Student student, Project project) throws Exception;
}
