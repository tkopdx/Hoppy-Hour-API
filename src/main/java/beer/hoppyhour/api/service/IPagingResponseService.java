package beer.hoppyhour.api.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import beer.hoppyhour.api.payload.response.PagingResponse;

public interface IPagingResponseService<A, B> {
    PagingResponse<B> get(Specification<A> spec, HttpHeaders headers, Sort sort);
    PagingResponse<B> get(Specification<A> spec, Pageable pageable);
    Boolean isRequestedPage(HttpHeaders headers);
    Pageable buildPageRequest(HttpHeaders headers, Sort sort);
}
