package br.com.myproject.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.myproject.model.Student;
import br.com.myproject.repository.StudentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// inserindo dados para student
	@Test
	public void createShouldPersistData() {
		Student student = new Student("huelton", "huelton@gmail.com");

		this.studentRepository.save(student);

		assertThat(student.getId()).isNotNull();
		assertThat(student.getName()).isEqualTo("huelton");
		assertThat(student.getEmail()).isEqualTo("huelton@gmail.com");

	}

	// deletar um student
	@Test
	public void deleteShouldRemoveData() {
		Student student = new Student("huelton", "huelton@gmail.com");
		this.studentRepository.save(student);
		studentRepository.delete(student);
		assertThat(studentRepository.findOne(student.getId())).isNull();

	}

	// update um student
	@Test
	public void updateShouldChangeAndPersistData() {
		Student student = new Student("huelton", "huelton@gmail.com");
		this.studentRepository.save(student);
		student.setName("huelton");
		student.setEmail("huelton@gmail.com");
		this.studentRepository.save(student);
		student = this.studentRepository.findOne(student.getId());
		assertThat(student.getName()).isEqualTo("huelton");
		assertThat(student.getEmail()).isEqualTo("huelton@gmail.com");
	}
	
	// busca de students ignorando caracteres minusculos e maiusculos
    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("Huelton", "huelton@gmail.com");
        Student student2 = new Student("huelton", "huelton@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("huelton");
        assertThat(studentList.size()).isEqualTo(2);
    }
    
    // obrigatoriedade de informar um nome
    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo do estudande é obrigatorio!");
        this.studentRepository.save(new Student());
    }
    
    // obrigatoriedade de informar um email pois é campo obrigatorio
    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("huelton");
        this.studentRepository.save(student);
    }
    
    // obrigatoriedade de informar um email valido e verificar a frase de excessão
    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Digite um email válido");
        Student student = new Student();
        student.setName("huelton");
        student.setEmail("huelton");
        this.studentRepository.save(student);
    }

}
