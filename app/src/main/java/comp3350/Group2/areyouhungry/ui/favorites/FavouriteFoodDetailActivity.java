package comp3350.Group2.areyouhungry.ui.favorites;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;

import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.R;
import comp3350.Group2.areyouhungry.business.AccessFoods;
import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.ui.all_food.FoodDetailFragment;
import comp3350.Group2.areyouhungry.ui.home.HomeActivity;
import comp3350.Group2.areyouhungry.ui.more.MoreActivity;

/**
 * An activity representing a single FavouriteFood detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FavouriteFoodListActivity}.
 */
public class FavouriteFoodDetailActivity extends AppCompatActivity {

    private String curr_id = null;
    private AccessFoods accessFoods;
    private ArrayList<Food> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favouritefood_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //xu yang: get current food id
        curr_id = getIntent().getStringExtra(FavouriteFoodDetailFragment.ARG_ITEM_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_id != null){
                    accessFoods = new AccessFoods();
                    foodList = new ArrayList<Food>();
                    accessFoods.getFoods(foodList);
                    Iterator<Food> foodIterator = foodList.iterator();
                    Food getFood;
                    while(foodIterator.hasNext()){
                        getFood = foodIterator.next();
                        if(getFood.foodID.equals(curr_id)){
                            System.out.println("get the food by id");
                            if(getFood.favourite){
                                getFood.setFavourite(false);
                                Snackbar.make(view, "You unlike this food!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else{
                                getFood.setFavourite(true);
                                Snackbar.make(view, "You like this food!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }
                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FavouriteFoodDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FavouriteFoodDetailFragment.ARG_ITEM_ID));
            FavouriteFoodDetailFragment fragment = new FavouriteFoodDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.favouritefood_detail_container, fragment)
                    .commit();
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent home_intent = new Intent(FavouriteFoodDetailActivity.this, HomeActivity.class);
                        startActivity(home_intent);
                        break;

                    case R.id.navigation_favorites:
                        Intent favor_intent = new Intent(FavouriteFoodDetailActivity.this, FavouriteFoodListActivity.class);
                        startActivity(favor_intent);
                        break;

                    case R.id.navigation_more:
                        Intent more_intent = new Intent(FavouriteFoodDetailActivity.this, MoreActivity.class);
                        startActivity(more_intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, FavouriteFoodListActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}