package sweet_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMenu {
    List<Dessert> desserts;
    private String name;
    private String ingredients;
    private String steps;
    private List<String> dessert;

    public RecipeMenu(String name, String ingredients, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }
    public RecipeMenu() {
        this.desserts = new ArrayList<>();
        desserts.add(new Dessert("Chocolate Cake", "Vegetarian", 10.99));
        desserts.add(new Dessert("Fruit Tart", "Vegan", 7.99));
        desserts.add(new Dessert("Ice Cream", "Gluten-Free", 5.99));
        desserts = new ArrayList<>();

    }

    public List<String> getDessertRecipes() {
        return dessert;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
   
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }

    public List<String> getFilteredDesserts() {
        return dessert;
    }
}
