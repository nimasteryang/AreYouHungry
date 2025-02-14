package comp3350.Group2.areyouhungry.objects;

public class Ingredient{
    private int ingredientID;
    private String ingredientName;
    private String measurement;

    public Ingredient(int ingredientID, String ingredientName, String measurement){
        if(ingredientID >= 0 && !ingredientName.isEmpty() && !measurement.isEmpty()){
            this.ingredientID = ingredientID;
            this.ingredientName = ingredientName;
            this.measurement = measurement;
        }
        else{
            throw new NullPointerException();
        }
    }

    public int getIngredientID(){
        return ingredientID;
    }

    public String getIngredientName(){
        return ingredientName;
    }

    public String getMeasurement(){
        return measurement;
    }

    public void setIngredientName(String ingredientName){
        this.ingredientName = ingredientName;
    }

    public void setMeasurement(String measurement){
        this.measurement = measurement;
    }

    public boolean equals(Object otherObject){
        boolean equal = false;
        if(otherObject instanceof Ingredient){
            Ingredient otherIngredient = (Ingredient)otherObject;
            if(this.ingredientID == otherIngredient.ingredientID){
                equal = true;
            }
        }
        return equal;
    }

    public String toString(){
        return "ID: " + ingredientID + "\n  IngredientName: " + ingredientName + "\n  Measurement: " + measurement + "\n";
    }
}
