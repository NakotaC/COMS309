package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.Game.GameActivity;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;
import com.example.androidapp.ShopInventory.ShopActivity;
import com.example.androidapp.Leaderboard.LeaderboardActivity;
import com.google.android.material.button.MaterialButton;


/**
 * The following method is the main method. It is invoked whenever the home screen starts up.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{

    //variable declaration
    private MaterialButton button1;
    private MaterialButton clanButton;
    private MaterialButton shopButton;
    private TextView text1;
    private User user;
    private ImageButton statsButton;

    /**
     * The following method is used to initialize all the elements of the screen. In this case, it
     * initializes buttons and text fields.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");

       // imageButton1 = findViewById(R.id.imageButton1);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        clanButton = findViewById(R.id.clanButton);
        shopButton = findViewById(R.id.shopButton);
        statsButton = findViewById(R.id.statsButton);


        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);


    }

    /**
     * The following method is initialized whenever the user clicks one of the navigation buttons.
     * The user is then redirected to the one of the screens that corresponds to the button clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.clanButton) {
            Intent intent = new Intent(HomeActivity.this, ClanActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }  else if (id1 == R.id.button) {
            Intent intent = new Intent(HomeActivity.this, GameActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }
        else if (id1 == R.id.shopButton) {
            Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
            startActivity(new Intent(HomeActivity.this, ClanActivity.class));
        } else if (id1 == R.id.statsButton) {
            startActivity(new Intent(HomeActivity.this, LeaderboardActivity.class));
        }
       else if (id1 == R.id.shopButton) {
            startActivity(new Intent(HomeActivity.this, ShopActivity.class));
        }
    }
}