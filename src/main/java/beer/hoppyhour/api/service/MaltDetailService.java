package beer.hoppyhour.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.MaltDetailRepository;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.MaltDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class MaltDetailService implements IIngredientDetailService<MaltDetail, Malt> {

    @Autowired
    MaltDetailRepository maltDetailRepository;

    @Autowired
    MaltService maltService;

    @Override
    public MaltDetail createNewDetail(PostRecipeRequestDetailEvent data) {
        //create the new detail
        MaltDetail maltDetail = new MaltDetail(data.getWeight(), data.getLiquidVolume(), data.getDetailNotes());
        //get the associated malt
        Malt malt = maltService.get(data.getIngredientId());
        //associate detail and ingredient
        malt.addIngredientDetail(maltDetail);
        //save the detail
        return save(maltDetail);
    }

    @Override
    public MaltDetail save(MaltDetail detail) {
        return maltDetailRepository.save(detail);
    }

    @Override
    public MaltDetail get(Long id) {
        Optional<MaltDetail> maltDetail = maltDetailRepository.findById(id);
        if (maltDetail.isPresent()) {
            return maltDetail.get();
        } else {
            return null;
        }
    }

    @Override
    public List<MaltDetail> createDetailsForExampleSearch(List<Malt> ingredients) {
        List<MaltDetail> maltDetails = new ArrayList<>();
        for (Malt malt: ingredients) {
            MaltDetail detail = new MaltDetail();
            detail.setIngredient(malt);
            maltDetails.add(detail);
        }
        return maltDetails;
    }
    
}
