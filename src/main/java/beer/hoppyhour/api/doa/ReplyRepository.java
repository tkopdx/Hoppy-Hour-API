package beer.hoppyhour.api.doa;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Reply;

public interface ReplyRepository extends PagingAndSortingRepository<Reply, Long> {
    
}
