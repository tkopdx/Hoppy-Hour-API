package beer.hoppyhour.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.HopDetailRepository;
import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.HopDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class HopDetailService implements IIngredientDetailService<HopDetail, Hop> {

    @Autowired
    HopDetailRepository hopDetailRepository;

    @Autowired
    HopService hopService;

    @Override
    public HopDetail createNewDetail(PostRecipeRequestDetailEvent data) {
        //create the new detail
        HopDetail hopDetail = new HopDetail(data.getWeight(), data.getLiquidVolume(), data.getDetailNotes(), data.getPurpose());
        //get the associated hop
        Hop hop = hopService.get(data.getIngredientId());
        //associate detail and ingredient
        hop.addIngredientDetail(hopDetail);
        //save the detail
        return save(hopDetail);
    };

    @Override
    public HopDetail save(HopDetail detail) {
        return hopDetailRepository.save(detail);
    }

    @Override
    public HopDetail get(Long id) {
        Optional<HopDetail> hopDetail = hopDetailRepository.findById(id);
        if (hopDetail.isPresent()) {
            return hopDetail.get();
        } else {
            return null;
        }
    }

    @Override
    public List<HopDetail> createDetailsForExampleSearch(List<Hop> ingredients) {
        List<HopDetail> hopDetails = new ArrayList<>();
        for (Hop hop: ingredients) {
            HopDetail detail = new HopDetail();
            detail.setIngredient(hop);
            hopDetails.add(detail);
        }
        return hopDetails;
    };
    
}
