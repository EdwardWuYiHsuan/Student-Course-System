package com.poseitech.assignment.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.poseitech.assignment.dto.GradeDto;

@Entity
@Table(name = "grade")
public class Grade {
	
	@Id
	@Column(name = "level")
	private Character level;
	
	@Size(max = 100)
	private String remark;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grade")
    private Set<StudentProjectGrade> studentCourseGrade = new HashSet<StudentProjectGrade>(0);
	
	
	public Character getLevel() {
		return level;
	}

	public void setLevel(Character level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<StudentProjectGrade> getStudentCourseGrade() {
		return studentCourseGrade;
	}

	public void setStudentCourseGrade(Set<StudentProjectGrade> studentCourseGrade) {
		this.studentCourseGrade = studentCourseGrade;
	}
	
	public void addStudentProjectGrade(StudentProjectGrade spg) {
		this.studentCourseGrade.add(spg);
	}
	
	public GradeDto convertGradeDto()
	{
		GradeDto dto = new GradeDto();
		dto.setLevel(this.getLevel());
		dto.setRemark(this.getRemark());
		
		return dto;
	}
	
}
