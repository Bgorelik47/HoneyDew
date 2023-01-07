package com.pace.honeydew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button logout;
    private Button deleteAccount;
    private ProgressBar progressBar;
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        logout = (Button) findViewById(R.id.signOut);

        deleteAccount = findViewById(R.id.btnDeleteAccount);
        progressBar = findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        logout.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,MainActivity.class));
            }

        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                deleteUser(userID);
            }
        });

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationVIew);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        //Perform ItemsSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.News:
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        return true;
                }
                return false;
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView ageTextView = (TextView) findViewById(R.id.age);
        final TextView favoritesTextView = (TextView) findViewById(R.id.favorites);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                Favorites favoriteList = snapshot.getValue(Favorites.class);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference myRef = database.getReference("Users");
                userID = user.getUid();

                if (userProfile != null) {
                String fullName = userProfile.fullName;
                String email = userProfile.email;
                String age = userProfile.age;



                //DatabaseReference refHealth = database.getReference("Users/" + userID + "/Health");
                //DatabaseReference refAnimals = database.getReference("Users/" + userID + "/Animals");
                //String Favorites = refHealth + " " + refAnimals;



                greetingTextView.setText("Welcome, " + fullName + "!");
                fullNameTextView.setText(fullName);
                emailTextView.setText(email);
                ageTextView.setText(age);

                }

                Log.i(TAG, "Health: " +  favoriteList.health);
                Log.i(TAG, "Animals: " +  favoriteList.animals);
                Log.i(TAG, "Small Size: " +  favoriteList.smallSize);

                if(favoriteList != null) {

                    boolean health = favoriteList.health;
                    boolean animals = favoriteList.animals;
                    boolean smallSize = favoriteList.smallSize;
                    String tempString = "";

                    if (health = true) {
                        tempString = " |Health| ";
                        //favoritesTextView.setText(tempString);

                    } else {
                        tempString = tempString;
                    }

                    if (animals = true) {
                        tempString = tempString + " |Animals| ";
                        //favoritesTextView.setText(tempString);

                    } else {
                        tempString = tempString;
                    }

                    if (smallSize = true) {
                        tempString = tempString + " |Small Charities| ";


                    } else {
                        tempString = tempString;
                    }

                    favoritesTextView.setText(tempString);

                }

                Log.i(TAG, "Health: " +  favoriteList.health);
                Log.i(TAG, "Animals: " +  favoriteList.animals);
                Log.i(TAG, "Small Size: " +  favoriteList.smallSize);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteUser(String userID) {

        //DatabaseReference drUserTest = FirebaseDatabase.getInstance().getReference("message");
        DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        drUser.removeValue();
        //drUserTest.removeValue();
        Toast.makeText(Profile.this, "User Deleted", Toast.LENGTH_LONG).show();
        startActivity(new Intent(Profile.this,MainActivity.class));
    }

}