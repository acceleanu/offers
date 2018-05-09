package com.deltapunkt.start.repo;

import com.deltapunkt.start.model.Offer;
import org.junit.Test;

import java.time.Duration;

import static com.deltapunkt.start.model.Offer.createOffer;
import static org.assertj.core.api.Assertions.assertThat;

public class OffersRepositoryTest {
    @Test
    public void createNewOfferInRepository() {
        OffersRepository repository = new OffersRepository();
        Offer offer = createOffer(
                1234L,
                "USD",
                "product1",
                Duration.ofMinutes(3).getSeconds()
        );
        Offer offerWithId = repository.addOffer(offer);

        assertThat(offerWithId)
                .extracting(
                        "id",
                        "price",
                        "currency",
                        "description",
                        "duration"
                )
                .containsSequence(
                        "0",
                        offer.getPrice(),
                        offer.getCurrency(),
                        offer.getDescription(),
                        offer.getDuration()
                );
    }
}
