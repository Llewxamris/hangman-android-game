package ca.qc.cegep_heritage.mhaley_a03_hangman;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

class WordFactory {

    static Word getWord(int minLength, int maxLength, int difficulty) throws IOException {
        String[] wordList;
        LinkedList<String> acceptableWords;

        if (difficulty == 0) {
            wordList = WordLists.getEasyList();
        } else {
            wordList = WordLists.getHardList();
        }

        acceptableWords = new LinkedList<>();

        for (String word : wordList) {
            if (word.length() >= minLength && word.length() <= maxLength) {
                acceptableWords.add(word);
            }
        }

        String randomWord = acceptableWords.get(new Random().nextInt(acceptableWords.size()));

        return new Word(randomWord);
    }

}
