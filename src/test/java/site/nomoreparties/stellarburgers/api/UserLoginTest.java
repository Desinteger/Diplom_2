package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.api.user.client.UserActions;
import site.nomoreparties.stellarburgers.api.user.client.UserGenerator;
import java.net.HttpURLConnection;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Тесты: авторизация пользователя")
public class UserLoginTest extends BaseTest {
    UserActions userActions =  new UserActions();
    private final UserGenerator generator = new UserGenerator();

    @After
    public void deleteUserAfterTest() { userActions.deleteUser(); }

    @DisplayName("Авторизация существующего пользователя")
    @Test
    public void testLoginExistingUser(){
        var user = generator.generic();
        userActions.createUser(user);
        ValidatableResponse response = userActions.loginUser(user);
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
    }

    @DisplayName("Авторизация с неверным логином и паролем")
    @Test
    public void testLoginWithIncorrectLoginAndPassword(){
        var user = generator.generic();
        user.setName("IncorrectLogin");
        user.setPassword("IncorrectPassword");
        ValidatableResponse response = userActions.loginUser(user);
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body(equalTo(TestData.INCORRECT_FIELDS_RESPONSE));
    }
}
