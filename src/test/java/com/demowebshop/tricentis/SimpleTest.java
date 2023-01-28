package com.demowebshop.tricentis;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SimpleTest extends TestBase {


    @Test
    @DisplayName("Проверка авторизации")
    public void authorizationTest() {

        String getCookie =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("Email", "qa@Mail.ru")
                        .formParam("Password", "123456")
                        .formParam("RememberMe", false)
                        .when()
                        .post("/login")
                        .then()
                        .extract()
                        .cookie("NOPCOMMERCE.AUTH");

        open("https://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", getCookie));

    }

    @Test
    @DisplayName("Проверка изменения имени у пользователя")
    public void renameUser() {

                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("Gender", "M")
                        .formParam("FirstName", "Ivor")
                        .formParam("LastName", "Voronin")
                        .formParam("Email", "qa@Mail.ru")
                        .formParam("Password", "123456")
                        .formParam("save-info-button", "Save")
                        .formParam("__RequestVerificationToken", "cT0kzlBshQS-pT4__wJI7DRHt3zlPgLUmoH43IAscZtOPS5pAgVPctt9aNdIgN6s6RxCSjmPttxC_2j-f2rARQE7br28LOpFrLNIZl401Is1")
                        .when()
                        .post("/customer/info")
                        .then()
                        .log().all()
                        .statusCode(302);


    }
}
