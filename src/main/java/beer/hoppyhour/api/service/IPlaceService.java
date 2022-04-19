package beer.hoppyhour.api.service;

import beer.hoppyhour.api.entity.Place;

public interface IPlaceService {
    Place get(Long id);
    Place getCapitalByCountry(String country);
}
