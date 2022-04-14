package beer.hoppyhour.api.doa;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import beer.hoppyhour.api.doa.projection.RecipeSearchResult;
import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.entity.Recipe;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaSpecificationExecutorWithProjection<Recipe> {

    @Query(
        value = "select r.name, r.id, r.style, r.method, r.created_date as createdDate, r.abv, r.srm, r.ibu, r.rating, p.country, u.username, u.id as userId " +
        "from recipe r " +
        "left join place p " +
        "on place_id = p.id " +
        "left join user u " +
        "on user_id = u.id " +
        "where r.id " +
        "in " + "(SELECT DISTINCT recipe_id FROM recipe_ingredient WHERE ingredient_id IN (:ingredients) GROUP BY recipe_id HAVING count(recipe_id) = :size)",
        countQuery = "SELECT count(DISTINCT re.id) FROM Recipe re JOIN re.ingredients AS i WHERE i IN (:ingredients) GROUP BY i.id HAVING count(re.id) = :ingredients.size())",
        nativeQuery = true
    )
    Page<RecipeSearchResult> findAllByIngredientsWithPagination(@Param("ingredients") Collection<Ingredient<? extends IngredientDetail<?>>> ingredients, @Param("size") long size, Pageable pageable);
}
