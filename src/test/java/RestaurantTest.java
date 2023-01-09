import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setupTestRestaurant() {
        restaurant = new Restaurant("Amelie's cafe", "Chennai", LocalTime.parse("08:00:00"), LocalTime.parse("16:00:00"));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime testTime = LocalTime.parse("10:00:00");
        Restaurant spyRestaurant = spy(restaurant);
        doReturn(testTime).when(spyRestaurant).getCurrentTime();
        assertTrue(spyRestaurant.isRestaurantOpen(), "Restaurant must be open in this test case");
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime testTime = LocalTime.parse("20:00:00");
        Restaurant spyRestaurant = spy(restaurant);
        doReturn(testTime).when(spyRestaurant).getCurrentTime();
        assertFalse(spyRestaurant.isRestaurantOpen(), "Restaurant must be open in this test case");

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_order_total_for_valid_menu_items_should_return_expected_number() throws itemNotFoundException {
        List<String> order_items = new ArrayList<String>();
        order_items.add("Sweet corn soup");
        order_items.add("Vegetable lasagne");
        assertTrue(restaurant.orderTotal(order_items) == 388);
    }

    @Test
    public void get_order_total_for_item_not_in_menu_should_throw_exception() throws itemNotFoundException {
        List<String> order_items = new ArrayList<String>();
        order_items.add("Unexpected item");
        assertThrows(itemNotFoundException.class,
                () -> restaurant.orderTotal(order_items));
    }

}