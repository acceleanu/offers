package com.deltapunkt.start.api;

import com.deltapunkt.start.model.Offer;
import com.deltapunkt.start.repo.OffersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.time.Instant;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
    ResponseEntity<Offer> newOffer(@RequestBody Offer offer) {
        return new ResponseEntity<>(repository.addOffer(offer), HttpStatus.CREATED);
    }

    @RequestMapping(
        method = GET,
        path="{id}",
        produces = "application/json"
    )
    @ResponseBody
    ResponseEntity getOffer(@PathVariable("id") String id) {
        return repository.getOffer(id).map(offer ->
        {
            if(offer.hasExpired()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(offer, HttpStatus.OK);
            }
        }).orElse(
            new ResponseEntity(HttpStatus.NOT_FOUND)
        );
    }
}
