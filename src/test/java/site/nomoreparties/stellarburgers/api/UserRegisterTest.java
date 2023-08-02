package site.nomoreparties.stellarburgers.api;

import org.junit.After;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.*;
import site.nomoreparties.stellarburgers.api.user.client.UserActions;
import site.nomoreparties.stellarburgers.api.user.client.UserGenerator;
import java.net.HttpURLConnection;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Тесты: создание пользователя")
public class UserRegisterTest extends BaseTest {
    UserActions userActions =  new UserActions();
    private final UserGenerator generator = new UserGenerator();

    @After
    public void deleteUserAfterTest() { userActions.deleteUser(); }

    @DisplayName("Создание уникального пользователя со всеми валидными полями")
    @Test
    public void testCreatingUniqueUserWithValidFields() {
        var user = generator.random();
        ValidatableResponse response = userActions.createUser(user);
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("success", equalTo(true));
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
     public void testCreatingUserThatHasBeenAlreadyRegistered()    {
        var user = generator.generic();
        userActions.createUser(user);
        user.setEmail("test-email@test.com");
        ValidatableResponse response = userActions.createUser(user);
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body(equalTo(TestData.EXISTING_USER_RESPONSE));
    }

    @DisplayName("Создание пользователя без поля password")
    @Test
    public void testCreatingUserWithoutPasswordField(){
        var user = generator.random();
        user.setPassword(null);
        ValidatableResponse response = userActions.createUser(user);
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .and()
                .body(equalTo(TestData.NO_PASSWORD_RESPONSE));
    }
}