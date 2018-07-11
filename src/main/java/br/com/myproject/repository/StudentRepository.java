package br.com.myproject.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.myproject.model.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

	List<Student> findByNameIgnoreCaseContaining(String name);
}
