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

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(
    path = "/offers",
    produces = "application/json"
)
public class OffersApi {
    private final OffersRepository repository;

    public OffersApi(OffersRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
        method = POST,
        consumes = "application/json"
    )
    @ResponseBody
    ResponseEntity<Offer> newOffer(@RequestBody Offer offer) {
        return new ResponseEntity<>(repository.addOffer(offer), HttpStatus.CREATED);
    }

    @RequestMapping(
        method = GET,
        path="{id}"
    )
    @ResponseBody
    ResponseEntity getOffer(@PathVariable("id") String id) {
        return repository.getOffer(id).map(offer ->
        {
            if(offer.hasExpired()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else
                if(offer.isCancelled()) {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            else {
                return new ResponseEntity<>(offer, HttpStatus.OK);
            }
        }).orElse(
            new ResponseEntity(HttpStatus.NOT_FOUND)
        );
    }

    @RequestMapping(
        method = DELETE,
        path="{id}"
    )
    @ResponseBody
    ResponseEntity cancelOffer(@PathVariable("id") String id) {
        return repository.getOffer(id).map(offer ->
        {
            if(offer.hasExpired()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                offer.cancel();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }).orElse(
            new ResponseEntity(HttpStatus.NOT_FOUND)
        );
    }
}
