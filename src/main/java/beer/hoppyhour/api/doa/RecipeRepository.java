package beer.hoppyhour.api.doa;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import beer.hoppyhour.api.doa.projection.RecipeSearchResult;
import beer.hoppyhour.api.entity.Recipe;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, JpaSpecificationExecutorWithProjection<Recipe> {

    @Query(
        "SELECT new " + 
        "beer.hoppyhour.api.doa.projection.RecipeSearchResultClass" +
        "(r.name, r.id, r.style, r.method, r.createdDate, r.abv, r.srm, r.ibu, r.rating, u.id, u.username, p.id, p.country, p.city) " + 
        "FROM Recipe r " +
        "LEFT JOIN r.user u " +
        "LEFT JOIN r.place p " +
        "WHERE r.id IN " +
        "(SELECT re.id " +
        "FROM Recipe re " + 
        "JOIN re.ingredients i " +
        "WHERE i.id IN (:ingredients) GROUP BY re.id HAVING count(re.id) = :size)"
    )
    Page<RecipeSearchResult> findAllByIngredientsWithPagination(@Param("ingredients") Collection<Long> ingredients, @Param("size") long size, Pageable pageable);
}
