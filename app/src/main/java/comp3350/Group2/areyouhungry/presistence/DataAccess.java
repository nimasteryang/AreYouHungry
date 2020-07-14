package comp3350.Group2.areyouhungry.presistence;

import java.util.List;
import java.util.Map;

import comp3350.Group2.areyouhungry.objects.Food;

public interface DataAccess {
    void open(String string);

    void close();

    public Map getFoodMap(Map ret_food_map);

    public String getFoodSequential(List<Food> foodResult);

    public String getFavouriteFoodSequential(List<Food> foodResult);

    public String getFoodRandom(List<Food> foodResult);

    public String getFoodPreferred(List<Food> foodResult, String food);

    public String addFood(Food newFood);

    public Food getFoodFromID(String foodID);

    public String setFoodToFavourite(String curr_id, boolean favourite);
}
