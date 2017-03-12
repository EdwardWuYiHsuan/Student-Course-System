package com.poseitech.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "student")
public class Student implements Serializable {
	
	private static final long serialVersionUID = 4012534442879056855L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "student_id_seq_gen")
	@SequenceGenerator(name = "student_id_seq_gen", sequenceName = "STUDENT_ID_SEQUENCE")
	private Long id;
   
	@NotNull
	@Size(max = 60)
	private String name;
   
	@NotNull
	@Type(type="date")
	private Date birthday;
   
	@NotNull
	@Type(type="timestamp")
	private Date registerDate;
   
	@Size(max = 100)
	private String remark;
	
	
	public Student() {
		this.registerDate = new Date();
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
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
