package br.com.myproject.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/*
 * Modelo para criação de Student
 * 
 * */

@Entity
public class Student extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="O campo do estudande é obrigatorio!")
	private String name;
	
	@NotEmpty
	@Email(message="Digite um email válido")
	private String email;	

	public Student() {
	}	

	public Student(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public Student(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
