package com.shortestpath.app;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.shortestpath.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Performs an instrumented test of the MainFragmentActivity flow to calculate the
 * shortest path of a given maze.
 * */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainFragmentActivityTest {

    @Rule
    public ActivityTestRule<MainFragmentActivity> activityTestRule = new ActivityTestRule<>(MainFragmentActivity.class);

    @Test
    public void exampleInstrumentedTest1() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "8", "6", "4"}
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest2() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "1", "2", "3"}
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest3() throws Exception {

        String[][] mazeArray = new String[][] {
                {"19", "10", "19", "10", "19"},
                {"21", "23", "20", "19", "12"},
                {"20", "12", "20", "11", "10"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest4() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "8", "5", "3", "5"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest5() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5"},
                {"8"},
                {"5"},
                {"3"},
                {"5"}
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest6() throws Exception {

        String[][] mazeArray = new String[][] {
                {"5", "5", "H"},
                {"8", "M", "7"},
                {"5", "7", "5"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest7() throws Exception {

        String[][] mazeArray = new String[][] {};

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest8() throws Exception {

        String[][] mazeArray = new String[][] {
                {"69", "10", "19", "10", "19"},
                {"51", "23", "20", "19", "12"},
                {"60", "12", "20", "11", "10"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest9() throws Exception {

        String[][] mazeArray = new String[][] {
                {"60", "3", "3", "6"},
                {"6", "3", "7", "9"},
                {"5", "6", "8", "3"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest10() throws Exception {

        String[][] mazeArray = new String[][] {
                {"6", "3", "-5", "9"},
                {"-5", "2", "4", "10"},
                {"3", "-2", "6", "10"},
                {"6", "-1", "-2", "10"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest11() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51"},
                {"0", "51"},
                {"51", "51"},
                {"5", "5"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest12() throws Exception {

        String[][] mazeArray = new String[][] {
                {"51", "51", "51"},
                {"0", "51", "51"},
                {"51", "51", "51"},
                {"5", "5", "51"},
        };

        performInstrumentedTest(mazeArray);
    }

    @Test
    public void exampleInstrumentedTest13() throws Exception {

        String[][] mazeArray = new String[][] {
                {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2"},
        };

        performInstrumentedTest(mazeArray);
    }

    /**
     * Performs the instrumented test with a given maze.
     * */
    private void performInstrumentedTest(String[][] maze) {

        // Opens a dialog where the size of the maze is typed
        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());

        // If the typed size of the maze is bigger than zero, check mazeEditText is enabled
        EditText mazeEditText = activityTestRule.getActivity().findViewById(R.id.mazeEditText);
        if (mazeEditText.isEnabled()) {
            // If mazeEditText is enabled, type the data of the maze and click startFloatingButton to calculate
            onView(withId(R.id.mazeEditText)).perform(typeText(getTypedString(maze)));
            onView(withId(R.id.startFloatingButton)).perform(click());

            // After calculating, check that the activity is showing the results, as well as
            // startFloatingButton is now disabled and addFloatingButton is now enabled
            onView(withId(R.id.shortestPathLabelTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.succesLabelTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.successTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.distanceLabelTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.distanceTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.rowSequenceLabelTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.rowSequenceTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.startFloatingButton)).check(matches(not(isEnabled())));
            onView(withId(R.id.addFloatingButton)).check(matches(isEnabled()));

        } else {
            // As the input maze has 0 length, check if the mazeEditText is not enabled
            onView(withId(R.id.mazeEditText)).check(matches(not(isEnabled())));
        }
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
