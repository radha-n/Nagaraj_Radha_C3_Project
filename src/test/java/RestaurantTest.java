import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void Add_defaultRestaurant_details(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne",269);
    }

    public int getMenuSize(){
        return restaurant.getMenu().size();
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        LocalTime betweenTime = LocalTime.parse("14:00:00");
        Restaurant spyRestaurant = Mockito.spy(restaurant);


        //Case1: To Test whether the restaurant is open at 2:00 pm
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(betweenTime);
        assertTrue(spyRestaurant.isRestaurantOpen());

        //Case2: To Test whether the restaurant is open at 10.30 am
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(openingTime);
        assertTrue(spyRestaurant.isRestaurantOpen());

        //Case3: To Test whether the restaurant is open at 21:59 pm
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(closingTime.minusMinutes(1));
        assertTrue(spyRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime beforeOpeningTime = LocalTime.parse("10:29:00");
        LocalTime afterClosingTime = LocalTime.parse("22:01:00");
        Restaurant spyRestaurant = Mockito.spy(restaurant);


        //Case1: To Test whether the restaurant is open at 10:29 am
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(beforeOpeningTime);
        assertFalse(spyRestaurant.isRestaurantOpen());

        //Case1: To Test whether the restaurant is open at 22:01 pm
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(afterClosingTime);
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = getMenuSize();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = getMenuSize();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
         assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}