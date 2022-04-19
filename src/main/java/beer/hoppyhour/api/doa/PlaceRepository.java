package beer.hoppyhour.api.doa;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import beer.hoppyhour.api.entity.Place;

public interface PlaceRepository extends PagingAndSortingRepository<Place, Long> {
    Optional<Place> findByCountryAndIsCapital(String country, Boolean isCapital);
}
