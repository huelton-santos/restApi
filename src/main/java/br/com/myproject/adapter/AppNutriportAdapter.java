package br.com.myproject.adapter;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * classe de adapter para alterar a paginação do Page em listAll 
 * 
 * */

@Configuration
public class AppNutriportAdapter extends WebMvcConfigurerAdapter {
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver();
		phmar.setFallbackPageable(new PageRequest(0, 5));
		argumentResolvers.add(phmar);
	}

}
