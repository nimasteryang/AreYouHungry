package comp3350.Group2.areyouhungry.tests.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.objects.Categorys;
import comp3350.Group2.areyouhungry.objects.FC;
import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.persistence.DataAccess;

import static comp3350.Group2.areyouhungry.MainActivity.dbName;

public class DataAccessStub implements DataAccess{

    private String dbName;
    private String dbType = "stub";

    private ArrayList<Food> foods;
    private ArrayList<FC> fcs;
    private ArrayList<Categorys> categorysList;

    private Map<String,Food> Food_map;

    public DataAccessStub(String dbName){
        this.dbName = dbName;
    }

    public DataAccessStub(){
        this(MainActivity.dbName);
    }


    public void open(String dbPath){
        Food food;
        Categorys categorys;
        FC fc;

        foods = new ArrayList<Food>();
        Food_map = new HashMap<>();
        food = new Food(1,"Fish","https://www.food.com/recipe/beer-battered-fish-130102");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);
        food = new Food(2,"Burger","https://www.food.com/recipe/the-perfect-burger-92021");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);
        food = new Food(3,"Pie","https://www.tasteofhome.com/recipes/apple-pie/");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);
        food = new Food(4,"Cake","https://addapinch.com/the-best-chocolate-cake-recipe-ever/");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);
        food = new Food(5,"Fries","https://www.spendwithpennies.com/crispy-oven-fries/");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);
        food = new Food(6,"veggies","https://www.cookingclassy.com/greek-salad/");
        foods.add(food);
        Food_map.put(String.valueOf(food.getFoodID()),food);

        categorysList = new ArrayList<Categorys>();
        categorys = new Categorys(1,"Meat");
        categorysList.add(categorys);
        categorys = new Categorys(2,"Grain");
        categorysList.add(categorys);
        categorys = new Categorys(3,"Vegetable");
        categorysList.add(categorys);

        fcs = new ArrayList<FC>();
        fc = new FC(1,1);
        fcs.add(fc);
        fc = new FC(1,2);
        fcs.add(fc);
        fc = new FC(2,1);
        fcs.add(fc);
        fc = new FC(2,3);
        fcs.add(fc);
        fc = new FC(3,1);
        fcs.add(fc);
        fc = new FC(3,3);
        fcs.add(fc);

        System.out.println("Opened " +dbType +" database " +dbName);
    }


    public void close(){
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    /* Add on function to get the food map for FoodDetail Activity. */
    public Map getFoodMap(Map ret_food_map){
        ret_food_map.putAll(Food_map);
        return null;
    }

    /* This function is from sample project, used to add food to a list that contain
       all foods in the stub database. */
    public String getFoodSequential(List<Food> foodResult){
        foodResult.addAll((foods));
        return null;
    }

    /* Generate the favourite list. */
    public String getFavouriteFoodSequential(List<Food> foodResult){
        Iterator<Food> foodIterator = foods.iterator(); /* This iterates through the foods list. */
        Food food;
        while(foodIterator.hasNext()){
            food = foodIterator.next();
            if(food.getFavourite()){
                foodResult.add(food);
            }
        }
        return null;
    }

    /* This function is used to generate a random food from the stub database
       and add it into the foodresult list. */
    public String getFoodRandom(List<Food> foodResult){
        Random random = new Random();
        foodResult.add(foods.get(random.nextInt(foods.size())));
        return null;
    }

    public String getFoodPreferred(List<Food> foodResult, String food){
        if(food != null){
            if (food.equals("Meat")){
                foodResult.add(foods.get(1));
                foodResult.add(foods.get(0));
            } else if (food.equals("Dessert")){
                foodResult.add(foods.get(2));
                foodResult.add(foods.get(3));
            }
        }
        return null;
    }

    @Override
    public Food getFoodFromID(String foodID){
        Iterator<Food> foodIterator = foods.iterator(); /* This iterates through the foods list. */
        Food food;
        while(foodIterator.hasNext()){
            food = foodIterator.next();
            if(food.getFoodID() == foodID){
                return food;
            }
        }
        return null;
    }

    @Override
    public String setFoodToFavourite(String curr_id, boolean favourite){
        Iterator<Food> foodIterator = foods.iterator(); /* This iterates through the foods list. */
        Food food;
        while(foodIterator.hasNext()){
            food = foodIterator.next();
            if(food.getFoodID().equals(curr_id)){
                food.setFavourite(favourite);
            }
        }
        return null;
    }

    public String addFood(Food newFood){
        if(newFood != null){
            if(newFood.getFoodID() != "" && newFood.getFoodName() != ""){
                foods.add(newFood);
                Food_map.put(newFood.getFoodID(), newFood);
            }
        }
        return null;
    }

    @Override
    public int getFoodTableRow(){
        return foods.size();
    }

    @Override
    public String addFoodCategory(int foodID, int categoryID){
        return null;
    }

    @Override
    public int getIDByFood(Food food){
        return 0;
    }

    @Override
    public int getCategoryIDbyName(String categoryName){
        return 0;
    }
}
