package beer.hoppyhour.api;

import org.springframework.data.repository.CrudRepository;

public interface BrewedRepository extends CrudRepository<Brewed, Long> {
    
}
