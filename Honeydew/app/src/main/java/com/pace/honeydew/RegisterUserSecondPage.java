package com.pace.honeydew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegisterUserSecondPage extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    //These switches relate to the Categories made in on Create
    private Switch healthCategory;
    private Switch animalCategory;
    private Switch smallSizeCategory;

    private ImageView banner;

    private Button registerUserSecondStep;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_second_page);

        mAuth = FirebaseAuth.getInstance();

        banner = (ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        //Categories For each category wanting to be added follow the samples below in formatting
        healthCategory = (Switch) findViewById(R.id.health);
        healthCategory.setOnClickListener(this);

        animalCategory = (Switch) findViewById(R.id.animal);
        animalCategory.setOnClickListener(this);

        smallSizeCategory = (Switch) findViewById(R.id.smallSized);
        smallSizeCategory.setOnClickListener(this);

        //Register User
        registerUserSecondStep = (Button) findViewById(R.id.registerUserSecondStep);
        registerUserSecondStep.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        // initiate a Switch
        Switch Case1 = (Switch) findViewById(R.id.health);
        //set the current state of a Switch
        Case1.setChecked(true);

        // initiate a Switch
        Switch Case2 = (Switch) findViewById(R.id.animal);
        //set the current state of a Switch
        Case2.setChecked(true);

        Switch Case3 = (Switch) findViewById(R.id.smallSized);
        Case3.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.registerUserSecondStep:
                Test1();
                break;
        }
    }

    public void Test1(){

        Switch healthCategory = (Switch) findViewById(R.id.health);
        Switch animalCategory = (Switch) findViewById(R.id.animal);
        Switch smallSizeCategory = (Switch) findViewById(R.id.smallSized);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = database.getReference("Users");
        userID = user.getUid();

        boolean health = false;
        boolean animals = false;
        boolean smallSize = false;

        // Two lines below get database, find and or create a message class if there is none, and then input data based on if statment
        // FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DatabaseReference myRef = database.getReference("message");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        //DatabaseReference myRef2 = database.getReference("message/Information");
        //DatabaseReference myRef3 = database.getReference("message/Details");

        if (healthCategory.isChecked()) {

            // (This line creates a message class and inputs the text inside)  myRef.setValue("Test Case");
            //myRef2.setValue("Test Case2");
            //myRef3.setValue("Test Case3");

            //DatabaseReference myRefStep2 = database.getReference("Users/" + userID + "/age");
            //myRefStep2.setValue("ProofOfChange");

            DatabaseReference Ref = database.getReference("Users/" + userID + "/Health");
            Ref.setValue("True");
            health = true;

        } else if(!healthCategory.isChecked()){

            DatabaseReference Ref = database.getReference("Users/" + userID + "/Health");
            Ref.setValue("False");
            health = false;

        }

        if (animalCategory.isChecked()){

            DatabaseReference Ref = database.getReference("Users/" + userID + "/Animals");
            Ref.setValue("True");
            animals = true;

        }

        else if (!animalCategory.isChecked()){

            DatabaseReference Ref = database.getReference("Users/" + userID + "/Animals");
            Ref.setValue("False");
            animals = false;
        }

        if (smallSizeCategory.isChecked()){

            DatabaseReference Ref = database.getReference("Users/" + userID + "/SmallSizedCharities");
            Ref.setValue("True");
            smallSize = true;

        }

        else if (!smallSizeCategory.isChecked()){

            DatabaseReference Ref = database.getReference("Users/" + userID + "/SmallSizedCharities");
            Ref.setValue("False");
            smallSize = false;

        }
        Favorites favorite  = new Favorites(health, animals, smallSize);
        startActivity(new Intent(RegisterUserSecondPage.this, MainActivity.class));

    }

}