package ca.qc.cegep_heritage.mhaley_a03_hangman;

public class Word {

    private String word;
    private int length;

    public Word(String word) {
        this.word = word;
        length = word.length();
    }

    public String getWord() {
        return word;
    }

    public int getLength() {
        return length;
    }

    private boolean isLetterInWord(CharSequence letter) {
        return word.contains(letter);
    }
}
