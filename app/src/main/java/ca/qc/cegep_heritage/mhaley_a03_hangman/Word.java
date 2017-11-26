package ca.qc.cegep_heritage.mhaley_a03_hangman;

public class Word {

    private String word;
    private int length;

    Word(String word) {
        this.word = word;
        length = word.length();
    }

    public String getWord() {
        return word;
    }

    int getLength() {
        return length;
    }

    boolean isLetterInWord(CharSequence letter) {
        return word.contains(letter);
    }
}
