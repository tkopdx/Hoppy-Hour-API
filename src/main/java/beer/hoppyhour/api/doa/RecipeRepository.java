package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Recipe;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaSpecificationExecutorWithProjection<Recipe> {

}
