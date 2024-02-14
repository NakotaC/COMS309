package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ImageReqActivity extends AppCompatActivity {

    private Button btnImageReq;
    private ImageView imageView;

    private Button menu_button;

    public static final String URL_IMAGE = "https://m.media-amazon.com/images/I/81CA3GV9sXL.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_req);

        btnImageReq = (Button) findViewById(R.id.btnImageReq);
        imageView = (ImageView) findViewById(R.id.imgView);
        menu_button = findViewById(R.id.menu_button);

        btnImageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {makeImageRequest();}
        });
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(ImageReqActivity.this, MainActivity.class));
            }
        });
    }

    /**
     * Making image request
     * */
    private void makeImageRequest() {

        ImageRequest imageRequest = new ImageRequest(
            URL_IMAGE,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // Display the image in the ImageView
                    imageView.setImageBitmap(response);
                }
            },
            0, // Width, set to 0 to get the original width
            0, // Height, set to 0 to get the original height
            ImageView.ScaleType.FIT_XY, // ScaleType
            Bitmap.Config.RGB_565, // Bitmap config

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle errors here
                    Log.e("Volley Error", error.toString());
                }
            }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
    }

}