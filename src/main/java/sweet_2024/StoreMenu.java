package sweet_2024;

import java.util.Scanner;

public class StoreMenu {
    private final RecipeMenu recipeMenu;

    public StoreMenu(RecipeMenu recipeMenu) {
        this.recipeMenu = recipeMenu;
    }

    public void displayStore() {
        recipeMenu.displayRecipes();
    }

    public void purchaseDessert(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the dessert you wish to purchase:");
        String dessertName = scanner.nextLine();

        for (Dessert dessert : recipeMenu.desserts) {
            if (dessert.getName().equalsIgnoreCase(dessertName)) {
                System.out.println("Purchasing " + dessert.getName() + " for $" + dessert.getPrice());
                // Simulate purchase
                System.out.println("Purchase completed successfully!");
                return;
            }
        }

        System.out.println("Dessert not found. Please try again.");
    }
}
