package ca.qc.cegep_heritage.mhaley_a03_hangman;

import java.util.LinkedList;

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

    Integer[] getLocationsOfLetter(char letter) {
        LinkedList<Integer> ll = findIndexesOf(letter);
        Integer[] locationsOfLetters = new Integer[ll.size()];
        ll.toArray(locationsOfLetters);
        return locationsOfLetters;
    }

    int getLetterAtIndex(int index) {
        return word.charAt(index);
    }

    boolean isLetterInWord(CharSequence letter) {
        return word.contains(letter);
    }

    private LinkedList<Integer> findIndexesOf(char letter) {
        LinkedList<Integer> indexes = new LinkedList<>();
        int index = word.indexOf(letter);
        indexes.add(index);

        if (index != word.length() - 1) {
            return findIndexesOf(letter, indexes, word.substring(++index), index);
        }

        return indexes;
    }

    private LinkedList<Integer> findIndexesOf(char letter, LinkedList<Integer> indexes, String wordSegment, int lastIndex) {
        if (!wordSegment.contains(String.valueOf(letter))) {
            return indexes;
        }

        int index = wordSegment.indexOf(letter);
        indexes.add(lastIndex + index);

        return findIndexesOf(letter, indexes, wordSegment.substring(++index), lastIndex + index);
    }
}
