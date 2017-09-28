package com.shortestpath;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.shortestpath.app.main.MainFragmentActivity;
import com.shortestpath.app.mazeparser.MazeParser;
import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Performs an instrumented test of the application flow to calculate the
 * shortest path of a given maze.
 *
 * The result of the calculation will be printed with ASSERT priority in Logcat.
 * */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainFragmentActivityTest {

    @Rule
    public ActivityTestRule<MainFragmentActivity> activityTestRule = new ActivityTestRule<>(MainFragmentActivity.class);

    @Before
    public void setUp() throws Exception {

        // Creates a new instance of the MazeParser class to calculate the shortest path.
        MazeParser mazeParser = activityTestRule.getActivity().getMazeParser();

        // Sets an OnShortestPathFoundListener which will be called to inform that the
        // when the shortest path calculation process has finished.
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
    }

    @Test
    public void exampleInstrumentedTest1() throws Exception {

        String[][] maze = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "8", "6", "4"}
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest2() throws Exception {

        String[][] maze = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "1", "2", "3"}
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest3() throws Exception {

        String[][] maze = new String[][] {
                {"19", "10", "19", "10", "19"},
                {"21", "23", "20", "19", "12"},
                {"20", "12", "20", "11", "10"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest4() throws Exception {

        String[][] maze = new String[][] {
                {"5", "8", "5", "3", "5"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest5() throws Exception {

        String[][] maze = new String[][] {
                {"5"},
                {"8"},
                {"5"},
                {"3"},
                {"5"}
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest6() throws Exception {

        String[][] maze = new String[][] {
                {"5", "5", "H"},
                {"8", "M", "7"},
                {"5", "7", "5"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());

        // As an invalid matriz is entered, mazeEditText may show a bad representation of the data
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));

        // As an invalid matriz is entered, check if startFloatingButton is disabled
        onView(withId(R.id.startFloatingButton)).check(matches(not(isEnabled())));
    }

    @Test
    public void exampleInstrumentedTest7() throws Exception {

        String[][] maze = new String[][] {};

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());

        // As the input maze has 0 length, check if the mazeEditText is not enabled
        onView(withId(R.id.mazeEditText)).check(matches(not(isEnabled())));
    }

    @Test
    public void exampleInstrumentedTest8() throws Exception {

        String[][] maze = new String[][] {
                {"69", "10", "19", "10", "19"},
                {"51", "23", "20", "19", "12"},
                {"60", "12", "20", "11", "10"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest9() throws Exception {

        String[][] maze = new String[][] {
                {"60", "3", "3", "6"},
                {"6", "3", "7", "9"},
                {"5", "6", "8", "3"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest10() throws Exception {

        String[][] maze = new String[][] {
                {"6", "3", "-5", "9"},
                {"-5", "2", "4", "10"},
                {"3", "-2", "6", "10"},
                {"6", "-1", "-2", "10"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest11() throws Exception {

        String[][] maze = new String[][] {
                {"51", "51"},
                {"0", "51"},
                {"51", "51"},
                {"5", "5"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest12() throws Exception {

        String[][] maze = new String[][] {
                {"51", "51", "51"},
                {"0", "51", "51"},
                {"51", "51", "51"},
                {"5", "5", "51"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
    }

    @Test
    public void exampleInstrumentedTest13() throws Exception {

        String[][] maze = new String[][] {
                {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"},
                {"2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2"},
        };

        String typedString = getTypedString(maze);

        onView(withId(R.id.addFloatingButton)).perform(click());
        onView(withId(R.id.rowsEditText)).perform(typeText("" + maze.length));
        onView(withId(R.id.columnsEditText)).perform(typeText("" + (maze.length == 0 ? 0 : maze[0].length)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.mazeEditText)).perform(typeText(typedString));
        onView(withId(R.id.startFloatingButton)).perform(click());
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

    /**
     * Listens if the MazeParser class has finished to findShortestPath the shortest path
     */
    private class MazeParserOnShortestPathFoundListener implements MazeParser.OnShortestPathFoundListener {

        @Override
        public void onShortestPathFound(boolean success, Path path) {

            if (success) {
                // Set success result as "Yes"
                Log.println(Log.ASSERT, "InstrumentedTest", "Yes");

                // Set distance result
                String distanceString = "" + path.getCost();
                Log.println(Log.ASSERT, "InstrumentedTest", distanceString);

                // Set sequence result
                setSequenceResult(path);

            } else {
                // Set success result as "No"
                Log.println(Log.ASSERT, "InstrumentedTest", "No");

                if (path == null) {
                    // Set a message saying that the given matrix is invalid
                    Log.println(Log.ASSERT, "InstrumentedTest", "Invalid matrix");

                } else if (path.getNodeList().size() == 0) {
                    // Set distance result
                    String distanceString = "" + path.getCost();
                    Log.println(Log.ASSERT, "InstrumentedTest", distanceString);

                    // Set sequence result as an empty array
                    Log.println(Log.ASSERT, "InstrumentedTest", "[]");

                } else if (path.getNodeList().size() > 0) {
                    // Set distance result
                    String distanceString = "" + path.getCost();
                    Log.println(Log.ASSERT, "InstrumentedTest", distanceString);

                    // Set sequence result
                    setSequenceResult(path);
                }
            }
        }

        private void setSequenceResult(Path path) {
            // Set sequence result
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (int i = 0; i < path.getNodeList().size(); i++) {

                Node node = (Node) path.getNodeList().get(i);

                stringBuilder.append(node.getRow() + 1);

                if (i < path.getNodeList().size() - 1) {
                    stringBuilder.append(", ");
                } else {
                    stringBuilder.append("]");
                }
            }
            Log.println(Log.ASSERT, "InstrumentedTest", stringBuilder.toString());
        }
    }
}
