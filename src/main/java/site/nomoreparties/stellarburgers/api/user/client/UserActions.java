package site.nomoreparties.stellarburgers.api.user.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import site.nomoreparties.stellarburgers.api.BaseClient;
import static io.restassured.RestAssured.given;

public class UserActions extends BaseClient {
    private static final String REGISTER_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String DATA_USER = "/api/auth/user";
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Step("Создание уникального пользователя")
    public ValidatableResponse createUser(User user) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_USER)
                .then().log().all();
        accessToken = response.extract().path("accessToken");
        return response;
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(User user) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then().log().all();
        accessToken = response.extract().path("accessToken");
        return response;
    }

    @Step("Обновление данных пользователя")
    public ValidatableResponse updateUserData() {
        String json = "{\"email\": \"a3sdghjkll@test.com\",\"password\": \"pa$$word\", \"name\": \"Test name\"}";
        RequestSpecification request = given().log().all().header("Content-type", "application/json");

        if (accessToken != null) {
            request = request.header("Authorization", accessToken);
        }
        return request.body(json).when().patch(DATA_USER).then().log().all();
    }


    @Step("Удаление пользователя по токену")
    public ValidatableResponse deleteUser() {
        RequestSpecification request = given().header("Content-type", "application/json");

        if (accessToken != null) {
            request = request.header("Authorization", accessToken);
        }
        return request.when().delete(DATA_USER).then().log().all();
    }
}
   