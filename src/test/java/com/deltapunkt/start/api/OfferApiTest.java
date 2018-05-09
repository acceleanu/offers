package com.deltapunkt.start.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static com.deltapunkt.start.util.Util.fluentMap;
import static com.deltapunkt.start.util.Util.toJson;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OfferApiTest {
    public static final String GBP = "GBP";
    public static final String PRODUCT_DESCRIPTION = "This is a great product offer";
    public static final int PRODUCT_PRICE_x100 = 10050;
    public static final int ONE_HOUR = (int)Duration.ofHours(1).getSeconds();

    @LocalServerPort
    int port;

    @Test
    public void createNewOffer() {
        String payload = toJson(
            fluentMap()
                .append("price", PRODUCT_PRICE_x100) // 100.50
                .append("currency", GBP)
                .append("description", PRODUCT_DESCRIPTION)
                .append("duration", ONE_HOUR)
        );
        given()
            .port(port)
            .contentType(JSON)
            .body(payload)
        .when()
            .post("/offer")
        .then()
            .assertThat()
                .statusCode(200)
                .and().body("id", notNullValue())
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(ONE_HOUR))
        ;
    }

    @Test
    public void createTwoOffers() {
        String payload = toJson(
            fluentMap()
                .append("price", PRODUCT_PRICE_x100) // 100.50
                .append("currency", GBP)
                .append("description", PRODUCT_DESCRIPTION)
                .append("duration", ONE_HOUR)
        );

        String id1 = given()
            .port(port)
            .contentType(JSON)
            .body(payload)
        .when()
            .post("/offer")
        .then()
            .assertThat()
                .statusCode(200)
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(ONE_HOUR))
                .extract().jsonPath().getString("id")
        ;

        String id2 = given()
            .port(port)
            .contentType(JSON)
            .body(payload)
        .when()
            .post("/offer")
        .then()
            .assertThat()
                .statusCode(200)
                .and().body("price", equalTo(PRODUCT_PRICE_x100))
                .and().body("description", equalTo(PRODUCT_DESCRIPTION))
                .and().body("duration", equalTo(ONE_HOUR))
                .extract().jsonPath().getString("id")
        ;

        assertThat(id1).isNotEqualTo(id2);
    }

}
