package sweet_2024;

import java.util.Scanner;

public class StoreMenu {
    private RecipeMenu recipeMenu;
    private String selectedDessert;
    public StoreMenu(RecipeMenu recipeMenu) {
        this.recipeMenu = recipeMenu;
    }

    public void displayStore() {
        recipeMenu.displayRecipes();
    }
    public String navigateToStoreMenu() {
        return "Navigated to the store menu.";
    }

    public void selectDessert(String dessert) {
        this.selectedDessert = dessert;
    }
    public StoreMenu(){

    }
    public String getSelectedDessert() {
        return selectedDessert;
    }

    public boolean choosePurchaseOption() {
        return selectedDessert != null;
    }
    public boolean completePurchase() {
        if (selectedDessert != null) {
            System.out.println("Purchase completed for: " + selectedDessert);
            return true;
        }
        return false;
    }
    public void purchaseDessert(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the dessert you wish to purchase:");
        String dessertName = scanner.nextLine();

        for (Dessert dessert : recipeMenu.desserts) {
            if (dessert.getName().equalsIgnoreCase(dessertName)) {
                System.out.println("Purchasing " + dessert.getName() + " for $" + dessert.getPrice());
                System.out.println("Purchase completed successfully!");
                return;
            }
        }
        System.out.println("Dessert not found. Please try again.");
    }
}
