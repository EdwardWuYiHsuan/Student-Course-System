package com.poseitech.assignment.dao;

import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;

public interface StudentProjectGradeDao {

	public StudentProjectGrade addProjectToStudent(Student student, Project project) throws Exception;
	
	public StudentProjectGrade markGradeToStudentProject(Student student, Project project, Grade grade) throws Exception;
	
}
