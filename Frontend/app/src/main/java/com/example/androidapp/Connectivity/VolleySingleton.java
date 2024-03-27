package com.example.androidapp.Connectivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Class that handles Volley Requests
 */
public class VolleySingleton {
    /**
     * var for the current instance of the VolleySingleton
     */
    private static VolleySingleton instance;
    /**
     * var for the request queue
     */
    private RequestQueue requestQueue;
    /**
     * var for the Imageloader
     */
    private final ImageLoader imageLoader;
    /**
     * var to hold the Context of the request
     */
    private static Context ctx;


    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Gets the current instance of the Singleton
     * @param context the context of the volley request
     * @return returns the instance of the VolleySingleton
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    /**
     * Gets the current request queue
     * @return returns the current request queue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds a request to the request queue
     * @param req this is the request that you'd like to add
     * @param <T> this is the request type you're using
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * gets the Image Loader
     * @return returns the Image Loader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
