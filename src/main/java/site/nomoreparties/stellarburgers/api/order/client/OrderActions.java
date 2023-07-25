package site.nomoreparties.stellarburgers.api.order.client;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import site.nomoreparties.stellarburgers.api.BaseClient;
import static io.restassured.RestAssured.given;

public class OrderActions extends BaseClient {
    private static final String ORDER_PATH = "/api/orders";
    private String accessToken;
    private String loginToken;
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order){
        Gson gson = new Gson();
        String json = gson.toJson(order);
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
        accessToken = response.extract().path("accessToken");
        return response;
    }

    @Step("Получение заказов")
    public ValidatableResponse getOrders() {
        RequestSpecification request = given().header("Content-type", "application/json");

        if (loginToken != null) {
            request = request.header("Authorization", loginToken);
        }
        return request.when().get(ORDER_PATH).then().log().all();
    }
}
