package org.zayac.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.zayac.api.lombok.LombokUserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zayac.api.Specs.*;
import static org.zayac.api.helpers.CustomApiListener.withCustomTemplates;

public class TestApi {

    @Test
    @DisplayName("Проверка данных пользователя под id 2")
    public void checkNameUser2() {

        LombokUserData data = given()
                .spec(request)
                .filter(withCustomTemplates())
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
    LombokUserData lombokUserData = new LombokUserData();

        lombokUserData.setName("morpheus");
        lombokUserData.setJob("zion resident");

        LombokUserData data = given()
                .spec(request)
                .filter(withCustomTemplates())
                .when()
                .body(lombokUserData)
                .put("/users/2")
                .then()
                .spec(response200)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals(lombokUserData.getName(), data.getName());
        assertEquals(lombokUserData.getJob(), data.getJob());

    }

    @Test
    @DisplayName("Проверка создания пользователя")
    public void createUser() {
        LombokUserData lombokUserData = new LombokUserData();

        lombokUserData.setName("morpheus");
        lombokUserData.setJob("leader");

        LombokUserData data = given()
                .spec(request)
                .filter(withCustomTemplates())
                .when()
                .body(lombokUserData)
                .post("/users")
                .then()
                .spec(response201)
                .log().all()
                .extract().as(LombokUserData.class);
        assertEquals(lombokUserData.getName(), data.getName());
        assertEquals(lombokUserData.getJob(), data.getJob());
    }
    @Test
    @DisplayName("Проверка запроса данных у несуществуюшего пользователя")
    public void checkUnknownUser() {

        given()
                .spec(request)
                .filter(withCustomTemplates())
                .when()
                .get("/users/23")
                .then()
                .spec(response404)
                .log().all();
    }
    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    public void registerNewUser() {
        LombokUserData lombokUserData = new LombokUserData();

        lombokUserData.setEmail("eve.holt@reqres.in");
        lombokUserData.setPassword("pistol");

        given()
                .spec(request)
                .filter(withCustomTemplates())
                .when()
                .body(lombokUserData)
                .post("/register")
                .then()
                .spec(response200)
                .log().all()
                .body("id", is(notNullValue()))
                .body("token", is(notNullValue()));

    }
}

