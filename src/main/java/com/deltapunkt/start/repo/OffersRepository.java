package com.deltapunkt.start.repo;

import com.deltapunkt.start.model.Offer;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.deltapunkt.start.model.Offer.createOffer;
import static java.lang.String.format;

public class OffersRepository {
    private final AtomicInteger sequence;
    private final ConcurrentMap<String, Offer> offers;

    public OffersRepository() {
        sequence = new AtomicInteger(0);
        offers = new ConcurrentHashMap<>();
    }

    public Offer addOffer(Offer offer) {
        String id = getNewId();
        Offer result = createOffer(id, offer);
        offers.put(id, result);

        return result;
    }

    public Optional<Offer> getOffer(String id) {
        return Optional.ofNullable(offers.get(id));
    }

    private String getNewId() {
        return format("%d", sequence.getAndIncrement());
    }
}
