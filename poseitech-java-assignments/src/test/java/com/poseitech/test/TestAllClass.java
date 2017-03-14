package com.poseitech.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.poseitech.test.controller.AssignmentControllerTest;
import com.poseitech.test.dao.ProjectDaoTest;
import com.poseitech.test.dao.StudentDaoTest;
import com.poseitech.test.service.AssignmentServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ ProjectDaoTest.class, StudentDaoTest.class, AssignmentServiceTest.class, AssignmentControllerTest.class} )
public final class TestAllClass {}