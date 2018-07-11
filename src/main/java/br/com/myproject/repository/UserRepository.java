package br.com.myproject.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.myproject.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	User findByUsername(String username);
	
}
