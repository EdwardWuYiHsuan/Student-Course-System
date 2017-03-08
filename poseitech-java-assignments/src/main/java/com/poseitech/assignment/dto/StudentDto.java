package com.poseitech.assignment.dto;

import java.io.Serializable;
import java.util.List;

public class StudentDto implements Serializable {

   //
   private static final long serialVersionUID = 3209888691671533902L;
   
   private List<ProjectDto> interestedProjects;

   public List<ProjectDto> getInterestedProjects() {
      return interestedProjects;
   }

   public void setInterestedProjects(List<ProjectDto> pInterestedProjects) {
      interestedProjects = pInterestedProjects;
   } 

}
