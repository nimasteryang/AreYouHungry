package comp3350.Group2.areyouhungry.business;

import java.util.List;

import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.Services;
import comp3350.Group2.areyouhungry.objects.Direction;
import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.persistence.DataAccess;

public class AccessDirections{
    private DataAccess dataAccess;
    private List<Direction> directions;

    public AccessDirections(){
        dataAccess = Services.getDataAccess(MainActivity.dbName);
        directions = null;
    }

    public String getDirection(Food food, List<Direction> directions){
        return dataAccess.getDirectionByFood(food,directions);
    }

    public int getNewDirectionId(){
        return dataAccess.getDirectionRow()+1;
    }
    public String addDirection(Direction newDirection){
        return dataAccess.addNewDirection(newDirection);
    }
    public String addFoodDirection(int foodid,int directionid){
        return dataAccess.addFoodDirection(foodid,directionid);
    }
}
