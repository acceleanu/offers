package com.deltapunkt.start.repo;

import com.deltapunkt.start.model.Offer;

import java.util.concurrent.atomic.AtomicInteger;

import static com.deltapunkt.start.model.Offer.createOffer;
import static java.lang.String.format;

public class OffersRepository {
    private final AtomicInteger sequence = new AtomicInteger(0);

    public Offer addOffer(Offer offer) {
        return createOffer(getNewId(), offer);
    }

    private String getNewId() {
        return format("%d", sequence.getAndIncrement());
    }
}
