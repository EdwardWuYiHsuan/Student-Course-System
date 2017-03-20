package com.poseitech.assignment.dto;

import com.poseitech.assignment.entity.Grade;

public class GradeDto {

	private char level;
	private String remark;
	
	
	public char getLevel() {
		return level;
	}
	public void setLevel(char level) {
		this.level = level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Grade convertGradeEntity()
	{
		Grade grade = new Grade();
		grade.setLevel(this.getLevel());
		grade.setRemark(this.getRemark());
		
		return grade;
	}
	
}
