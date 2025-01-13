package com.learning_platform.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Teacher extends User {
	
	List<String> courses;
	List<String> students;
	

}
