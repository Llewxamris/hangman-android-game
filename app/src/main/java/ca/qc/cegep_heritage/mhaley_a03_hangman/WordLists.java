package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class WordLists {
    private static String[] easyList = null;

    private static String[] hardList = null;

    private static String[] list;

    static String[] getEasyList() throws IOException {

        if (easyList == null) {
            AssetManager assetManager = AssetsGrabber.getAssetManager();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(assetManager.open("hangwords_easy.txt")));

            String line;
            StringBuilder fullList = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                fullList.append(line);
                fullList.append(",");
            }

            easyList = fullList.toString().split(",");
        }

        return easyList;
    }

    static String[] getHardList() throws IOException {
        if (hardList == null) {
            AssetManager assetManager = AssetsGrabber.getAssetManager();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(assetManager.open("hangwords_hard.txt")));

            String line;
            StringBuilder fullList = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                fullList.append(line);
                fullList.append(",");
            }

            hardList = fullList.toString().split(",");
        }

        return hardList;
    }

    private WordLists() {
    }
}
