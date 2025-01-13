package com.learning_platform.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Student extends User{
	
	private List<String> courses;
	private List<String> testScores;
	
	private List<String> quizes;
	
	
}
