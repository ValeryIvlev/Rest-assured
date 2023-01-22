package org.zayac.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.zayac.api.lombok.LombokUserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zayac.api.Specs.*;

public class TestApi {

    @Test
    @DisplayName("Проверка данных пользователя под id 2")
    public void checkNameUser2() {

        LombokUserData data = given()
                .spec(request)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseOk)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals(2, data.getUser().getId());
        assertEquals("fuchsia rose", data.getUser().getName());

    }

    @Test
    @DisplayName("Проверка изменения должности у пользователя")
    public void updateJob() {

        String infoBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        LombokUserData data = given()
                .spec(request)
                .when()
                .body(infoBody)
                .put("/users/2")
                .then()
                .spec(responseOk)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals("morpheus", data.getUser().getName());
        assertEquals("zion resident", data.getUser().getJob());
        // .body("name", is("morpheus"))

        //  .body("job", is("zion resident"));

    }
}
