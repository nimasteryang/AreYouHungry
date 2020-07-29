package comp3350.Group2.areyouhungry.tests.persistence;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.objects.User;
import comp3350.Group2.areyouhungry.objects.FoodCategory;
import comp3350.Group2.areyouhungry.persistence.DataAccess;
import comp3350.Group2.areyouhungry.persistence.DataAccessObject;


public class DataAccessTest extends TestCase{
    private DataAccess dataAccess;

    public DataAccessTest(String arg0){
        super(arg0);
    }

    public void setUp(){
        System.out.println("\nStarting Persistence test DataAccess (using stub)");

        // Use the following statements to run with the stub database:
//        dataAccess = new DataAccessStub();
//        dataAccess.open("Stub");
        // or switch to the real database:
        dataAccess = new DataAccessObject(MainActivity.dbName);
        dataAccess.open(MainActivity.getDBPathName());
        // Note the increase in test execution time.
    }
    public void tearDown(){
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }
    public void testDataAccess(){
        ArrayList<Food> foods;
        Food food;
        String result;

        foods = new ArrayList<Food>();
        result = dataAccess.getFoodSequential(foods);
        assertNull(result);
        assertEquals(6, foods.size());
    }

    public void testGetFoodByID(){
        String foodID = "1";
        Food food = dataAccess.getFoodFromID(foodID);
        Food compareFood = new Food(1, "Fish and Chip",1,10, "Savory", "Easy", "American");
        assertTrue(food.equals(compareFood));
    }

    public void testGetNegativeFoodByID(){
        String foodID = "-1";
        Food food = dataAccess.getFoodFromID(foodID);
        assertNull(food);
    }

    public void testGetNullFoodByID(){
        String foodID = null;
        Food food = dataAccess.getFoodFromID(foodID);
        assertNull(food);
    }

    public void testGetNonExistantFoodByID(){
        String foodID = "738649298";
        Food food = dataAccess.getFoodFromID(foodID);
        assertNull(food);
    }

    public void testEmptyStringGetFoodByID(){
        String foodID = "";
        Food food = dataAccess.getFoodFromID(foodID);
        assertNull(food);
    }

    public void testGetTotalQuestions(){
        int totalTests = dataAccess.getTotalQuestions();
        assertEquals(totalTests,5);
    }

    public void testSetNewUser(){
        int id = 77;
        String username = "Test User";
        User user1 = new User(id,username);
        User userSet = dataAccess.setNewUser(id,username);
        System.out.println(userSet.toString());
        assertEquals(user1, userSet);
    }

    public void testDuplicateSetNewUser(){
        int id = 4;
        String username = "Test User";
        dataAccess.setNewUser(id,username);
        User user2 =  dataAccess.setNewUser(id,username);
        assertNull(user2);
    }

    public void testNegativeIDSetNewUser(){
        int id = -1;
        String username = "Test User";
        User user1 = dataAccess.setNewUser(id,username);
        assertNull(user1);
    }
    public void testNullUsernameSetNewUser(){
        int id = 5;
        String username = null;
        User user1 = dataAccess.setNewUser(id,username);
        assertNull(user1);
    }

    public void testAddNewFoodCategory(){
        int id=1;
        int category=3;
        FoodCategory test = new FoodCategory(id,category);
        FoodCategory result = dataAccess.addFoodCategory(id,category);
        System.out.println(result);
        System.out.println(test);
        assertEquals(test, result);
    }

    public void testAddDuplicateFoodCategory(){
        int id = 1;
        int category=2;
        FoodCategory result = dataAccess.addFoodCategory(id, category);
        assertNull(result);
    }

    public void testNegativeIDAddFoodCategory(){
        int id = -1;
        int category=4;
        FoodCategory result = dataAccess.addFoodCategory(id,category);
        assertNull(result);
    }

    public void testNegativeCategoryAddFoodCategory(){
        int id = 1;
        int category=-4;
        FoodCategory result = dataAccess.addFoodCategory(id,category);
        assertNull(result);
    }

}
