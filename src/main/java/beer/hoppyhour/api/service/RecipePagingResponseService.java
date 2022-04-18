package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RecipeRepository;
import beer.hoppyhour.api.doa.projection.RecipeSearchResult;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.payload.response.PagingResponse;

@Service
public class RecipePagingResponseService implements IPagingResponseService<Recipe, RecipeSearchResult> {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public PagingResponse<RecipeSearchResult> get(Specification<Recipe> spec, Long pageSize, Long pageNumber, Sort sort) {
        if (!includesPageSizeAndPageNumber(pageSize, pageNumber)) {
            //default values
            pageSize = Long.valueOf(20);
            pageNumber = Long.valueOf(0);
        }

        return get(spec, buildPageRequest(pageSize, pageNumber, sort));
    }

    @Override
    public PagingResponse<RecipeSearchResult> get(Specification<Recipe> spec, Pageable pageable) {
        Page<RecipeSearchResult> page = recipeRepository.findAll(spec, RecipeSearchResult.class, pageable);
        List<RecipeSearchResult> data = page.getContent();
        return new PagingResponse<RecipeSearchResult>(
            page.getTotalElements(), 
            (long) page.getNumber(), 
            (long) page.getNumberOfElements(), 
            (long) pageable.getOffset(), 
            (long) page.getTotalPages(), 
            data
        );
    }

    @Override
    public Boolean includesPageSizeAndPageNumber(Long pageSize, Long pageNumber) {
        return (pageSize != null && pageNumber != null);
    }

    @Override
    public Pageable buildPageRequest(Long pageSize, Long pageNumber, Sort sort) {
        return PageRequest.of(pageNumber.intValue(), pageSize.intValue(), sort);
    }

    public PagingResponse<RecipeSearchResult> getAllByExample(Set<Long> ingredients, Long pageSize, Long pageNumber, Sort sort) {
        if (!includesPageSizeAndPageNumber(pageSize, pageNumber)) {
            //default values
            pageSize = Long.valueOf(20);
            pageNumber = Long.valueOf(0);
        }
        System.out.println("get service");
        return get(ingredients, buildPageRequest(pageSize, pageNumber, sort));
    }

    public PagingResponse<RecipeSearchResult> get(Set<Long> ingredients, Pageable pageable) {
        System.out.println("making the repo query call in service");
        Page<RecipeSearchResult> page = recipeRepository.findAllByIngredientsWithPagination(ingredients, ingredients.size(), pageable);
        List<RecipeSearchResult> data = page.getContent();
        return new PagingResponse<RecipeSearchResult>(
            page.getTotalElements(), 
            (long) page.getNumber(), 
            (long) page.getNumberOfElements(), 
            (long) pageable.getOffset(), 
            (long) page.getTotalPages(), 
            data
        );
    }
    
}
