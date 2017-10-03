package com.shortestpath.app.dialog;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shortestpath.R;
import com.shortestpath.app.MainFragmentActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

/**
 * Performs an instrumented test of the InputMazeSizeDialog to test that the input values for
 * the number of rows and columns for a maze is inside the constrained values.
 *
 * These values are:
 * For rows: Between 1 and 10
 * For columns: between 1 and 100
 * */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InputMazeSizeDialogFragmentEspressoTest {

    private int rowSize;
    private int columnSize;

    @Rule
    public ActivityTestRule<MainFragmentActivity> activityTestRule = new ActivityTestRule<>(MainFragmentActivity.class);

    @Before
    public void setUp() throws Exception {
        InputMazeSizeDialogFragment inputMazeSizeDialogFragment = InputMazeSizeDialogFragment.newInstance();
        inputMazeSizeDialogFragment.setOnMazeSizeEnteredListener(new InputMazeSizeDialogFragmentOnMazeSizeEnteredListener());
        inputMazeSizeDialogFragment.show(activityTestRule.getActivity().getFragmentManager(), null);
    }

    @Test // Test case where the input rows and columns values are outside of the constrained values:
    // rows: Any value smaller than 0
    // columns: Any value smaller that 0
    public void inputMazeSizeDialogFragmentInstrumentedTest1() {

        int rows = new Random().nextInt(Integer.MAX_VALUE - 1) * -1;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(Integer.MAX_VALUE - 1) * -1;
        String columnsString = Integer.toString(columns);

        performInstrumentedTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(0, rowSize);
        assertEquals(0, columnSize);
    }

    @Test // Test case where the input rows and columns are equal to zero
    public void inputMazeSizeDialogFragmentInstrumentedTest2() {

        String rowsString = "0";
        String columnsString = "0";

        performInstrumentedTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(0, rowSize);
        assertEquals(0, columnSize);
    }

    @Test // Test case where the input rows and columns values are inside the constrained values:
    // rows: Any value between 1 and 10
    // columns: Any value between 1 and 100
    public void inputMazeSizeDialogFragmentInstrumentedTest3() {

        int rows = new Random().nextInt(10) + 1;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(100) + 1;
        String columnsString = Integer.toString(columns);

        performInstrumentedTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(rows, rowSize);
        assertEquals(columns, columnSize);
    }

    @Test
    // Test case where the input rows and columns values are outside of the constrained values:
    // rows: Any value bigger than 10
    // columns: Any value bigger that 100
    public void InputMazeSizeDialogFragmentInstrumentedTest4() {

        int rows = new Random().nextInt(Integer.MAX_VALUE) + 11;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(Integer.MAX_VALUE) + 101;
        String columnsString = Integer.toString(columns);

        performInstrumentedTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(10, rowSize);
        assertEquals(100, columnSize);
    }

    /**
     * Performs an instrumented test with the given rows and columns size.
     * */
    private void performInstrumentedTest(String rowsString, String columnsString) {
        // Type the rows and columns values in the EditTexts of the inputMazeSizeDialogFragment and press the OK button
        onView(withId(R.id.rowsEditText)).perform(typeText(rowsString));
        onView(withId(R.id.columnsEditText)).perform(typeText(columnsString));
        onView(withText("OK")).perform(click());
    }

    /**
     * Called after user presses the "OK" button in InputMazeSizeDialogFragment.
     *
     * It returns the entered values by the user. These values are adjusted to the constrained range limits
     * if they are either smaller or bigger than the constrained range.
     * */
    private class InputMazeSizeDialogFragmentOnMazeSizeEnteredListener implements InputMazeSizeDialogFragment.OnMazeSizeEnteredListener {

        @Override
        public void onMazeSizeEntered(int rowSize, int columnSize) {
            // Set the values of both row and column sizes in the class fields
            InputMazeSizeDialogFragmentEspressoTest.this.rowSize = rowSize;
            InputMazeSizeDialogFragmentEspressoTest.this.columnSize = columnSize;
        }
    }
}
