package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

public class AssetsGrabber extends Application {
    /* A fake Application class to get a context for the purpose of returning the asset manager. */

    @SuppressLint("StaticFieldLeak") // Leak will only occur if accessing UI elements
    private static Context mContext;

    @Override
    public void onCreate() {
        /* Set the context to a static attribute. */
        super.onCreate();
        mContext = this;
    } // onCreate()

    public static AssetManager getAssetManager(){
        /* Returns the AssetManager */
        return mContext.getAssets();
    } // getAssetManager()
} // AssetsGrabber