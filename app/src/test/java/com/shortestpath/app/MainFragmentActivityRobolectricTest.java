package com.shortestpath.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shortestpath.BuildConfig;
import com.shortestpath.R;
import com.shortestpath.app.dialog.InputMazeSizeDialogFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Performs an non instrumented test of the MainFragmentActivity flow to calculate the
 * shortest path of a given maze.
 * */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainFragmentActivityRobolectricTest {

    // The FragmentActivity which holds the view hierarchy
    MainFragmentActivity mainFragmentActivity;

    // The EditText which shows the given maze
    EditText mazeEditText;
    MazeEditTextTextWatcher mazeEditTextTextWatcher;

    @Test
    public void exampleTest1() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "8", "6", "4"}
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest2() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "1", "2", "3"}
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest3() throws Exception {

        String[][] mazeArray = new String[][] {
                {"19", "10", "19", "10", "19"},
                {"21", "23", "20", "19", "12"},
                {"20", "12", "20", "11", "10"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest4() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "8", "5", "3", "5"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest5() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5"},
                {"8"},
                {"5"},
                {"3"},
                {"5"}
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest6() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "5", "H"},
                {"8", "M", "7"},
                {"5", "7", "5"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest7() throws Exception {

        String[][] mazeArray = new String[][] {};

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest8() throws Exception {

        String[][] mazeArray = new String[][] {
                {"69", "10", "19", "10", "19"},
                {"51", "23", "20", "19", "12"},
                {"60", "12", "20", "11", "10"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest9() throws Exception {

        String[][] mazeArray = new String[][] {
                {"60", "3", "3", "6"},
                {"6", "3", "7", "9"},
                {"5", "6", "8", "3"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest10() throws Exception {

        String[][] mazeArray = new String[][] {
                {"6", "3", "-5", "9"},
                {"-5", "2", "4", "10"},
                {"3", "-2", "6", "10"},
                {"6", "-1", "-2", "10"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest11() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51"},
                {"0", "51"},
                {"51", "51"},
                {"5", "5"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest12() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51", "51"},
                {"0", "51", "51"},
                {"51", "51", "51"},
                {"5", "5", "51"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Test
    public void exampleTest13() throws Exception {

        String[][] mazeArray = new String[][] {
                {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2"},
        };

        performWalkthroughTest(mazeArray);
    }

    @Before
    public void setUp() throws Exception {
        mainFragmentActivity = Robolectric.setupActivity(MainFragmentActivity.class);
        mazeEditText = mainFragmentActivity.findViewById(R.id.mazeEditText);
        mazeEditTextTextWatcher = new MazeEditTextTextWatcher();
    }

    /**
     * Performs the instrumented test with a given maze.
     * */
    private void performWalkthroughTest(String[][] mazeArray) {

        // Opens a dialog where the size of the maze is typed
        FloatingActionButton addFloatingButton = mainFragmentActivity.findViewById(R.id.addFloatingButton);
        addFloatingButton.performClick();

        // Check that inputMazeSizeDialogFragment has been opened after clicking addFloatingButton
        InputMazeSizeDialogFragment inputMazeSizeDialogFragment = (InputMazeSizeDialogFragment) mainFragmentActivity.getFragmentManager().findFragmentByTag("inputMazeSizeDialogFragment");
        assertNotNull(inputMazeSizeDialogFragment.getDialog());

        // Insert the rows size of the maze
        EditText rowsEditText = inputMazeSizeDialogFragment.getDialog().findViewById(R.id.rowsEditText);
        rowsEditText.setText(String.format(Locale.US, "%d", mazeArray.length));
        assertEquals("" + mazeArray.length, rowsEditText.getText().toString());

        // Insert the columns size of the maze
        EditText columnsEditText = inputMazeSizeDialogFragment.getDialog().findViewById(R.id.columnsEditText);
        columnsEditText.setText(String.format(Locale.US, "%d", mazeArray.length == 0 ? 0 : mazeArray[0].length));
        assertEquals("" + (mazeArray.length == 0 ? 0 : mazeArray[0].length), columnsEditText.getText().toString());

        // Close the dialog by clicking its "OK" button
        AlertDialog alertDialog = (AlertDialog) inputMazeSizeDialogFragment.getDialog();
        Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.performClick();
        assertNull(inputMazeSizeDialogFragment.getDialog());

        if (mazeEditText.isEnabled()) { // Checks if mazeEditText is enabled

            // Add mazeEditTextTextWatcher mazeEditText
            mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

            // Convert the maze in a string like the one entered by the user
            String inputString = getTypedString(mazeArray);

            // Append the inputString in mazeEditText. This will simulate the user's keyboard typing
            mazeEditText.append(inputString);

            // Check that startFloatingButton is enabled and click it
            FloatingActionButton startFloatingButton = mainFragmentActivity.findViewById(R.id.startFloatingButton);
            assertTrue(startFloatingButton.isEnabled());
            startFloatingButton.performClick();

            // After calculating, check that the activity is showing the results, as well as
            // startFloatingButton is again disabled and addFloatingButton is again enabled
            TextView shortestPathLabelTextView = mainFragmentActivity.findViewById(R.id.shortestPathLabelTextView);
            assertTrue(shortestPathLabelTextView.getVisibility() == View.GONE);

            TextView successLabelTextView = mainFragmentActivity.findViewById(R.id.succesLabelTextView);
            assertTrue(successLabelTextView.getVisibility() == View.VISIBLE);
            TextView successTextView = mainFragmentActivity.findViewById(R.id.successTextView);
            assertTrue(successTextView.getVisibility() == View.VISIBLE);
            System.out.println(successTextView.getText());

            TextView distanceLabelTextView = mainFragmentActivity.findViewById(R.id.distanceLabelTextView);
            assertTrue(distanceLabelTextView.getVisibility() == View.VISIBLE);
            TextView distanceTextView = mainFragmentActivity.findViewById(R.id.distanceTextView);
            assertTrue(distanceTextView.getVisibility() == View.VISIBLE);
            System.out.println(distanceTextView.getText());

            TextView rowSequenceLabelTextView = mainFragmentActivity.findViewById(R.id.rowSequenceLabelTextView);
            assertTrue(rowSequenceLabelTextView.getVisibility() == View.VISIBLE);
            TextView rowSequenceTextView = mainFragmentActivity.findViewById(R.id.rowSequenceTextView);
            assertTrue(rowSequenceTextView.getVisibility() == View.VISIBLE);
            System.out.println(rowSequenceTextView.getText());

            startFloatingButton = mainFragmentActivity.findViewById(R.id.startFloatingButton);
            assertTrue(!startFloatingButton.isEnabled());

            addFloatingButton = mainFragmentActivity.findViewById(R.id.addFloatingButton);
            assertTrue(addFloatingButton.isEnabled());

            // Remove mazeEditTextTextWatcher mazeEditText to avoid multiple registries for each test case
            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);

        } else {
            assertTrue(!mazeEditText.isEnabled());
        }
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
