package com.deltapunkt.start.api;

import com.deltapunkt.start.repo.OffersRepository;
import com.deltapunkt.start.model.Offer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/offer")
public class OfferApi {
    private final OffersRepository repository;

    public OfferApi(OffersRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
        method = POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    Offer createNewOffer(@RequestBody Offer offer) {
        return repository.addOffer(offer);
    }
}
