package com.liutianjiao.smartvideosurveillance.data;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Created by Lenovo on 2015/4/14.
 */
public class LruImageCache implements ImageCache {

    private static LruCache<String, Bitmap> mMemoryCache;
    private static LruImageCache lruImageCache;

    private LruImageCache(){
        // Get the Max available memory
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    public static LruImageCache instance(){
        if(lruImageCache == null){
            lruImageCache = new LruImageCache();
        }
        return lruImageCache;
    }
    @Override
    public Bitmap getBitmap(String s) {
        return mMemoryCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        if(getBitmap(s) == null){
            mMemoryCache.put(s, bitmap);
        }
    }
}
