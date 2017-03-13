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

import com.poseitech.assignment.dto.ProjectDto;

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
    private Set<StudentProjectGrade> studentProjectGrade = new HashSet<StudentProjectGrade>(0);
	
	
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

	public Set<StudentProjectGrade> getStudentProjectGrade() {
		return studentProjectGrade;
	}

	public void setStudentProjectGrade(Set<StudentProjectGrade> studentProjectGrade) {
		this.studentProjectGrade = studentProjectGrade;
	}
	
	public void addStudentProjectGrade(StudentProjectGrade spg) {
		this.studentProjectGrade.add(spg);
	}
	
	public ProjectDto convertProjectDto() 
	{
		ProjectDto dto = new ProjectDto();
		dto.setId(this.getId());
		dto.setName(this.getName());
		dto.setCreateDate(this.getCreateDate());
		dto.setRemark(this.getRemark());
		
		return dto;
	}
	
	@Override
	public int hashCode() {
		return this.id.intValue();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (null == obj)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Project anoProject = (Project) obj;
		return this.getName().equals(anoProject.getName());
	}
	
}
