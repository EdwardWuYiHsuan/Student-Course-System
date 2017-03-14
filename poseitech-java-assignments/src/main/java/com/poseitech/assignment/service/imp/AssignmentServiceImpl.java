package com.poseitech.assignment.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.dto.InquireGradeDto;
import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;
import com.poseitech.assignment.enumeration.APICode;
import com.poseitech.assignment.enumeration.GradeLevel;
import com.poseitech.assignment.exception.ApiException;
import com.poseitech.assignment.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
	
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private GradeDao gradeDao;
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;

	@Override
	public StudentDto createStudent(StudentDto pNewStudent) throws Exception 
	{
		Student studentDto = studentDao.saveOrUpdate(pNewStudent.convertStudentEntity());
		
		return studentDto.convertStudentDto();
	}

	@Override
	public List<StudentDto> getAllStudents(int pStartRowNumber, int pLimit) throws Exception 
	{
		List<Student> students = studentDao.findAllStudents(pStartRowNumber, pLimit);
		
		List<StudentDto> studentDtoList = new ArrayList<>();
		for (Student student : students) {
			studentDtoList.add(student.convertStudentDto());
		}
		
		return studentDtoList;
	}

	@Override
	public boolean deleteStudent(StudentDto pNewStudent) throws Exception 
	{
		studentDao.delete(Long.valueOf(pNewStudent.getId()).intValue());
		return true;
	}

	@Override
	public List<ProjectDto> getInterestedProjects(StudentDto pStudent) throws Exception 
	{
		Student student = studentDao.findById(Long.valueOf(pStudent.getId()).intValue());
		if (null == student)
			throw new ApiException(APICode.InvalidParameter, "invalid-student-id");
		
		List<ProjectDto> projectDtoList = new ArrayList<>();
		for (StudentProjectGrade spg : student.getStudentProjectGrade()) {
			projectDtoList.add(spg.getProject().convertProjectDto());
		}
		
		return projectDtoList;
	}

	@Override
	public StudentDto registerProjects(Integer pStudendId, List<ProjectDto> pProjects) throws Exception 
	{
		try {
			Student student = studentDao.findById(pStudendId);
			if (null == student)
				throw new ApiException(APICode.InvalidParameter, "invalid-student-id");
			
			for (ProjectDto projectDto : pProjects) {
				Project project = projectDao.findById(projectDto.getId());
				if (null == project)
					throw new ApiException(APICode.InvalidParameter, "invalid-project-id");
				
				studentProjectGradeDao.addProjectToStudent(student, project);
			}
			
			student = studentDao.findById(pStudendId);
			
			return student.convertStudentDto();
		} catch (NonUniqueObjectException e) {
			throw new ApiException(APICode.AccessFailed, "student-has-registered-project");
		}
	}

	@Override
	public List<ProjectDto> createProjects(List<ProjectDto> pProjects) throws Exception 
	{
		List<ProjectDto> createResult = new ArrayList<>();
		for (ProjectDto projectDto : pProjects) {
			Project project = projectDao.saveOrUpdate(projectDto.convertProjectEntity());
			createResult.add(project.convertProjectDto());
		}
		
		return createResult;
	}

	@Override
	public boolean deleteProjects(List<ProjectDto> pProjects) throws Exception 
	{
		for (ProjectDto projectDto : pProjects) {
			projectDao.delete(projectDto.getId());
		}
		
		return true;
	}

	@Override
	public StudentDto getStudentById(int studentId) throws Exception 
	{
		Student student = studentDao.findById(studentId);
		if (null == student)
			throw new ApiException(APICode.InvalidParameter, "invalid-student-id");
		
		return student.convertStudentDto();
	}
	
	@Override
	public List<StudentDto> getStudentByCondition(Integer studentId, String name, Date registerDate) throws Exception 
	{
		StringBuilder hql = new StringBuilder("from Student as student ");
		List<Object> params = new ArrayList<>();
		if (null != studentId || null != name || null != registerDate) {
			hql.append("where ");
			
			if (null != studentId) {
				hql.append("student.id = ? and ");
				params.add(studentId);
			}
			
			if (null != name && !name.trim().isEmpty()) {
				hql.append("student.name = ? and ");
				params.add(name);
			}
			
			if (null != registerDate) {
				hql.append("student.registerDate = ? and ");
				params.add(registerDate);
			}
				
			hql.delete(hql.lastIndexOf("and"), hql.length());
		}
		
		List<Student> studentRes = studentDao.findByHql(hql.toString().trim(), -1, -1, params.toArray(new Object[0]));
		
		List<StudentDto> studentList = new ArrayList<>();
		for (Student student : studentRes) {
			studentList.add(student.convertStudentDto());
		}
			
		return studentList;
	}
	
	@Override
	public List<InquireGradeDto> inquireStudentCountByGrade() throws Exception 
	{
		List<InquireGradeDto> result = new ArrayList<InquireGradeDto>();
		
		for (GradeLevel gradeLevel : GradeLevel.values()) {
			Grade grade = gradeDao.findByLevel(gradeLevel.name().charAt(0));
			if (null == grade)
				break;
			
			Map<String, Integer> countStudentWithProject = new HashMap<>();
			for (StudentProjectGrade spg : grade.getStudentCourseGrade()) {
				String projectName = spg.getProject().getName();
				
				if (countStudentWithProject.containsKey(projectName)) {
					int count = countStudentWithProject.get(projectName).intValue();
					
					countStudentWithProject.put(projectName, ++count);
				} else {
					countStudentWithProject.put(projectName, 1);
				}
			}
			
			for (Map.Entry<String, Integer> entry : countStudentWithProject.entrySet()) {
				InquireGradeDto inquireGrade = new InquireGradeDto();
				inquireGrade.setLevel(gradeLevel);
				inquireGrade.setProjectName(entry.getKey());
				inquireGrade.setCount(entry.getValue());
				
				result.add(inquireGrade);
			}
		}
		
		return result;
	}
	
}


