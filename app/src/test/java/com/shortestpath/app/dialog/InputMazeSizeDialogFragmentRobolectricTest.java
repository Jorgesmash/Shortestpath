package com.shortestpath.app.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
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

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Performs an instrumented test of the InputMazeSizeDialog to test that the input values for
 * the number of rows and columns for a maze is inside the constrained values.
 *
 * These values are:
 * For rows: Between 1 and 10
 * For columns: between 1 and 100
 * */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InputMazeSizeDialogFragmentRobolectricTest {

    // The FragmentActivity which holds the view hierarchy
    MainFragmentActivity mainFragmentActivity;

    // The DialogFragment to be tested
    InputMazeSizeDialogFragment inputMazeSizeDialogFragment;

    private int rowSize;
    private int columnSize;

    @Before
    public void setUp() throws Exception {
        mainFragmentActivity = Robolectric.setupActivity(MainFragmentActivity.class);
        inputMazeSizeDialogFragment = InputMazeSizeDialogFragment.newInstance();
        inputMazeSizeDialogFragment.setOnMazeSizeEnteredListener(new InputMazeSizeDialogFragmentOnMazeSizeEnteredListener());
        inputMazeSizeDialogFragment.show(mainFragmentActivity.getFragmentManager(), null);
    }

    @Test // Test case where the input rows and columns values are outside of the constrained values:
    // rows: Any value smaller than 0
    // columns: Any value smaller that 0
    public void inputMazeSizeDialogFragmentTest1() {

        int rows = new Random().nextInt(Integer.MAX_VALUE - 1) * -1;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(Integer.MAX_VALUE - 1) * -1;
        String columnsString = Integer.toString(columns);

        performTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(0, rowSize);
        assertEquals(0, columnSize);
    }

    @Test // Test case where the input rows and columns are equal to zero
    public void inputMazeSizeDialogFragmentTest2() {

        String rowsString = "0";
        String columnsString = "0";

        performTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(0, rowSize);
        assertEquals(0, columnSize);
    }

    @Test // Test case where the input rows and columns values are inside the constrained values:
    // rows: Any value between 1 and 10
    // columns: Any value between 1 and 100
    public void inputMazeSizeDialogFragmentTest3() {

        int rows = new Random().nextInt(10) + 1;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(100) + 1;
        String columnsString = Integer.toString(columns);

        performTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(rows, rowSize);
        assertEquals(columns, columnSize);
    }

    @Test
    // Test case where the input rows and columns values are outside of the constrained values:
    // rows: Any value bigger than 10
    // columns: Any value bigger that 100
    public void InputMazeSizeDialogFragmentTest4() {

        int rows = new Random().nextInt(Integer.MAX_VALUE) + 11;
        String rowsString = Integer.toString(rows);

        int columns = new Random().nextInt(Integer.MAX_VALUE) + 101;
        String columnsString = Integer.toString(columns);

        performTest(rowsString, columnsString);

        // Assert the corresponding row and column sizes are also 0
        assertEquals(10, rowSize);
        assertEquals(100, columnSize);
    }

    /**
     * Performs an instrumented test with the given rows and columns size.
     * */
    private void performTest(String rowsString, String columnsString) {

        // Type the rows and columns values in the EditTexts of the inputMazeSizeDialogFragment
        EditText rowsEditText = inputMazeSizeDialogFragment.getDialog().findViewById(R.id.rowsEditText);
        rowsEditText.setText(rowsString);
        EditText columnsEditText = inputMazeSizeDialogFragment.getDialog().findViewById(R.id.columnsEditText);
        columnsEditText.setText(columnsString);

        // Press the OK button
        AlertDialog alertDialog = (AlertDialog) inputMazeSizeDialogFragment.getDialog();
        Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.performClick();
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
            InputMazeSizeDialogFragmentRobolectricTest.this.rowSize = rowSize;
            InputMazeSizeDialogFragmentRobolectricTest.this.columnSize = columnSize;
        }
    }
}
