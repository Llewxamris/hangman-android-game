package ca.qc.cegep_heritage.mhaley_a03_hangman;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

class WordFactory {
    /* Creates and returns a Word class based off the parameters. The word in the Word class is
    taken randomly from one of the WordLists singleton.*/

    static Word getWord(int minLength, int maxLength, int difficulty) throws IOException {
        String[] wordList;
        LinkedList<String> acceptableWords;

        // Get WordList based on difficulty
        if (difficulty == 0) {
            wordList = WordLists.getEasyList();
        } else {
            wordList = WordLists.getHardList();
        }

        acceptableWords = new LinkedList<>();

        // Find words that fit within the parameters
        for (String word : wordList) {
            if (word.length() >= minLength && word.length() <= maxLength) {
                acceptableWords.add(word);
            }
        }

        // Get a random word from the list of acceptable words
        String randomWord = acceptableWords.get(new Random().nextInt(acceptableWords.size()));

        return new Word(randomWord);
    } // getWord(...)
} // WordFactory
