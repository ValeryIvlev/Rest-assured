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
                .spec(response200)
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
                .spec(response200)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals("morpheus", data.getName());
        assertEquals("zion resident", data.getJob());

    }

    @Test
    @DisplayName("Проверка создания пользователя")
    public void createUser() {

        String newUser = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        LombokUserData data = given()
                .spec(request)
                .when()
                .body(newUser)
                .post("/users")
                .then()
                .spec(response201)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals("morpheus", data.getName());
        assertEquals("leader", data.getJob());
    }
    @Test
    @DisplayName("Проверка запроса данных у несуществуюшего пользователя")
    public void checkUnknownUser() {

         given()
                .spec(request)
                .when()
                .get("/users/23")
                .then()
                .spec(response404)
                .log().all();
    }
    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    public void registerNewUser() {

        String newUser = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .spec(request)
                .when()
                .contentType(ContentType.JSON)
                .body(newUser)
                .post("/register")
                .then()
                .spec(response200)
                .log().all()
                .body("id", is(notNullValue()))
                .body("token", is(notNullValue()));

    }

}
