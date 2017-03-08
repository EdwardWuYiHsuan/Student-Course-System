package com.poseitech.assignment.service;

import java.util.List;

import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;

public interface AssignmentService {
   
   // 新增一個學生至系統
   public StudentDto createStudent(StudentDto pNewStudent) throws Exception;
   
   // 以分頁方式調閱所有系統中所有學生
   // pStartRowNumber: 從第幾筆開始
   // pLimit: 此次讀取筆數
   public List<StudentDto> getAllStudents(int pStartRowNumber, int pLimit) throws Exception;
   
   // 自系統刪除指定的學生
   public boolean deleteStudent(StudentDto pNewStudent) throws Exception;
   
   // 查詢指定的學生感興趣的所有專案
   // pStudent: 依指定的條件查詢
   public List<ProjectDto> getInterestedProjects(StudentDto pStudent) throws Exception;
   
   // 將學生指定感興趣的專案註冊到系統
   public StudentDto registerProjects(Integer pStudendId, List<ProjectDto> pProjects) throws Exception;
   
   // 將專案資料新增至系統
   public List<ProjectDto> createProjects(List<ProjectDto> pProjects) throws Exception;

   // 將專案資料自系統刪除，同時將這些專案自有感興趣的學生列表中移除。
   public boolean deleteProjects(List<ProjectDto> pProjects) throws Exception;
}
