package beer.hoppyhour.api.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.RecipeRepository;
import beer.hoppyhour.api.doa.projection.RecipeSearchResult;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.payload.response.PagingHeaders;
import beer.hoppyhour.api.payload.response.PagingResponse;

@Service
public class RecipePagingResponseService implements IPagingResponseService<Recipe, RecipeSearchResult> {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public PagingResponse<RecipeSearchResult> get(Specification<Recipe> spec, HttpHeaders headers, Sort sort) {
        if (isRequestedPage(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            headers.set(PagingHeaders.PAGE_NUMBER.getName(), "0");
            headers.set(PagingHeaders.PAGE_SIZE.getName(), "20");
            return get(spec, buildPageRequest(headers, sort));
        }
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
    public Boolean isRequestedPage(HttpHeaders headers) {
        return (headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName()));
    }

    @Override
    public Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
    
}
