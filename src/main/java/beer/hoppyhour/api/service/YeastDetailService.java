package beer.hoppyhour.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beer.hoppyhour.api.doa.YeastDetailRepository;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.entity.YeastDetail;
import beer.hoppyhour.api.payload.request.PostRecipeRequestDetailEvent;

@Service
public class YeastDetailService implements IIngredientDetailService<YeastDetail> {

    @Autowired
    YeastDetailRepository yeastDetailRepository;

    @Autowired
    YeastService yeastService;

    @Override
    public YeastDetail createNewDetail(PostRecipeRequestDetailEvent data) {
        //create the new detail
        YeastDetail yeastDetail = new YeastDetail(data.getWeight(), data.getLiquidVolume(), data.getDetailNotes());
        //get the associated yeast
        Yeast yeast = yeastService.get(data.getIngredientId());
        //associate detail and ingredient
        yeast.addIngredientDetail(yeastDetail);
        //save the detail
        return save(yeastDetail);
    }

    @Override
    public YeastDetail save(YeastDetail detail) {
        return yeastDetailRepository.save(detail);
    }

    @Override
    public YeastDetail get(Long id) {
        Optional<YeastDetail> yeastDetail = yeastDetailRepository.findById(id);
        if (yeastDetail.isPresent()) {
            return yeastDetail.get();
        } else {
            return null;
        }
    }
    
}
