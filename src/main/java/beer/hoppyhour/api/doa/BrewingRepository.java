package beer.hoppyhour.api.doa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Brewing;
import beer.hoppyhour.api.entity.User;

public interface BrewingRepository extends PagingAndSortingRepository<Brewing, Long> {
    //allow users to delete their own brewings
    @Override
    void deleteById(Long id);

    //allow users to save or update their own brewings
    @Override
    <S extends Brewing> S save(S entity);

    //find all brewings associated with user
    List<Brewing> findByUser(User user);
}
