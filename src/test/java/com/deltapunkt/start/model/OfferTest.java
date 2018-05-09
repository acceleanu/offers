package com.deltapunkt.start.model;

import org.junit.Test;

import static alexh.Unchecker.unchecked;
import static com.deltapunkt.start.model.Offer.createOffer;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferTest {
    @Test
    public void offerExpiresAfterOneSecond() {
        Offer offer = createOffer(
                "id1",
                12345,
                "GBP",
                "desc1",
                1
        );
        assertThat(offer.hasExpired()).isFalse();
        unchecked(() -> Thread.sleep(2000));
        assertThat(offer.hasExpired()).isTrue();
    }
}
