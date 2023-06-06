package com.ivchern.exchange_employers;


import com.ivchern.exchange_employers.Controllers.AuthController;
import com.ivchern.exchange_employers.Controllers.CardControllers.RequestWorkerController;
import com.ivchern.exchange_employers.Controllers.CardControllers.ResourcesController;
import com.ivchern.exchange_employers.Controllers.UserController;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestWorkerDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.ContactDTO;
import com.ivchern.exchange_employers.DTO.TeamDTO.TeammateDTO;
import com.ivchern.exchange_employers.DTO.UserDTO;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.User;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import com.ivchern.exchange_employers.Repositories.TeammateRepository;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.Query;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Arrays;
import java.util.Date;

//TODO: add liquidbase
@SpringBootApplication
@SecurityScheme(
		name = "JWT",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class ExchangeEmployersApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExchangeEmployersApplication.class, args);
	}
	@Bean
	public CommandLineRunner LoadData(AuthController authController, UserController userController,
									  SkillRepository skillRepository, TeammateService teammateService,
									  ResourcesController resourcesController, RequestWorkerController requestWorkerController) {
		return (args) -> {
			authController.registerUser(new User("test1", "test1@test.test", "123456"));
			authController.registerUser(new User("test2", "test2@test.test", "123456"));
			authController.registerUser(new User("test3", "test3@test.test", "123456"));
			UserDTO test1 = userController.getUserByUsername("test1").getBody();
			UserDTO test2 = userController.getUserByUsername("test2").getBody();
			UserDTO test3 = userController.getUserByUsername("test3").getBody();
			test1.setFirstname("Ivan");
			test2.setFirstname("Egor");
			test3.setFirstname("Ahmet");
			test1.setLastname("Ivanov");
			test2.setLastname("Emelyanovf");
			test3.setLastname("Starii");
			test1.setNameTeam("Java Team");
			test2.setNameTeam("React Team");
			test3.setNameTeam("Mobile Team");
			test1.setTeamDescription("Description Team Java");
			test2.setTeamDescription("Description Team React");
			test3.setTeamDescription("Description Team Mobile");
			List<ContactDTO> contacts = new ArrayList<>();
			ContactDTO contact1 = new ContactDTO("Telegram", "@test1");
			ContactDTO contact = new ContactDTO("Phone", "+7999932143");
			ContactDTO contact2 = new ContactDTO("Telegram", "@test2");
			ContactDTO contact3 = new ContactDTO("Telegram", "@test3");

			contacts.add(contact);
			contacts.add(contact1);
			test1.setContacts(new ArrayList(contacts));

			contacts.clear();;
			contacts.add(contact2);
			test2.setContacts(new ArrayList(contacts));

			contacts.clear();;
			contacts.add(contact3);
			test3.setContacts(new ArrayList(contacts));

			userController.updateUser(test1.getId(), test1);
			userController.updateUser(test2.getId(), test2);
			userController.updateUser(test3.getId(), test3);

			skillRepository.save(new Skill(1L, "Java", "Java desc"));
			skillRepository.save(new Skill(2L, "SQL", "SQL desc"));
			skillRepository.save(new Skill(3L, "React", "React desc"));
			skillRepository.save(new Skill(4L, "Android", "Android desc"));
			skillRepository.save(new Skill(5L, "IOS", "IOS desc"));
			skillRepository.save(new Skill(6L, "Flutter", "Flutter desc"));
			skillRepository.save(new Skill(7L, "Maven", "Maven desc"));
			skillRepository.save(new Skill(8L, "Spring", "Spring desc"));

			teammateService.save(new TeammateDTO("Ivan",  "Ivanov", "Java Developer", "Senior",1L, 1L
					, new HashSet<String>(Arrays.asList("Java", "SQL", "Maven"))));
			teammateService.save(new TeammateDTO("Egor",  "Egorov", "Java Developer", "Junior", 1L, 1L
			,new HashSet<String>(Arrays.asList("Java", "SQL", "Maven"))));
			teammateService.save(new TeammateDTO("Ilia",  "Maslow", "Java Developer", "Middle", 1L, 1L
			,new HashSet<String>(Arrays.asList("Java", "SQL"))));
			teammateService.save(new TeammateDTO("Alex",  "Alexandrov", "Java Developer","Middle", 1L, 1L,
					new HashSet<String>(Arrays.asList("React"))));
			teammateService.save(new TeammateDTO("Alex",  "Mask", "React Developer","Middle", 2L, 2L,
					new HashSet<String>(Arrays.asList("React"))));
            teammateService.save(new TeammateDTO("Mikos",  "Mal", "Frontend Developer","Middle", 2L, 2L,
					new HashSet<String>(Arrays.asList("React", "Flutter"))));
			teammateService.save(new TeammateDTO("Goshan", "Mikos",  "Mobile Developer","Middle", 3L, 3L,
					new HashSet<String>(Arrays.asList("Flutter", "Android"))));
			teammateService.save(new TeammateDTO("Alex",  "Mask", "Android Developer","Middle", 3L, 3L,
					new HashSet<String>(Arrays.asList("IOS", "Android", "Flutter"))));
            teammateService.save(new TeammateDTO("Alex",  "Mask", "IOS Developer","Middle", 3L, 3L,
					new HashSet<String>(Arrays.asList("IOS"))));

			resourcesController.setResources(new ResourceDtoOnCreate("Java Developer", "From Home",
					new Date(1212121212121L), new Date(1212121212131L), 1L));
			resourcesController.setResources(new ResourceDtoOnCreate("Java Developer", "From Home",
					new Date(1212121212121L), new Date(1212121212131L), 2L));
			resourcesController.setResources(new ResourceDtoOnCreate("Mobile Developer", "From Home",
					new Date(1212121212121L), new Date(1212121212131L), 9L));
			resourcesController.setResources(new ResourceDtoOnCreate("React Developer", "From Home",
					new Date(1212121212121L), new Date(1212121212131L), 4L));

			requestWorkerController.postRequest(new RequestWorkerDtoOnCreate(
				"Java Developer",
			"Time Squ Build",
					   "Senior",
					   "Created new Fine project",
					   "From Home",
					   new Date(1212121212121L),
					false,new HashSet<>(
					Arrays.asList("React", "SQL", "Maven"))));

			requestWorkerController.postRequest(new RequestWorkerDtoOnCreate(
					"React Developer",
					"Time Squ Build",
					"Senior",
					"Created new Fine project",
					"From Home",
					new Date(1212121212121L),
					false,new HashSet<>(
					Arrays.asList("React", "SQL", "Maven"))));
		};
	}
}
