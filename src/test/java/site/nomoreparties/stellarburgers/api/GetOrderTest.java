package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.order.client.Order;
import site.nomoreparties.stellarburgers.api.order.client.OrderActions;
import site.nomoreparties.stellarburgers.api.user.client.UserActions;
import site.nomoreparties.stellarburgers.api.user.client.UserGenerator;
import java.net.HttpURLConnection;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Тесты: получение заказов")
public class GetOrderTest extends BaseTest {
    OrderActions orderActions = new OrderActions();
    UserActions userActions = new UserActions();
    private final UserGenerator generator = new UserGenerator();

    @After
    public void deleteUserAfterTest() { userActions.deleteUser(); }

    @DisplayName("Получение заказов авторизированного пользователя")
    @Test
    public void testGettingOrderWithAuthorization() {
        var user = generator.random();
        userActions.createUser(user);
        var loginResponse = userActions.loginUser(user);
        String accessToken = loginResponse.extract().path("accessToken");
        orderActions.setLoginToken(accessToken);
        Order order = new Order().generic();
        orderActions.createOrder(order);

        ValidatableResponse response = orderActions.getOrders();
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
    }

        @DisplayName("Получение заказов неавторизированного пользователя")
        @Test
        public void testGettingOrderWithoutAuthorization() {
        Order order = new Order().generic();
        orderActions.createOrder(order);
        ValidatableResponse response = orderActions.getOrders();
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body(equalTo(TestData.N0_AUTHORIZATION_RESPONSE));
    }
}
