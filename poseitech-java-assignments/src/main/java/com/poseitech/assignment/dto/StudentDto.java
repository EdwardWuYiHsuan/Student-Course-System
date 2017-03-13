package com.poseitech.assignment.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.poseitech.assignment.entity.Student;

public class StudentDto implements Serializable {

   private static final long serialVersionUID = 3209888691671533902L;
   
   private long id;
   private String name;
   private Date birthday;
   private Date registerDate;
   private String remark;
   private List<ProjectDto> interestedProjects;
   
   
   public long getId() {
	   return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<ProjectDto> getInterestedProjects() {
		return interestedProjects;
	}

   public void setInterestedProjects(List<ProjectDto> pInterestedProjects) {
      interestedProjects = pInterestedProjects;
   }
   
   public void addInterestedProjects(ProjectDto projectDto) 
   {
	   if (null == interestedProjects)
		   interestedProjects = new ArrayList<>();
	   
	   interestedProjects.add(projectDto);
   }
   
   public Student convertStudentEntity() 
   {
	   Student student = new Student();
	   student.setName(this.getName());
	   student.setBirthday(this.getBirthday());
	   student.setRegisterDate(this.getRegisterDate());
	   student.setRemark(this.getRemark());
	   
	   return student;
   }

}
