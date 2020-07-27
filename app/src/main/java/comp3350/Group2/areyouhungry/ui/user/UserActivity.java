package comp3350.Group2.areyouhungry.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import comp3350.Group2.areyouhungry.MainActivity;
import comp3350.Group2.areyouhungry.R;

import comp3350.Group2.areyouhungry.business.AccessUsers;

import comp3350.Group2.areyouhungry.objects.Food;
import comp3350.Group2.areyouhungry.objects.User;

import comp3350.Group2.areyouhungry.ui.favorites.FavouriteFoodListActivity;
import comp3350.Group2.areyouhungry.ui.user.UserActivity;
import comp3350.Group2.areyouhungry.ui.user.UserActivity;
import comp3350.Group2.areyouhungry.ui.home.HomeActivity;
import comp3350.Group2.areyouhungry.ui.more.MoreActivity;

public class UserActivity extends AppCompatActivity {
    private AccessUsers accessUsers;
    private ArrayList<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        /* For button navigation bar. */
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){

                    case R.id.navigation_home:
                        Intent home_intent = new Intent(UserActivity.this, HomeActivity.class);
                        startActivity(home_intent);
                        break;

                    case R.id.navigation_favorites:
                        Intent fav_intent = new Intent(UserActivity.this, FavouriteFoodListActivity.class);
                        startActivity(fav_intent);
                        break;

                    case R.id.navigation_more:
                        Intent more_intent = new Intent(UserActivity.this, MoreActivity.class);
                        startActivity(more_intent);
                        break;
                }
                return false;
            }
        });

        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) findViewById(R.id.all_fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                Intent add_intent = new Intent(UserActivity.this, AddUserActivity.class);
//                UserActivity.this.startActivity(add_intent);
            }
        });
        View recyclerView = findViewById(R.id.user_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView){
        accessUsers = new AccessUsers();
        userList = new ArrayList<User>();
        accessUsers.getUsers(userList);
        UserActivity.SimpleItemRecyclerViewAdapter theAdapter  = new UserActivity.SimpleItemRecyclerViewAdapter(this, userList);
        recyclerView.setAdapter(theAdapter);
    }
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<UserActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final UserActivity mParentActivity;
        private final List<User> mValues;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener(){
            public void onClick(View view){
                final User user = (User) view.getTag();
                System.out.println("click on user");
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to switch to "+user.getUserName()+" ?");
                builder.setIcon(R.drawable.ic_baseline_account_circle_24);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        System.out.println(user.getUserName());
                        changeUser(user.getUserID());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };


        SimpleItemRecyclerViewAdapter(UserActivity parent,
                                      List<User> items
                                      ){
            mValues = items;
            mParentActivity = parent;

        }

        @Override
        public UserActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_content, parent, false);
            return new UserActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final UserActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position){
            holder.mIdView.setText(String.valueOf(mValues.get(position).getUserID()));
            holder.mContentView.setText(mValues.get(position).getUserName());
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount(){
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view){
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    private void changeUser(int userID) {
        accessUsers = new AccessUsers();
        User newUser = accessUsers.getUserByID(userID);
        if (newUser != null){
            MainActivity.currentUser = newUser;
        }
    }
}