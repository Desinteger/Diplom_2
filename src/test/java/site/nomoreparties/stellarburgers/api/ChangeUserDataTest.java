package site.nomoreparties.stellarburgers.api;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.api.user.client.UserActions;
import site.nomoreparties.stellarburgers.api.user.client.UserGenerator;
import java.net.HttpURLConnection;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Тесты: обновление информации пользователя")
    public class ChangeUserDataTest extends BaseTest {
        UserActions userActions = new UserActions();
        private final UserGenerator generator = new UserGenerator();

        @After
        public void deleteUserAfterTest() { userActions.deleteUser(); }

        @DisplayName("Обновление информации о пользователе с авторизацией")
        @Test
        public void testChangeUserDataWithAuthorization() {
                var user = generator.random();
                userActions.createUser(user);
                userActions.loginUser(user);
                ValidatableResponse response = userActions.updateUserData();
                response.assertThat().statusCode(HttpURLConnection.HTTP_OK)
                        .and()
                        .body(equalTo(TestData.UPDATE_WITH_AUTHORIZATION));
        }

    @DisplayName("Обновление информации о пользователе без авторизации")
    @Test
    public void testChangeUserDataWithoutAuthorization() {
            var user = generator.random();
            userActions.createUser(user);
            userActions.setAccessToken(null);
            ValidatableResponse response = userActions.updateUserData();

            response.assertThat().statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .and()
                .body(equalTo(TestData.N0_AUTHORIZATION_RESPONSE));
     }
}