package com.example.androidapp.MainAuth;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.Game.GameActivity;
import com.example.androidapp.Game.User;
import com.example.androidapp.Leaderboard.LeaderboardActivity;
import com.example.androidapp.R;
import com.example.androidapp.ShopInventory.ShopActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


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
    private TextView welcomeText;

    private TextView dailyQuests;
    private TextView text3;
    private TextView text4;
    private TextView dailyQuestTask;
    private TextView globalChat;

    public User user;
    private ImageButton statsButton;
    private View hiddenLayout;
    private FloatingActionButton floatingActionButton1;
    private boolean random1 = false;
    private ListView matchHistoryList;
    private MatchHistoryListAdapter adapter1;
    private JSONArray quests;
    private JSONArray equippedItems;
    private static final String URL1 =
            "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/matches";
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

        assert user != null;
        equippedItems = user.getEquippedItems();

       // quests = user.getQuests();

        // imageButton1 = findViewById(R.id.imageButton1);
        welcomeText = findViewById(R.id.home_welcome_text);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        clanButton = findViewById(R.id.clanButton);
        shopButton = findViewById(R.id.shopButton);
        statsButton = findViewById(R.id.statsButton);
        dailyQuests = findViewById(R.id.dailyQuests);
        globalChat = findViewById(R.id.globalChat);
        floatingActionButton1 = findViewById(R.id.matchHistory);
        hiddenLayout = findViewById(R.id.hiddenLayout);
        text2 = findViewById(R.id.matchHistoryTitle);
        matchHistoryList = findViewById(R.id.matchHistoryList);

        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);
        floatingActionButton1.setOnClickListener(this);

        adapter1 = new MatchHistoryListAdapter(this, new LinkedList<>());
        matchHistoryList.setAdapter(adapter1);

        welcomeText.setText("Welcome " + user.getUsername());
        welcomeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String item0;
        try {
        JSONObject temp = user.getEquippedItems().getJSONObject(0);
            item0 = temp.getString("itemName");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (item0.equals("Blue")){
        welcomeText.setTextColor(ColorStateList.valueOf(Color.BLUE));

        } else if (item0.equals("Red")){
            welcomeText.setTextColor(ColorStateList.valueOf(Color.RED));

        }else if (item0.equals("Yellow")){
            welcomeText.setTextColor(ColorStateList.valueOf(Color.YELLOW));

        }

        /* assert user != null;
        welcomeText.setText("Welcome " + user.getUsername());
        welcomeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); */

        Dialog dialog1 = new Dialog(HomeActivity.this);

        String random1 = "Daily Quests?";
        SpannableString spannableString1 = new SpannableString(random1);

        ClickableSpan clickableSpan1 = new ClickableSpan()
        {
            @Override
            public void onClick(@NonNull View widget)
            {
            dialog1.setContentView(R.layout.activity_dialogbox);
            dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog1.setCancelable(false);

            dailyQuestTask = dialog1.findViewById(R.id.dailyQuestTask);
            text3 = dialog1.findViewById(R.id.confirm);
            text4 = dialog1.findViewById(R.id.goback);

            String dailyQuestTaskText = "Daily Quest: win " + LoginActivity.questNumber + " games.";
            dailyQuestTask.setText(dailyQuestTaskText);
            text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            dialog1.dismiss();
            }
            });

            text4.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
            {
            dialog1.dismiss();
            }
            });

            dialog1.show();
            }
        };
        spannableString1.setSpan(clickableSpan1, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dailyQuests.setText(spannableString1);
        dailyQuests.setMovementMethod(LinkMovementMethod.getInstance());

        String random2 = "or Chat?";
        SpannableString spannableString2 = new SpannableString(random2);

        ClickableSpan clickableSpan2 = new ClickableSpan()
        {
            @Override
            public void onClick(@NonNull View widget)
            {
                Intent intent = new Intent(HomeActivity.this,
                        GlobalChatActivity.class);
                intent.putExtra("USEROBJ", user);
                startActivity(intent);
            }
        };
        spannableString2.setSpan(clickableSpan2, 3, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        globalChat.setText(spannableString2);
        globalChat.setMovementMethod(LinkMovementMethod.getInstance());
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
        parseJsonArrayReq();
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

    private void parseJsonArrayReq()
    {
        LinkedList<String> winner = new LinkedList();
        LinkedList<String> time = new LinkedList();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/history/" + user.getId() + "/",
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        System.out.println(response);

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                                winner.add(i, jsonObject.getString("winnerId"));
                                time.add(i, jsonObject.getString("itemName"));

                                MatchItemObject item = new MatchItemObject(winner.get(i),
                                        time.get(i));
                                adapter1.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }
        };


        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }


}

