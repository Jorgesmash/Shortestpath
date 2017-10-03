package com.shortestpath.app.utils;

import android.text.Editable;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.widget.EditText;


/**
 * Called every time a new element is entered in mazeEditText by the user.
 *
 * Each time the string changes, it looks for commas (','), negative and positive signs ('-', '+') and
 * empty characters ('') in order to determine if the string should continue adding numbers in
 * the same row or should it do it in the next one.
 *
 * Each time a comma is typed, a space character (' ') is appended in the elements sequence, or
 * a new line character is appended when the maximum number of elements for a row has been reached.
 *
 * When a minus sign is typed, the number to be typed after the sign will be negative.
 *
 * Once the total number of rows is reached, the maze is ready to calculate its shortest path.
 * */
public class ElementsSequenceMaker {

    // Maze size variables
    private int rowSize = 0;
    private int columnSize = 0;

    // The sequence of elements to be inserted in the maze
    private Editable elementsSequence;

    // Negative sign control variable
    private boolean negative = false;

    // Rows and Columns Control variables
    private int currentColumn = 0;
    private int currentRow = 0;

    /**
     *  Listener which informs that there has been changes in the sequence of elements.
     *  */
    public interface OnSequencePositionChangedListener {
        void onSequencePositionChanged(int newRow, int newColumn);
    }

    private OnSequencePositionChangedListener onSequencePositionChangedListener;

    public void setOnSequencePositionChangedListener(OnSequencePositionChangedListener onSequencePositionChangedListener) {
        this.onSequencePositionChangedListener = onSequencePositionChangedListener;
    }

    /** Constructor */
    public ElementsSequenceMaker(EditText editText, int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;

        this.elementsSequence = editText.getText();
    }

    /**
     * Check each typed character by the user and makes some treatment depending of the character
     * by converting the entered sequence string in an output sequence which is used to give a
     * look aesthetically good to the maze shown in a EditText
     * */
    public boolean consumeKeyCode(int keyCode) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL:
            {
                if (elementsSequence.length() > 0) {

                    char lastChar = elementsSequence.charAt(elementsSequence.length() - 1);
                    if (lastChar == ' ') {
                        elementsSequence.delete(elementsSequence.toString().lastIndexOf(","), elementsSequence.length());
                        currentColumn--;

                        // Set the sign of the current number
                        int lastNumber = Integer.parseInt(getLastTypedElement());
                        negative = lastNumber < 0;

                        // Call the listener to let know that elements sequence has changed
                        if (onSequencePositionChangedListener != null) {
                            onSequencePositionChangedListener.onSequencePositionChanged(currentRow, currentColumn);
                        }

                        // The KeyEvent is consumed
                        return true;

                    } else if (lastChar == '\n') {
                        elementsSequence.delete(elementsSequence.toString().lastIndexOf("\n"), elementsSequence.length());
                        currentRow--;
                        currentColumn = columnSize - 1;

                        // Set the sign of the current number
                        int lastNumber = Integer.parseInt(getLastTypedElement());
                        negative = lastNumber < 0;

                        // Call the listener to let know that elements sequence has changed
                        if (onSequencePositionChangedListener != null) {
                            onSequencePositionChangedListener.onSequencePositionChanged(currentRow, currentColumn);
                        }

                        // The KeyEvent is consumed
                        return true;

                    } else if (lastChar == '-') {

                        negative = false;

                        // The KeyEvent is not consumed
                        return false;
                    }
                }

                return false;
            }
            case KeyEvent.KEYCODE_MINUS: // If the typed character is a minus sign
            {
                // Invert the sign of the number being currently typed
                negative = !negative;

                // Calculate the beginning of the number being currently typed
                String numberString = elementsSequence.toString().substring(elementsSequence.toString().lastIndexOf("\n") + 1); // Find the beginning of the last row
                numberString = numberString.substring(numberString.lastIndexOf(" ") + 1); // In the last row, find the last space character

                if (negative) {
                    // If the negative flag is positive, make the number negative
                    numberString = "-" + numberString;
                } else {
                    // Else, remove the negative sign of the number to make it positive
                    numberString = numberString.substring(numberString.lastIndexOf(" ") == -1 ? 1 : numberString.lastIndexOf(" ") + 2);
                }

                // Remove the number and right away add it with its new sign
                if (elementsSequence.toString().lastIndexOf(" ") > elementsSequence.toString().lastIndexOf("\n")) {
                    elementsSequence.delete(elementsSequence.toString().lastIndexOf(" ") + 1, elementsSequence.length());
                } else {
                    elementsSequence.delete(elementsSequence.toString().lastIndexOf("\n") + 1, elementsSequence.length());
                }

                // Update elementsSequence and fire the listener
                elementsSequence.append(numberString);

                // The KeyEvent is consumed
                return true;
            }

            case KeyEvent.KEYCODE_COMMA: // If the typed character is a comma
            {
                currentColumn++;
                if (currentColumn < columnSize) { // Checks if the new number is still part of the current row

                    // Discard that the last entered element is a control character
                    boolean elementAdded = isNotControlCharacter();
                    if (elementAdded) { // If the number was successfully added in mazeEditText

                        // Reset negative sign control variable to false
                        negative = false;

                        // Add a space after the comma in the elements sequence
                        elementsSequence.append(", ");

                        // Call the listener to let know that elements sequence has changed
                        if (onSequencePositionChangedListener != null) {
                            onSequencePositionChangedListener.onSequencePositionChanged(currentRow, currentColumn);
                        }
                    }

                } else { // Else, the new element is part of the next row

                    // Discard that the last entered element is a control character
                    boolean elementAdded = isNotControlCharacter();
                    if (elementAdded) { // If the element was successfully added in mazeEditText

                        // Reset negative sign control variable to false
                        negative = false;

                        currentRow++;
                        if (currentRow < rowSize) { // Checks if it's still possible to add more rows

                            // Add an enter at the end of the elements sequence
                            elementsSequence.append("\n");

                            // Reset variables for row control
                            currentColumn = 0;

                        }

                        // Update the current insertion position
                        if (onSequencePositionChangedListener != null) {
                            onSequencePositionChangedListener.onSequencePositionChanged(currentRow, currentColumn);
                        }
                    }
                }

                // The KeyEvent is consumed
                return true;
            }
        }

        // The KeyEvent is not consumed
        return false;
    }

    /**
     * Validates the last typed element in the elements sequence.
     *
     * The element is invalid if it is any of the control characters like: comma (,), a minus (-) or an empty char().
     *
     * If the element is invalid, it is ignored, so it is necessary to discount one comma in
     * order to release the position and allow a next element to use it.
     * */
    private boolean isNotControlCharacter() {

        String elementString = getLastTypedElement();
        if (elementString.equals(",") || elementString.equals("-") || elementString.equals("+") || elementString.equals("")) {
            currentColumn--;
            return false;
        }

        return true;
    }

    /**
     * Returns the last typed number by the user.
     *
     * The location of the number in the elements sequence is defined by the last position of a
     * ' ' or a '\n' character, until the end of the string.
     * */
    private String getLastTypedElement() {

        String lastNumber;

        if (elementsSequence.toString().lastIndexOf(' ') > elementsSequence.toString().lastIndexOf('\n')) {
            lastNumber = elementsSequence.toString().substring(elementsSequence.toString().lastIndexOf(' ') + 1);

        } else {
            lastNumber = elementsSequence.toString().substring(elementsSequence.toString().lastIndexOf('\n') + 1);
        }

        return lastNumber;
    }
}
