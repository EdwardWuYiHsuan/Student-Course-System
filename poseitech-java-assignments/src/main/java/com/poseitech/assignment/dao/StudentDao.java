/**
 * StudentDao.java created 2016年11月17日
 */
package com.poseitech.assignment.dao;

import java.util.List;

import com.poseitech.assignment.entity.Student;

/**
 * 
 */
public interface StudentDao {
   
   // save or update student to database
   public Student saveOrUpdate(Student pStudent) throws Exception;
   
   // load student by identification
   public Student findById(Integer pStudentId) throws Exception;
   
   // find all projects by the student interested
   public List<Student> findStudentByProjectId(Integer pProjectId) throws Exception;
   
   // delete student.
   public Student delete(Integer pStudentId) throws Exception;
   
   // find all students by paging.  
   public List<Student> findAllStudents(int pStartRowNumber, int pFectchSize) throws Exception;
   
   //
   public List<Student> findByHql(String pHql, int pStartRowNumber, int pFectchSize, Object... pValues) throws Exception;

   //
   public List<Student> findByNamedQuery(String pNameQuery, Class<?> clazz, Object[] params, int pStartRowNumber, int pFectchSize) throws Exception;
}
