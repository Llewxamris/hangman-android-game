package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

public class AssetsGrabber extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static AssetManager getAssetManager(){
        return mContext.getAssets();
    }
}