package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class WordLists {
    /* Singleton class that contains to lazy-loading lists of words. These words are pulled from two
    text files included inside the assets folder. */
    private static String[] easyList = null;
    private static String[] hardList = null;

    private static final String EASY_FILE = "hangwords_easy.txt";
    private static final String HARD_FILE = "hangwords_hard.txt";

    static String[] getEasyList() throws IOException {
        if (easyList == null) {
            AssetManager assetManager = AssetsGrabber.getAssetManager();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(assetManager.open(EASY_FILE)));

            String line;
            StringBuilder fullList = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                fullList.append(line);
                fullList.append(",");
            }

            easyList = fullList.toString().split(",");
        }

        return easyList;
    } // getEasyList()

    static String[] getHardList() throws IOException {
        if (hardList == null) {
            AssetManager assetManager = AssetsGrabber.getAssetManager();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(assetManager.open(HARD_FILE)));

            String line;
            StringBuilder fullList = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                fullList.append(line);
                fullList.append(",");
            }

            hardList = fullList.toString().split(",");
        }

        return hardList;
    } // getHardList()

    private WordLists() { }
} // WordLists
