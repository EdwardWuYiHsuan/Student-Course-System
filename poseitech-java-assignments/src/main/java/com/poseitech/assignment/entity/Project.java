package com.poseitech.assignment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "project")
public class Project implements Serializable {
	
	private static final long serialVersionUID = 1862690403944384704L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "project_id_seq_gen")
	@SequenceGenerator(name = "project_id_seq_gen", sequenceName = "PROJECT_ID_SEQUENCE")
	@Column(name = "project_id")
	private Long id;
	
	@NotNull
	@Size(max = 60)
	@Column(unique = true)
	private String name;
	
	@NotNull
	@Type(type = "timestamp")
	private Date createDate;
	
	@Size(max = 100)
	private String remark;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private Set<StudentProjectGrade> studentCourseGrade = new HashSet<StudentProjectGrade>(0);
	
	
	public Project() {
		this.createDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreateDate() {
		return createDate;
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
	
}
