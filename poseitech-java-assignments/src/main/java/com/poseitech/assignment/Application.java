package com.poseitech.assignment;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}
	
	@Bean(name="sessionFactory")
    public SessionFactory configureSessionFactory(EntityManagerFactory emf) 
	{
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        return sessionFactory;
    }
	
}
