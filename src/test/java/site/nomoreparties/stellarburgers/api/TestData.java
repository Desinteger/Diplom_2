package site.nomoreparties.stellarburgers.api;

public class TestData {
    public static final String UPDATE_WITH_AUTHORIZATION = "{\"success\":true,\"user\":{\"email\":\"a3sdghjkll@test.com\",\"name\":\"Test name\"}}";
    public static final String INCORRECT_FIELDS_RESPONSE = "{\"success\":false,\"message\":\"email or password are incorrect\"}";
    public static final String ORDER_NAME = "Бессмертный флюоресцентный бургер";
    public static final String EXISTING_USER_RESPONSE = "{\"success\":false,\"message\":\"User already exists\"}";
    public static final String NO_PASSWORD_RESPONSE = "{\"success\":false,\"message\":\"Email, password and name are required fields\"}";
    public static final String N0_AUTHORIZATION_RESPONSE = "{\"success\":false,\"message\":\"You should be authorised\"}";
    public static final String NO_INGREDIENTS_RESPONSE = "{\"success\":false,\"message\":\"Ingredient ids must be provided\"}";
    public static final String INCORRECT_HASH_RESPONSE = "{\"success\":false,\"message\":\"One or more ids provided are incorrect\"}";
    public static final String[] INCORRECT_INGREDIENTS = {"61c0c5a71d1f82001bdaaa62", "261c0c5a71d1f82001bdaaa6"};

}
