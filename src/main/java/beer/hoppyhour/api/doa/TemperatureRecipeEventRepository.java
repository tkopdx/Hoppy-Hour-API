package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.TemperatureRecipeEvent;

public interface TemperatureRecipeEventRepository extends PagingAndSortingRepository<TemperatureRecipeEvent, Long> {
    
}
