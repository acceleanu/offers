package com.deltapunkt.start.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static alexh.Unchecker.unchecked;
import static com.deltapunkt.start.util.Util.fluentMap;
import static com.deltapunkt.start.util.Util.toJson;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OffersApiTest {
    static final String GBP = "GBP";
    static final String PRODUCT_DESCRIPTION = "This is a great product offer";
    static final int PRODUCT_PRICE_x100 = 10050;
    static final int ONE_HOUR = (int)Duration.ofHours(1).getSeconds();
    static final int ONE_SECOND = 1;

    @LocalServerPort
    int port;

    @Test
    public void createOneOffer() {
        given()
            .port(port)
            .contentType(JSON)
            .body(createPayload(ONE_HOUR))
        .when()
            .post("/offers")
        .then()
            .assertThat()
                .statusCode(SC_CREATED)
                .and().body("id", notNullValue())
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(ONE_HOUR))
        ;
    }

    @Test
    public void createTwoOffers() {
        String id1 = createOffer(ONE_HOUR);
        String id2 = createOffer(ONE_HOUR);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void getCreatedOffer() {
        String id = createOffer(ONE_HOUR);

        given()
            .port(port)
        .when()
            .get("/offers/{id}", id)
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .and().body("id", equalTo(id))
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(ONE_HOUR))
        ;
    }

    @Test
    public void offerExpiresAfterOneSecond() {
        String id = createOffer(ONE_SECOND);
        unchecked(() -> Thread.sleep(2000));
        given()
            .port(port)
        .when()
            .get("/offers/{id}", id)
        .then()
            .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void cancelledOfferIsNoLongerAvailable() {
        String id = createOffer(ONE_HOUR);
        given()
            .port(port)
        .when()
            .get("/offers/{id}", id)
        .then()
            .assertThat()
                .statusCode(SC_OK);

        given()
            .port(port)
        .when()
            .delete("/offers/{id}", id)
        .then()
            .assertThat()
                .statusCode(SC_NO_CONTENT);

        given()
            .port(port)
        .when()
            .get("/offers/{id}", id)
        .then()
            .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    private String createOffer(int duration) {
        return given()
            .port(port)
            .contentType(JSON)
            .body(createPayload(duration))
        .when()
            .post("/offers")
        .then()
            .assertThat()
                .statusCode(SC_CREATED)
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(duration))
                .extract().jsonPath().getString("id");
    }

    private static String createPayload(int duration) {
        return toJson(
            fluentMap()
                .append("price", PRODUCT_PRICE_x100) // 100.50
                .append("currency", GBP)
                .append("description", PRODUCT_DESCRIPTION)
                .append("duration", duration)
        );
    }
}
