package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class Word implements Parcelable {

    private String word;
    private int length;

    Word(String word) {
        this.word = word;
        length = word.length();
    }

    private Word(Parcel in) {
        word = in.readString();
        length = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(word);
        out.writeInt(length);
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

}
