package site.nomoreparties.stellarburgers.api;

import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.api.order.client.Order;
import site.nomoreparties.stellarburgers.api.order.client.OrderActions;
import site.nomoreparties.stellarburgers.api.order.client.OrderResponse;
import site.nomoreparties.stellarburgers.api.user.client.UserActions;
import site.nomoreparties.stellarburgers.api.user.client.UserGenerator;
import java.net.HttpURLConnection;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Тесты: создание заказа")
public class CreateOrderTest extends BaseTest {
    OrderActions orderActions = new OrderActions();
    UserActions userActions = new UserActions();
    private final UserGenerator generator = new UserGenerator();

    @After
    public void deleteUserAfterTest() { userActions.deleteUser(); }

    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    @Test
    public void testCreatingOrderWithAuthorization(){
        var user = generator.random();
        userActions.createUser(user);
        userActions.loginUser(user);
        Order order = new Order().generic();
        ValidatableResponse response = orderActions.createOrder(order);

        Gson gson = new Gson();
        OrderResponse orderResponse = gson.fromJson(response.extract().asString(), OrderResponse.class);

        OrderResponse expectedResponse = new OrderResponse(
                TestData.ORDER_NAME,
                orderResponse.getOrder(),
                true
        );

        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("name", equalTo(expectedResponse.getName()))
                .body("order.number", equalTo(expectedResponse.getOrder().getNumber()))
                .body("success", equalTo(expectedResponse.getSuccess()));
    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    public void testCreatingOrderWithoutAuthorization(){
        var user = generator.random();
        userActions.createUser(user);
        userActions.setAccessToken(null);
        Order order = new Order().generic();
        ValidatableResponse response = orderActions.createOrder(order);

        Gson gson = new Gson();
        OrderResponse orderResponse = gson.fromJson(response.extract().asString(), OrderResponse.class);

        OrderResponse expectedResponse = new OrderResponse(
                TestData.ORDER_NAME,
                orderResponse.getOrder(),
                true
        );

        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("name", equalTo(expectedResponse.getName()))
                .body("order.number", equalTo(expectedResponse.getOrder().getNumber()))
                .body("success", equalTo(expectedResponse.getSuccess()));
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void testCreatingOrderWithoutIngredients(){
        var user = generator.random();
        userActions.createUser(user);
        userActions.loginUser(user);
        Order order = new Order().generic();
        order.setIngredients(null);
        ValidatableResponse response = orderActions.createOrder(order);

        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body(equalTo(TestData.NO_INGREDIENTS_RESPONSE));
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void testCreatingOrderWithIncorrectIngredientHash(){
        var user = generator.random();
        userActions.createUser(user);
        userActions.loginUser(user);
        Order order = new Order().generic();
        order.setIngredients(TestData.INCORRECT_INGREDIENTS);
        ValidatableResponse response = orderActions.createOrder(order);

        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body(equalTo(TestData.INCORRECT_HASH_RESPONSE));
    }
}
