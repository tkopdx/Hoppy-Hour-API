package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    
}
