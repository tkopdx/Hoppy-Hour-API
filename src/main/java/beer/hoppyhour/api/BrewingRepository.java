package beer.hoppyhour.api;

import org.springframework.data.repository.CrudRepository;

public interface BrewingRepository extends CrudRepository<Brewing, Long> {
    
}
