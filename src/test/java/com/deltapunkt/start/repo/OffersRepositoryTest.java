package com.deltapunkt.start.repo;

import com.deltapunkt.start.model.Offer;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static com.deltapunkt.start.model.Offer.createOffer;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;

public class OffersRepositoryTest {
    private static final Offer OFFER1 = createOffer(
            1234L,
            "USD",
            "product1",
            Duration.ofMinutes(3).getSeconds()
    );
    private OffersRepository repository;

    @Before
    public void setUp() {
        repository = new OffersRepository();
    }

    @Test
    public void createNewOfferInRepository() {
        Offer offer = repository.addOffer(OFFER1);

        assertThat(offer)
                .extracting(
                        "id",
                        "price",
                        "currency",
                        "description",
                        "duration"
                )
                .containsSequence(
                        "0",
                        OFFER1.getPrice(),
                        OFFER1.getCurrency(),
                        OFFER1.getDescription(),
                        OFFER1.getDuration()
                );
    }

    @Test
    public void getOfferThatExistsInTheRepository() {
        Offer offer = repository.addOffer(OFFER1);
        assertThat(repository.getOffer(offer.getId()).get())
                .isEqualToComparingFieldByField(createOffer(offer.getId(), offer));
    }

    @Test
    public void getOffersFromTheRepository() {
        Offer offer1 = repository.addOffer(OFFER1);
        Offer offer2 = repository.addOffer(OFFER1);
        assertThat(repository.getOffers())
                .containsExactlyInAnyOrder(offer1, offer2);
    }

    @Test
    public void getOfferThatDoesNotExistInTheRepository() {
        assertThat(repository.getOffer(random(10))).isEmpty();
    }
}
