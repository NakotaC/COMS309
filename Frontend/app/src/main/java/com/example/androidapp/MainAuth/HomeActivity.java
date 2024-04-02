package com.example.androidapp.MainAuth;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.Game.GameActivity;
import com.example.androidapp.Game.User;
import com.example.androidapp.Leaderboard.LeaderboardActivity;
import com.example.androidapp.Leaderboard.ListAdapterLeaderboard;
import com.example.androidapp.R;
import com.example.androidapp.ShopInventory.ShopActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;


/**
 * The following method is the main method. It is invoked whenever the home screen starts up.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //variable declaration
    private MaterialButton button1;
    private MaterialButton clanButton;
    private MaterialButton shopButton;
    private TextView text1;
    private TextView text2;
    private TextView welcomeSplash;
    private User user;
    private ImageButton statsButton;
    private View hiddenLayout;
    private FloatingActionButton floatingActionButton1;
    private boolean random1 = false;
    private ListView matchHistoryList;
    private MatchHistoryListAdapter adapter1;

    /**
     * The following method is used to initialize all the elements of the screen. In this case, it
     * initializes buttons and text fields.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        welcomeSplash = findViewById(R.id.welcomeSplashTxt);
        floatingActionButton1 = findViewById(R.id.matchHistory);
        hiddenLayout = findViewById(R.id.hiddenLayout);
        text2 = findViewById(R.id.matchHistoryTitle);
        matchHistoryList = findViewById(R.id.matchHistoryList);

        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);
        floatingActionButton1.setOnClickListener(this);
        welcomeSplash.setText("Welcome " + user.getUsername() + "!");

        adapter1 = new MatchHistoryListAdapter(this, new LinkedList<>());
        matchHistoryList.setAdapter(adapter1);
    }

    /**
     * The following method is initialized whenever the user clicks one of the navigation buttons.
     * The user is then redirected to the one of the screens that corresponds to the button clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id1 = v.getId();
        if (id1 == R.id.clanButton) {
            Intent intent = new Intent(HomeActivity.this, ClanActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        } else if (id1 == R.id.button) {
            Intent intent = new Intent(HomeActivity.this, GameActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        } else if (id1 == R.id.shopButton) {
            Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        } else if (id1 == R.id.statsButton) {
            Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }
        else if (id1 == R.id.matchHistory)
        {
        revealHiddenLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private void revealHiddenLayout()
    {
        if (!random1)
        {

            int x = hiddenLayout.getRight() / 2;
            int y = hiddenLayout.getBottom() / 2;

            int startRadius = 0;

            int endRadius = (int) Math.hypot(
                    (double) hiddenLayout.getWidth(),
                    (double) hiddenLayout.getHeight()
            );

            floatingActionButton1.setBackgroundTintList(ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                            getResources(),
                            R.color.white,
                            null
                    )
            ));

            floatingActionButton1.setImageResource(R.drawable.baseline_clear_24);

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    hiddenLayout,
                    x,
                    y,
                    (float) startRadius,
                    (float) endRadius
            );

            hiddenLayout.setVisibility(View.VISIBLE);

            anim.start();

            random1 = true;

        }
        else
        {
            int x = hiddenLayout.getRight() / 2;
            int y = hiddenLayout.getBottom() / 2;

            int startRadius = Math.max(hiddenLayout.getWidth(), hiddenLayout.getHeight());

            int endRadius = 0;

     /*       floatingActionButton1.setBackgroundTintList(ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                            getResources(),
                            R.color.black,
                            null
                    )
            )); */

            floatingActionButton1.setImageResource(R.drawable.icon__file_history_line_);

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    hiddenLayout,
                    x,
                    y,
                    (float) startRadius,
                    (float) endRadius
            );

            anim.addListener(new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animator)
                {
                }

                @Override
                public void onAnimationEnd(Animator animator)
                {
                    hiddenLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator)
                {
                }

                @Override
                public void onAnimationRepeat(Animator animator)
                {
                }
            });

            anim.start();

            random1 = false;
        }
    }
}

