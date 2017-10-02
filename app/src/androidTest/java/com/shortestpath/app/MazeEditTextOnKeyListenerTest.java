package com.shortestpath.app;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.shortestpath.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.shortestpath.app.testutils.TestUtilsActions.requestFocus;
import static com.shortestpath.app.testutils.TestUtilsActions.setEnabled;
import static com.shortestpath.app.testutils.TestUtilsActions.setFocusableInTouchMode;
import static junit.framework.Assert.assertEquals;

/**
 * Checks if the associated View.OnKeyListener to mazeEditText is able to convert the input element
 * sequence in an output sequence which looks aesthetically good in the mazeEditText.
 *
 * MainFragmentActivity.MazeEditTextOnKeyListener
 * */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MazeEditTextOnKeyListenerTest {

    @Rule
    public ActivityTestRule<MainFragmentActivity> activityTestRule = new ActivityTestRule<>(MainFragmentActivity.class);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("19, 10, 19, 10, 19\n21, 23, 20, 19, 12\n20, 12, 20, 11, 10", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample4() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "8", "5", "3", "5"},
        };

        String inputString = getTypedString(mazeArray);
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("5, 5, H\n8, M, 7\n5, 7, 5", outputString);
    }

    @Test
    public void testOnKeyListenerWithExample7() throws Exception {

        String[][] mazeArray = new String[][] {};

        String inputString = getTypedString(mazeArray);
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

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
        String outputString = testOnKeyListener(inputString, mazeArray.length, mazeArray.length == 0 ? 0 : mazeArray[0].length);

        // Check if outputString is equals to the expected result
        assertEquals("1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1\n2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2", outputString);
    }

    @Before
    public void setUp() throws Exception {

        // Set the enabled state of addFloatingButton to false, since it is not
        // necessary to use this botton during this test
        onView(withId(R.id.addFloatingButton)).perform(setEnabled(false));

        // Make mazeEditText enabled and focusableInTouchMode, so we can use and bring the keyboard before start typing
        // methods 'setEnabled' and 'setFocusableInTouchMode' are custom ViewActions
        onView(withId(R.id.mazeEditText)).perform(setEnabled(true));
        onView(withId(R.id.mazeEditText)).perform(setFocusableInTouchMode(true));
        onView(withId(R.id.mazeEditText)).perform(requestFocus(true));
    }

    /**
     * Performs the instrumented test with a given maze to test the associated View.OnKeyListener
     * to mazeEditText
     * */
    private String testOnKeyListener(String inputString, int rowSize, int columnSize) {

        // Set the maze rows and columns size
        activityTestRule.getActivity().setRowSize(rowSize);
        activityTestRule.getActivity().setColumnSize(columnSize);

        // Perform the typing of the inputString in mazeEditText
        onView(withId(R.id.mazeEditText)).perform(typeText(inputString));

        // Get the processed outputString from mazeEditText
        EditText mazeEditText = activityTestRule.getActivity().findViewById(R.id.mazeEditText);

        return mazeEditText.getText().toString();
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
