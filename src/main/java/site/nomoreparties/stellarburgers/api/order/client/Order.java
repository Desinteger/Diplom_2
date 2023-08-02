package site.nomoreparties.stellarburgers.api.order.client;

import java.util.Arrays;

public class Order {
        private String[] ingredients;

        public Order(String[] ingredients) {
            this.ingredients = ingredients;
        }

        public Order() {}

        public String[] getIngredients() {
            return ingredients;
        }

        public void setIngredients(String[] ingredients) {
            this.ingredients = ingredients;
        }

        public static Order withIngredients(String... ingredientIds) {
            return new Order(ingredientIds);
        }

        @Override
        public String toString() {
            return "Order{" +
                    "ingredients=" + Arrays.toString(ingredients) +
                    '}';
        }

        public Order generic() {
            return Order.withIngredients("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        }
    }
