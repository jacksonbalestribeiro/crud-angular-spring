package com.jackson.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jackson.crudspring.enums.Category;
import com.jackson.crudspring.model.Course;
import com.jackson.crudspring.model.Lesson;
import com.jackson.crudspring.repository.CoursesRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CoursesRepository coursesRepository){
		return args -> {
			coursesRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular com spring");
			c.setCategory(Category.FRONT_END);

			Lesson l = new Lesson();
			l.setName("Introdução I");
			l.setYoutubeUrl("teste 1");
			l.setCourse(c);
			c.getLessons().add(l);

			Lesson l1 = new Lesson();
			l1.setName("Introdução II");
			l1.setYoutubeUrl("tetse 2");
			l1.setCourse(c);
			c.getLessons().add(l1);

			coursesRepository.save(c);
		};
		
	}

}
