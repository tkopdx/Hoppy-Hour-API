package beer.hoppyhour.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.PlaceRepository;
import beer.hoppyhour.api.entity.Place;

@Service
public class PlaceService implements IPlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Override
    public Place get(Long id) {
        Optional<Place> place = placeRepository.findById(id);
        if (place.isPresent()) {
            return place.get();
        } else {
            throw new EntityNotFoundException("No place with id " + id + " was found.");
        }
    }

    @Override
    public Place getCapitalByCountry(String country) {
        Optional<Place> place = placeRepository.findByCountryAndIsCapital(country, true);
        if (place.isPresent()) {
            return place.get();
        } else {
            throw new EntityNotFoundException("No place with country " + country + " was found.");
        }
    }
    
}
