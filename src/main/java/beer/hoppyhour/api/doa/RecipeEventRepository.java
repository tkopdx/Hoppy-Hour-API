package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.RecipeEvent;

public interface RecipeEventRepository extends PagingAndSortingRepository<RecipeEvent, Long> {

}
