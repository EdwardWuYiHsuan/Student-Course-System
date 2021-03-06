package com.poseitech.assignment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.poseitech.assignment.dto.StudentDto;

@Entity
@Table(name = "student")
public class Student implements Serializable {
	
	private static final long serialVersionUID = 4012534442879056855L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "student_id_seq_gen")
	@SequenceGenerator(name = "student_id_seq_gen", sequenceName = "STUDENT_ID_SEQUENCE")
	@Column(name = "student_id")
	private Long id;
   
	@NotNull
	@Size(max = 60)
	private String name;
   
	@NotNull
	@Type(type = "date")
	private Date birthday;
   
	@NotNull
	@Type(type = "date")
	private Date registerDate;
   
	@Size(max = 100)
	private String remark;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private Set<StudentProjectGrade> studentProjectGrade = new HashSet<StudentProjectGrade>(0);
	
	
	public Long getId() {
		return id;
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

	public Set<StudentProjectGrade> getStudentProjectGrade() {
		return studentProjectGrade;
	}

	public void setStudentProjectGrade(Set<StudentProjectGrade> studentProjectGrade) {
		this.studentProjectGrade = studentProjectGrade;
	}
	
	public void addStudentProjectGrade(StudentProjectGrade spg) {
		this.studentProjectGrade.add(spg);
	}
	
	public StudentDto convertStudentDto()
	{
		StudentDto dto = new StudentDto();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setBirthday(this.getBirthday());
		dto.setRegisterDate(this.getRegisterDate());
		dto.setRemark(this.getRemark());
		
		for (StudentProjectGrade spg : this.getStudentProjectGrade()) {
			dto.addInterestedProjects(spg.getProject().convertProjectDto());
		}
		
		return dto;
	}
	
}
