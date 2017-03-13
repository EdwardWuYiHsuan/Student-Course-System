package com.poseitech.assignment.dto;

import com.poseitech.assignment.enumeration.GradeLevel;

public class InquireGradeDto {

	private GradeLevel level;
	private String projectName;
	private int count;
	
	public GradeLevel getLevel() {
		return level;
	}
	
	public void setLevel(GradeLevel level) {
		this.level = level;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
}
