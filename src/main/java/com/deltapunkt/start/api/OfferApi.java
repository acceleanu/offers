package com.deltapunkt.start.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static com.deltapunkt.start.api.Offer.createOffer;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class OfferApi {
    @RequestMapping(
        path = "/offer",
        method = POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    Offer createNewOffer(@RequestBody Offer offer) {
        return createOffer("0", offer.getPrice(), offer.getCurrency(), offer.getDescription(), offer.getDuration());
    }
}
