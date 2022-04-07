package beer.hoppyhour.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl;


@SpringBootApplication
//required for using projections in specification executors
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
public class HoppyHourApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoppyHourApiApplication.class, args);
	}

}
