package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.auth.AuthLoginRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {

    private static final String USERNAME_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String EMAIL = "modric@gmail.com";
    private static final String PASSWORD = "asdf1234";

    @Test
    void tokenLogin() {
        String token = RestAssured
                .given().log().all()
                .body(new AuthLoginRequest(EMAIL, PASSWORD))
                .contentType(ContentType.JSON)
                .when().post("/login")
                .then().log().all().extract().cookie("token");

        RestAssured
                .given().log().all()
                .cookie("token", token)
                .when().get("/login/check")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("modric"));
    }

}