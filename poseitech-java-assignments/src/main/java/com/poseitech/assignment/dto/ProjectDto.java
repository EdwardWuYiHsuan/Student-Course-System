package com.poseitech.assignment.dto;

import java.io.Serializable;
import java.util.Date;

public class ProjectDto implements Serializable {
   
	private static final long serialVersionUID = -8001753971454616296L;
	
	private long id;
	private String name;
	private Date createDate;
	private String remark;
	
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
