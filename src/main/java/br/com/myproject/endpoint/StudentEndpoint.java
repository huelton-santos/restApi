package br.com.myproject.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.myproject.error.ResourceNotFoundException;
import br.com.myproject.model.Student;
import br.com.myproject.repository.StudentRepository;

/*
 * classe responsavel de controle para os endpoint da entidade Student
 *  
 * */

@RestController
@RequestMapping("v1")
public class StudentEndpoint {
		
	private final StudentRepository studentDAO;
	
	@Autowired
	public StudentEndpoint(StudentRepository studentDAO) {
		this.studentDAO = studentDAO;
	}

	@GetMapping(path="protected/students")
	public ResponseEntity<?> listAll(Pageable pageable){
		return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
	}
	
	@GetMapping(path="protected/students/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails){
		  System.out.println(userDetails);
		   verifyIfStudentExist(id);
		   Student student = studentDAO.findOne(id);		   

		   return new ResponseEntity<>(student, HttpStatus.OK);	
	}
	
	@GetMapping(path="protected/students/findByName/{name}")
	public ResponseEntity<?> findStudentByName(@PathVariable String name) {
		 return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);	
	}
	
	@PostMapping(path="admin/students")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Student student ){
		return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="admin/students/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		verifyIfStudentExist(id);
		studentDAO.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(path="admin/students")
	public ResponseEntity<?> update(@RequestBody Student student ){
		verifyIfStudentExist(student.getId());
		studentDAO.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	private void verifyIfStudentExist(Long id) {
		if(studentDAO.findOne(id) == null) {
		    throw new ResourceNotFoundException("Student not found for ID:"+id);
		}
	}
}
