package com.demowebshop.tricentis;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    static void go() {
        baseURI = "https://demowebshop.tricentis.com";
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
    }

}
