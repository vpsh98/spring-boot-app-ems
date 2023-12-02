package com.crud.spring_crudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.crud.spring_crudApp.model.Employee;
import com.crud.spring_crudApp.repository.EmployeeRepository;

@SpringBootApplication
public class SpringCrudAppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudAppApplication.class, args);
	}

	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public void run(String... args) throws Exception {
		Employee emp = new Employee();
		emp.setFirstName("abc");
		emp.setEmailid("abc.gm.cm");
		empRepo.save(emp);
		
		Employee emp2 = new Employee();
		emp2.setFirstName("def");
		emp2.setEmailid("def.gm.cm");
		empRepo.save(emp2);
		
	}
	
	@Configuration
	public class Config implements WebMvcConfigurer {
		@Autowired
		MyInterceptor myInterceptor;

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(myInterceptor);
		}
	}

}
