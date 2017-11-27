package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class Word implements Parcelable {
    /* Represents a single word. Contains the word itself, and the length of that word. Implements
    parcelable so it can be saved during orientation changes. */

    private String word;
    private int length;

    Word(String word) {
        this.word = word;
        length = word.length();
    } // Word(...)

    private Word(Parcel in) {
        /* For Parcelable */
        word = in.readString();
        length = in.readInt();
    } // Word(...)

    String getWord() {
        return word;
    } // getWord()

    int getLength() {
        return length;
    } // getLength()

    Integer[] getLocationsOfLetter(char letter) {
        /* Finds all the indexes of the char *letter* inside the word, then returns them as an array
        of Integers. */
        LinkedList<Integer> ll = findIndexesOf(letter);
        Integer[] locationsOfLetters = new Integer[ll.size()];
        ll.toArray(locationsOfLetters); // Places values of *ll* into *locationsOfLetters*
        return locationsOfLetters;
    } // getLocationsOfLetter(...)

    boolean isLetterInWord(CharSequence letter) {
        return word.contains(letter);
    } // isLetterInWord(...)

    private LinkedList<Integer> findIndexesOf(char letter) {
        /* Takes in a char, finds the first index of that char inside the word, then determines if
        there is a chance of the char appearing again. If there is, it is passed to the other method
        *findIndexesOf*. */
        LinkedList<Integer> indexes = new LinkedList<>();
        int index = word.indexOf(letter);
        indexes.add(index);

        // Check if there could be more instances of char. If not, pass to new method to find more
        if (index != word.length() - 1) {
            return findIndexesOf(letter, indexes, word.substring(++index), index);
        }

        return indexes;
    } // findIndexesOf(...)

    private LinkedList<Integer> findIndexesOf(char letter, LinkedList<Integer> indexes,
                                              String wordSegment, int lastIndex) {
        /* Takes in a char, the current list of indexes, a segmented part of the word, and the
        previous index, and returns all other locations of the letter inside the word segment.*/

        // Check if Letter is NOT contained inside the word segment
        if (!wordSegment.contains(String.valueOf(letter))) {
            return indexes;
        }

        // Get the next index of char
        int index = wordSegment.indexOf(letter);
        indexes.add(lastIndex + index);

        // Check for more indexes
        return findIndexesOf(letter, indexes, wordSegment.substring(++index), lastIndex + index);
    } // findIndexesOf(...)

    @Override
    public int describeContents() {
        return 0;
    } // Must be defined for Parcelable

    @Override
    public void writeToParcel(Parcel out, int flags) {
        /* Save the data inside the object to a parcel. */
        out.writeString(word);
        out.writeInt(length);
    } // writeToParcel(...)

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        /* Auto-used Parcelable methods for communicating with the Word constructor. */
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

}
