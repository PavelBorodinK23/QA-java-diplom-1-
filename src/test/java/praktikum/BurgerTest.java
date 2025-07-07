package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static praktikum.IngredientType.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;
    private Bun bun;
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @Parameterized.Parameter
    public IngredientType ingredientType;

    @Parameterized.Parameters
    public static List<IngredientType> data() {
        return Arrays.asList(FILLING, SAUCE);
    }

    @Before
    public void setUp() {
        burger = new Burger();
        bun = Mockito.mock(Bun.class);
        ingredient1 = Mockito.mock(Ingredient.class);
        ingredient2 = Mockito.mock(Ingredient.class);

        Mockito.when(bun.getName()).thenReturn("white bun");
        Mockito.when(bun.getPrice()).thenReturn(200f);

        Mockito.when(ingredient1.getType()).thenReturn(FILLING);
        Mockito.when(ingredient1.getName()).thenReturn("cutlet");
        Mockito.when(ingredient1.getPrice()).thenReturn(100f);

        Mockito.when(ingredient2.getType()).thenReturn(SAUCE);
        Mockito.when(ingredient2.getName()).thenReturn("hot sauce");
        Mockito.when(ingredient2.getPrice()).thenReturn(50f);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertNotNull(burger.bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(ingredient1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 1);
        assertEquals(ingredient2, burger.ingredients.get(0));
        assertEquals(ingredient1, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        float expectedPrice = bun.getPrice() * 2 + ingredient1.getPrice() + ingredient2.getPrice();
        assertEquals(expectedPrice, burger.getPrice(), 0.0f);
    }

    @Test
    public void testGetReceipt() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        String expectedReceipt = String.format("(==== %s ====)%n", bun.getName()) +
                String.format("= %s %s =%n", ingredient1.getType().toString().toLowerCase(), ingredient1.getName()) +
                String.format("= %s %s =%n", ingredient2.getType().toString().toLowerCase(), ingredient2.getName()) +
                String.format("(==== %s ====)%n", bun.getName()) +
                String.format("%nPrice: %f%n", burger.getPrice());

        assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    public void testGetReceiptWithParameterizedIngredients() {
        Mockito.when(ingredient1.getType()).thenReturn(ingredientType);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        String expectedType = ingredientType.toString().toLowerCase();
        assertTrue(burger.getReceipt().contains(expectedType));
    }
}
