package com.deltapunkt.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StatusTest {
    @LocalServerPort
    int port;

    @Test
    public void getStatus() {
        given()
            .port(port)
        .when()
            .get("/status")
        .then()
            .statusCode(200)
            .body(equalTo("ok"))
        ;
    }
}
