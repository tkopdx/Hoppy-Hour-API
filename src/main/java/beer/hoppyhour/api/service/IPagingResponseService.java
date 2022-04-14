package beer.hoppyhour.api.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import beer.hoppyhour.api.payload.response.PagingResponse;

public interface IPagingResponseService<A, B> {
    PagingResponse<B> get(Specification<A> spec, Long pageSize, Long pageNumber, Sort sort);
    PagingResponse<B> get(Specification<A> spec, Pageable pageable);
    Boolean includesPageSizeAndPageNumber(Long pageSize, Long pageNumber);
    Pageable buildPageRequest(Long pageSize, Long pageNumber, Sort sort);
}
