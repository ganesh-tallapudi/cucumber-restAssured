package Utils;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {


    @Before("@SmokeTest")
    public static void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/";
    }
}
