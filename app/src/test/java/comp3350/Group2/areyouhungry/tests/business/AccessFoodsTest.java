package comp3350.Group2.areyouhungry.tests.business;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Iterator;


import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.Services;
import comp3350.Group2.areyouhungry.business.AccessFoods;
import comp3350.Group2.areyouhungry.business.AccessUsers;
import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.objects.User;
import comp3350.Group2.areyouhungry.tests.persistence.DataAccessStub;

public class AccessFoodsTest extends TestCase{

    public static String dbName = MainActivity.dbName;

    public AccessFoodsTest(String arg0){
        super(arg0);
    }


    public void testGetEmptyFavourites(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));

        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        User tempUser = new User(666,"i like no food");
        accessFood.getFavouriteFoodsByUser(tempUser,foodList);
        assertTrue(foodList.isEmpty());
        Services.closeDataAccess();
    }
    public void testGetEmptyLikes(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User tempUser = new User(666,"i like no food");
        boolean hasFood = accessFood.getFoodLikedByUser(tempUser,food);
        assertFalse(hasFood);
        Services.closeDataAccess();
    }
    public void testGetEmptyDislikes(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User tempUser = new User(666,"i like no food");
        boolean hasFood = accessFood.getFoodDislikedByUser(tempUser,food);
        assertFalse(hasFood);
        Services.closeDataAccess();
    }

    public void testAddingFavourite(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        User newUser = new User(777,"i like one food");
        accessFood.getRandom(foodList);
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),true);
        foodList.clear();
        accessFood.getFavouriteFoodsByUser(newUser,foodList);
        assertFalse(foodList.isEmpty()); //This should not be empty now as we have a favourited food!!
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),false);
        foodList.clear();
        Services.closeDataAccess();
    }
    public void testAddingLikedFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User newUser = new User(777,"i like one food");

        accessFood.setFoodLikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodLikedByUser(newUser,food));

        Services.closeDataAccess();
    }
    public void testAddingDislikedFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User newUser = new User(777,"i like one food");

        accessFood.setFoodDislikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodDislikedByUser(newUser,food));

        Services.closeDataAccess();
    }

    public void testUnfavouriteFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        /* Accesses a random food and favourites it. */
        accessFood.getRandom(foodList);
        User newUser = new User(777,"i dont like it now");
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),true);
        foodList.clear();
        accessFood.getFavouriteFoodsByUser(newUser,foodList);
        assertFalse(foodList.isEmpty());
        /* Gets favourite food list and removed item added. */
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),false);
        foodList.clear();
        accessFood.getFavouriteFoodsByUser(newUser,foodList);
        assertTrue(foodList.isEmpty());

        Services.closeDataAccess();
    }
    public void testUnLikingFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User newUser = new User(777,"i like one food");

        accessFood.setFoodLikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodLikedByUser(newUser,food));

        accessFood.setFoodLikedByUser(newUser, food.getFoodID(), false);

        assertFalse(accessFood.getFoodLikedByUser(newUser,food));

        Services.closeDataAccess();


    }
    public void testUnDislikingFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User newUser = new User(777,"i like one food");

        accessFood.setFoodDislikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodDislikedByUser(newUser,food));

        accessFood.setFoodDislikedByUser(newUser, food.getFoodID(), false);

        assertFalse(accessFood.getFoodDislikedByUser(newUser,food));

        Services.closeDataAccess();


    }

    public void testFavouriteTwice(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        /* This portion grabs a random food, favourites it, clears our foodList and fills foodList with all our favourited foods. */
        accessFood.getRandom(foodList);
        /* Setting its favourite to true twice in a row. */
        User newUser = new User(777,"i like food very much");
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),true);
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),true);
        foodList.clear();
        accessFood.getFavouriteFoodsByUser(newUser,foodList);
        assertFalse(foodList.isEmpty());
        /* Removing the favourite food for future tests. */
        accessFood.setFoodFavouriteByUser(newUser,foodList.get(0).getFoodID(),false);
        foodList.clear();
        Services.closeDataAccess();
    }
    public void testLikeTwice(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food1 = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        Food food2 = new Food(2, "Greek Salad",1,10, "Fresh", "Easy", "Greek");

        User newUser = new User(777,"i like two food");

        accessFood.setFoodLikedByUser(newUser, food1.getFoodID(), true);
        accessFood.setFoodLikedByUser(newUser, food2.getFoodID(), true);

        assertTrue(accessFood.getFoodLikedByUser(newUser,food1));
        assertTrue(accessFood.getFoodLikedByUser(newUser,food2));

        Services.closeDataAccess();
    }
    public void testDislikeTwice(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food1 = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        Food food2 = new Food(2, "Greek Salad",1,10, "Fresh", "Easy", "Greek");

        User newUser = new User(777,"i like two food");

        accessFood.setFoodDislikedByUser(newUser, food1.getFoodID(), true);
        accessFood.setFoodDislikedByUser(newUser, food2.getFoodID(), true);

        assertTrue(accessFood.getFoodDislikedByUser(newUser,food1));
        assertTrue(accessFood.getFoodDislikedByUser(newUser,food2));

        Services.closeDataAccess();
    }

    public void testLikingThenDisliking(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        Food food = new Food(1, "Baked Salmon",3,20, "Savoury", "Easy", "American");
        User newUser = new User(777,"i like one food");

        accessFood.setFoodLikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodLikedByUser(newUser,food));

        accessFood.setFoodLikedByUser(newUser, food.getFoodID(), false);

        assertFalse(accessFood.getFoodLikedByUser(newUser,food));

        accessFood.setFoodDislikedByUser(newUser, food.getFoodID(), true);

        assertTrue(accessFood.getFoodDislikedByUser(newUser,food));

        Services.closeDataAccess();

    }

    public void testAddNullFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        Food newItem = null;
        accessFood.addFood(newItem);
        accessFood.getFoods(foodList);
        assertFalse(foodList.contains(null));
        Services.closeDataAccess();
    }

    public void testAddRealFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        Food food = new Food(7, "test food",7,10, "Sweet", "Easy", "American");
        accessFood.addFood(food);
        accessFood.getFoods(foodList);
        assertTrue(foodList.contains(food));
        Services.closeDataAccess();
    }

    public void testAddFoodInvalidID(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        Food newItem;
        int testnum = 0;
        try{
            newItem = new Food(-1, "test",7,100, "test", "test", "test");
            testnum = 1;
        }catch (Exception e){
            testnum = 0;
        }
        assertEquals(0,testnum);
        Services.closeDataAccess();
    }

    public void testFoodMissingName(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        Food food;
        int testnum = 0;
        try{
            food = new Food(7, "", 7, 100, "test", "test", "test");
             testnum = 1;
        }catch(Exception e){
            testnum = 0;
        }
        assertEquals(0,testnum);
        Services.closeDataAccess();
    }

    public void testSequential(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodList = new ArrayList<>();
        int foodId;
        int num = 1;
        Iterator<Food> foodIterator;

        accessFood.getFoods(foodList);
        assertEquals(6, foodList.size());
        foodIterator = foodList.iterator();

        /* This part verifies that they're all different IDs/foods. */
        while(foodIterator.hasNext()){
            foodId = num;
            assertEquals(String.valueOf(foodId), foodIterator.next().getFoodID());
            num++;
        }
        Services.closeDataAccess();
    }

    public void testFoodCriteriaSearchEmpty(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodResult = new ArrayList<>();
        accessFood.foodCriteriaSearch(new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),foodResult);
        assertEquals(foodResult.size(),0);
    }

    public void testFoodCriteriaSearch(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFood = new AccessFoods();
        ArrayList<Food> foodResult = new ArrayList<>();
        ArrayList<String> totalTimeList = new ArrayList<>();
        totalTimeList.add("10");
        ArrayList<String> flavourList = new ArrayList<>();
        flavourList.add("Sweet");
        ArrayList<String> difficultList = new ArrayList<>();
        difficultList.add("Easy");
        ArrayList<String> ethnicityList = new ArrayList<>();
        ethnicityList.add("American");
        accessFood.foodCriteriaSearch(totalTimeList,flavourList,difficultList,ethnicityList,foodResult);
        assertEquals(foodResult.size(),1);
        assertEquals(foodResult.get(0).getFoodName(),"Banana Split");
        Services.closeDataAccess();
    }

    public void testGetFoodsByCategory(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFoods = new AccessFoods();
        ArrayList<Food> foodResult = new ArrayList<>();
        accessFoods.getFoodsByCategory("Grain", foodResult);
        assertEquals(foodResult.size(), 2);
        assertEquals(foodResult.get(0).getFoodName(), "Spicy Spaghetti");
        assertEquals(foodResult.get(1).getFoodName(), "Egg Fried Rice");
    }
    public void testGetImageByFood(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFoods = new AccessFoods();
        assertEquals("food1",accessFoods.getImagebyFood("1"));
        assertNull(accessFoods.getImagebyFood("8"));
        Services.closeDataAccess();
    }

    public void testAddFoodImage(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        AccessFoods accessFoods = new AccessFoods();
        Food randomFood = new Food(7, "foodname", 5, 10, "Spicy", "Expert", "Asian");
        accessFoods.addFood(randomFood);
        accessFoods.addFoodImage("7", "food7");
        assertEquals("food7", accessFoods.getImagebyFood("7"));
        Services.closeDataAccess();
    }

}
