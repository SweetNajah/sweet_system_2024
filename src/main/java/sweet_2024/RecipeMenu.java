package sweet_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMenu {
    List<Dessert> desserts;

    public RecipeMenu() {
        this.desserts = new ArrayList<>();
        // Sample desserts
        desserts.add(new Dessert("Chocolate Cake", "Vegetarian", 10.99));
        desserts.add(new Dessert("Fruit Tart", "Vegan", 7.99));
        desserts.add(new Dessert("Ice Cream", "Gluten-Free", 5.99));
    }

    public void displayRecipes() {
        System.out.println("Available Dessert Recipes:");
        for (Dessert dessert : desserts) {
            System.out.println(dessert);
        }
    }

    public void filterRecipes(String dietaryNeed) {
        desserts.removeIf(dessert -> !dessert.getDietaryInfo().equalsIgnoreCase(dietaryNeed));


    }
}
