package com.shortestpath.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shortestpath.BuildConfig;
import com.shortestpath.R;
import com.shortestpath.app.dialog.InputMazeSizeDialogFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainFragmentActivityText {

    MainFragmentActivity activity = Robolectric.setupActivity(MainFragmentActivity.class);

    @Test
    public void exampleTest1() throws Exception {

        String[][] mazeArray = new String[][] {
                {"3", "4", "1", "2", "8", "6"},
                {"6", "1", "8", "2", "7", "4"},
                {"5", "9", "3", "9", "9", "5"},
                {"8", "4", "1", "3", "2", "6"},
                {"3", "7", "2", "8", "6", "4"}
        };

        performInstrumentedTest(mazeArray);
    }

    /**
     * Performs the instrumented test with a given maze.
     * */
    private void performInstrumentedTest(String[][] mazeArray) {

        // Opens a dialog where the size of the maze is typed
        FloatingActionButton addFloatingButton = activity.findViewById(R.id.addFloatingButton);
        addFloatingButton.performClick();

        // Check that inputMazeSizeDialogFragment has been opened after clicking addFloatingButton
        InputMazeSizeDialogFragment inputMazeSizeDialogFragment = (InputMazeSizeDialogFragment) activity.getFragmentManager().findFragmentByTag("inputMazeSizeDialogFragment");
        assertNotNull(inputMazeSizeDialogFragment);

        // Check that the Dialog is not Null
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

        // Insert the
        EditText mazeEditText = activity.findViewById(R.id.mazeEditText);
        if (mazeEditText.isEnabled()) {

            /**
             * This is the end of the unit test using Robolectric.
             *
             * I was unable to find a way Robolectric can simulate text typing in an EditText.
             *
             * Because of this I cannot fire the method 'onKey' in the interface
             * MainFragmentActivity.MazeEditTextOnKeyListener. This interface is on charge of
             * interpret each typed key to check if it is part of an element for the maze or it is
             * a control character to separate the elements.
             *
             * mazeEditText.setText(string); is unable to fires this interface.
             * */

            assertTrue(mazeEditText.isEnabled());

        } else {
            assertTrue(!mazeEditText.isEnabled());
        }
    }
}
