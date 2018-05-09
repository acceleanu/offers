package com.deltapunkt.start.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class Offer {
    private String id;
    private long price;
    private String currency;
    private String description;
    private long duration;
    @JsonIgnore
    private long creationTime = getCurrentTimeInSeconds();

    @JsonIgnore
    private boolean cancelled;

    public Offer() {
    }

    private Offer(String id, long price, String currency, String description, long duration) {
        this.id = id;

        this.price = price;
        this.currency = currency;
        this.description = description;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public long getDuration() {
        return duration;
    }

    public static Offer createOffer(String id, Offer offer) {
        return new Offer(id, offer.getPrice(), offer.getCurrency(), offer.getDescription(), offer.getDuration());
    }

    public static Offer createOffer(String id, long price, String currency, String description, long duration) {
        return new Offer(id, price, currency, description, duration);
    }

    public static Offer createOffer(long price, String currency, String description, long duration) {
        return createOffer(null, price, currency, description, duration);
    }

    public boolean hasExpired() {
        long now = getCurrentTimeInSeconds();
        return now > creationTime + duration;
    }

    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    private long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }
}
