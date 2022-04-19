package beer.hoppyhour.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import beer.hoppyhour.api.doa.HopRepository;
import beer.hoppyhour.api.doa.MaltRepository;
import beer.hoppyhour.api.doa.PlaceRepository;
import beer.hoppyhour.api.doa.YeastRepository;
import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.Place;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.helper.ExcelHelper;

@Service
@Transactional
public class ExcelService {
    @Autowired
    HopRepository hopRepository;

    @Autowired
    MaltRepository maltRepository;

    @Autowired
    YeastRepository yeastRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ExcelHelper excelHelper;

    public void save(File file, String type) {
        try {
            switch (type) {
                case "hop":
                    List<Hop> hops = excelHelper.excelToHops(new FileInputStream(file));
                    hopRepository.saveAll(hops);
                    break;
                case "malt":
                    List<Malt> malts = excelHelper.excelToMalts(new FileInputStream(file));
                    maltRepository.saveAll(malts);
                    break;
                case "yeast":
                    List<Yeast> yeasts = excelHelper.excelToYeasts(new FileInputStream(file));
                    yeastRepository.saveAll(yeasts);
                    break;
                case "place":
                    List<Place> places = excelHelper.excelToPlaces(new FileInputStream(file));
                    placeRepository.saveAll(places);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to store excel data: " + e.getMessage());
        }
    }
}
