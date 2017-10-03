package com.shortestpath.app.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.EditText;

import com.shortestpath.BuildConfig;
import com.shortestpath.R;
import com.shortestpath.app.MainFragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

/**
 * Checks if ElementSequenceMaker is able to convert the user's typed input element sequence
 * into an output sequence which looks aesthetically good in the mazeEditText.
 *
 * ElementsSequenceMaker.consumeKeyCode
 * */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ElementsSequenceMakerRobolectricTest {

    // The FragmentActivity which holds the view hierarchy
    MainFragmentActivity mainFragmentActivity;

    // The EditText which shows the given maze
    EditText mazeEditText;
    MazeEditTextTextWatcher mazeEditTextTextWatcher;

    @Test
    public void testOnKeyListenerWithExample1() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "8", "6", "4"}
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("3, 4, 1, 2, 8, 6\n6, 1, 8, 2, 7, 4\n5, 9, 3, 9, 9, 5\n8, 4, 1, 3, 2, 6\n3, 7, 2, 8, 6, 4", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample2() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "1", "2", "3"}
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("3, 4, 1, 2, 8, 6\n6, 1, 8, 2, 7, 4\n5, 9, 3, 9, 9, 5\n8, 4, 1, 3, 2, 6\n3, 7, 2, 1, 2, 3", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample3() throws Exception {

        String[][] mazeArray = new String[][] {
                {"19", "10", "19", "10", "19"},
                {"21", "23", "20", "19", "12"},
                {"20", "12", "20", "11", "10"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("19, 10, 19, 10, 19\n21, 23, 20, 19, 12\n20, 12, 20, 11, 10", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample4() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "8", "5", "3", "5"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("5, 8, 5, 3, 5", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample5() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5"},
                {"8"},
                {"5"},
                {"3"},
                {"5"}
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("5\n8\n5\n3\n5", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample6() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "5", "H"},
                {"8", "M", "7"},
                {"5", "7", "5"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("5, 5, H\n8, M, 7\n5, 7, 5", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample7() throws Exception {

        String[][] mazeArray = new String[][] {};

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample8() throws Exception {

        String[][] mazeArray = new String[][] {
                {"69", "10", "19", "10", "19"},
                {"51", "23", "20", "19", "12"},
                {"60", "12", "20", "11", "10"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("69, 10, 19, 10, 19\n51, 23, 20, 19, 12\n60, 12, 20, 11, 10", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample9() throws Exception {

        String[][] mazeArray = new String[][] {
                {"60", "3", "3", "6"},
                {"6", "3", "7", "9"},
                {"5", "6", "8", "3"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("60, 3, 3, 6\n6, 3, 7, 9\n5, 6, 8, 3", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample10() throws Exception {

        String[][] mazeArray = new String[][] {
                {"6", "3", "-5", "9"},
                {"-5", "2", "4", "10"},
                {"3", "-2", "6", "10"},
                {"6", "-1", "-2", "10"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("6, 3, -5, 9\n-5, 2, 4, 10\n3, -2, 6, 10\n6, -1, -2, 10", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample11() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51"},
                {"0", "51"},
                {"51", "51"},
                {"5", "5"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("51, 51\n0, 51\n51, 51\n5, 5", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample12() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51", "51"},
                {"0", "51", "51"},
                {"51", "51", "51"},
                {"5", "5", "51"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("51, 51, 51\n0, 51, 51\n51, 51, 51\n5, 5, 51", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample13() throws Exception {

        String[][] mazeArray = new String[][] {
                {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testConsumeKeyCode(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1\n2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2", outputString);
    }

    @Before
    public void setUp() throws Exception {
        mainFragmentActivity = Robolectric.setupActivity(MainFragmentActivity.class);
        mazeEditText = mainFragmentActivity.findViewById(R.id.mazeEditText);
        mazeEditTextTextWatcher = new MazeEditTextTextWatcher();
    }

    /**
     * Performs the instrumented test with a given maze to test the associated View.OnKeyListener
     * to mazeEditText.
     * */
    private String testConsumeKeyCode(String inputString, int rowSize, int columnSize) {

        // Set the maze rows and columns size
        mainFragmentActivity.setMazeSize(rowSize, columnSize);

        // Add mazeEditTextTextWatcher mazeEditText
        mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

        // Perform the typing of the inputString in mazeEditText
        mazeEditText.append(inputString);

        // Remove mazeEditTextTextWatcher mazeEditText to avoid multiple registries for each test case
        mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);

        // Get the processed outputString from mazeEditText
        return mazeEditText.getText().toString();
    }

    /**
     * Converts the input typed string of elements in KeyCodes which are sent to
     * ElementsSequenceMaker.consumeKeyCode() in order to simulate the user's keyboard typing.
     *
     * The text input text will be converted in an out sequence which looks
     * aesthetically good in the mazeEditText.
     * */
    private class MazeEditTextTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);

            String inputString = charSequence.toString();
            CharSequence cs = mazeEditText.getText();
            mazeEditText.getText().delete(0, mazeEditText.length());

            KeyCharacterMap keyCharacterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
            for (int i = 0; i < inputString.length(); i++) {

                // Get the KeyCode of the character
                char character = inputString.charAt(i);

                KeyEvent keyEvent = null;
                if (Character.isLetterOrDigit(character)) {
                    KeyEvent[] keyEvents = keyCharacterMap.getEvents(new char[]{character});
                    keyEvent = keyEvents[0];
                } else if (character == ',') {
                    keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_COMMA);
                } else if (character == '-') {
                    keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MINUS);
                }

                boolean consumed = mainFragmentActivity.getElementsSequenceMaker().consumeKeyCode(keyEvent.getKeyCode());
                if (!consumed) {
                    mazeEditText.append("" + character);
                }
            }
            mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    }

    /**
     * Creates a string from the input maze that can be interpreted by the MazeParser.
     */
    private String getTypedString(String[][] maze) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {

                String elementString = maze[i][j];

                stringBuilder.append(elementString);
                stringBuilder.append(",");
            }
        }

        return stringBuilder.toString();
    }

}
