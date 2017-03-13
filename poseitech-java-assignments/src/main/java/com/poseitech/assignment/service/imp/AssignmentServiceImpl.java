package com.poseitech.assignment.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.enumeration.APICode;
import com.poseitech.assignment.exception.ApiException;
import com.poseitech.assignment.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
	
	@Autowired
	private StudentDao studentDao;

	@Override
	public StudentDto createStudent(StudentDto pNewStudent) throws Exception 
	{
		return null;
	}

	@Override
	public List<StudentDto> getAllStudents(int pStartRowNumber, int pLimit) throws Exception 
	{
		return null;
	}

	@Override
	public boolean deleteStudent(StudentDto pNewStudent) throws Exception 
	{
		return false;
	}

	@Override
	public List<ProjectDto> getInterestedProjects(StudentDto pStudent) throws Exception 
	{
		return null;
	}

	@Override
	public StudentDto registerProjects(Integer pStudendId, List<ProjectDto> pProjects) throws Exception 
	{
		return null;
	}

	@Override
	public List<ProjectDto> createProjects(List<ProjectDto> pProjects) throws Exception 
	{
		return null;
	}

	@Override
	public boolean deleteProjects(List<ProjectDto> pProjects) throws Exception 
	{
		return false;
	}

	@Override
	public StudentDto getStudentById(int studentId) throws Exception 
	{
		Student student = studentDao.findById(studentId);
		if (null == student)
			throw new ApiException(APICode.InvalidParameter, "invalid-student-id");
		
		return student.convertStudentDto();
	}
	
}


