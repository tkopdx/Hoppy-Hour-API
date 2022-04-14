package beer.hoppyhour.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.OtherIngredientDetailRepository;
import beer.hoppyhour.api.entity.OtherIngredient;
import beer.hoppyhour.api.entity.OtherIngredientDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class OtherIngredientDetailService implements IIngredientDetailService<OtherIngredientDetail, OtherIngredient> {

    @Autowired
    OtherIngredientDetailRepository otherIngredientDetailRepository;

    @Autowired
    OtherIngredientService otherIngredientService;

    @Override
    public OtherIngredientDetail createNewDetail(PostRecipeRequestDetailEvent data) {
        //create the new detail
        OtherIngredientDetail otherIngredientDetail = new OtherIngredientDetail(data.getWeight(), data.getLiquidVolume(), data.getDetailNotes(), data.getPurpose());
        //get the associated malt
        OtherIngredient otherIngredient = otherIngredientService.get(data.getIngredientId());
        //associate detail and ingredient
        otherIngredient.addIngredientDetail(otherIngredientDetail);
        //save the detail
        return save(otherIngredientDetail);
    }

    @Override
    public OtherIngredientDetail save(OtherIngredientDetail detail) {
        return otherIngredientDetailRepository.save(detail);
    }

    @Override
    public OtherIngredientDetail get(Long id) {
        Optional<OtherIngredientDetail> otherIngredientDetail = otherIngredientDetailRepository.findById(id);
        if (otherIngredientDetail.isPresent()) {
            return otherIngredientDetail.get();
        } else {
            return null;
        }
    }

    @Override
    public List<OtherIngredientDetail> createDetailsForExampleSearch(List<OtherIngredient> ingredients) {
        List<OtherIngredientDetail> otherIngredientDetails = new ArrayList<>();
        for (OtherIngredient otherIngredient: ingredients) {
            OtherIngredientDetail detail = new OtherIngredientDetail();
            detail.setIngredient(otherIngredient);
            otherIngredientDetails.add(detail);
        }
        return otherIngredientDetails;
    }
    
}
