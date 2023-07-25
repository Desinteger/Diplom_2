package site.nomoreparties.stellarburgers.api.user.client;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public User generic() {
        return new User("test-email@test.com", "pa$$word", "BurgerLover");
    }

    public static String generateRandomEmail(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return RandomStringUtils.random(length, allowedChars) + "@example.com";
    }

    public User random() {
        String randomEmail = generateRandomEmail(10);
        return new User(randomEmail, RandomStringUtils.randomAlphanumeric(5, 10), "Dmitrii");
    }
}
