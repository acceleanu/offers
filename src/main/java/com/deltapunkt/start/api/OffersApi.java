package com.deltapunkt.start.api;

import com.deltapunkt.start.model.Offer;
import com.deltapunkt.start.repo.OffersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
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
    ResponseEntity newOffer(@RequestBody Offer offer) {
        Offer createdOffer = repository.addOffer(offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOffer.getId()).toUri();

        return ResponseEntity.created(location).body(createdOffer);
    }

    @RequestMapping(
        method = GET
    )
    @ResponseBody
    ResponseEntity getOffers() {
        return ok(repository.getOffers());
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
                return notFound().build();
            } else
                if(offer.isCancelled()) {
                    return notFound().build();
                }
            else {
                return ok(offer);
            }
        }).orElseGet(
            () -> notFound().build()
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
                return notFound().build();
            } else {
                offer.cancel();
                return noContent().build();
            }
        }).orElseGet(
            () -> notFound().build()
        );
    }
}
