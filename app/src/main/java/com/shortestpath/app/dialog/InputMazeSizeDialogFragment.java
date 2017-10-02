package com.shortestpath.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shortestpath.R;

/**
 * Dialog which takes from user the row and column sizes and send them to the activity through a
 * listener.
 * */
public class InputMazeSizeDialogFragment extends DialogFragment {


    // Widgets
    private View inputDialogFragment;

    private EditText rowsEditText;
    private EditText columnsEditText;

    // Listener
    private OnMazeSizeEnteredListener onMazeSizeEnteredListener;

    public interface OnMazeSizeEnteredListener {
         void onMazeSizeEntered(int rowSize, int columnSize);
    }

    public void setOnMazeSizeEnteredListener(OnMazeSizeEnteredListener onMazeSizeEnteredListener) {
        this.onMazeSizeEnteredListener = onMazeSizeEnteredListener;
    }

    /** Constructor */
    public static InputMazeSizeDialogFragment newInstance() {
        return new InputMazeSizeDialogFragment();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        alertDialogBuilder.setTitle("Enter the size of the maze");
        alertDialogBuilder.setMessage("Maximum 10x100 / Minimum 1x5. \n" +  "If you exceed or miss, the exceeded/missed range will be adjusted to the max/min supported.");
        alertDialogBuilder.setPositiveButton("OK", new PositiveButtonOnClickListener());
        setCancelable(false);

        inputDialogFragment = View.inflate(getActivity(), R.layout.input_dialog_fragment, null);

        rowsEditText = inputDialogFragment.findViewById(R.id.rowsEditText);
        columnsEditText = inputDialogFragment.findViewById(R.id.columnsEditText);

        alertDialogBuilder.setView(inputDialogFragment);

        return alertDialogBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    @Override
    public void onPause() {
        super.onPause();

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(inputDialogFragment.getWindowToken(), 0);
    }

    /**
     * Called when user presses the OK button in the Dialog.
     *
     * When pressing OK, this method call onMazeSizeEnteredListener with the row and column sizes
     * as paramters.
     * */
    private class PositiveButtonOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            // Hide Keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(inputDialogFragment.getWindowToken(), 0);

            // Get the string with the number of rows and columns
            String rowsString = rowsEditText.getText().toString();
            String columnsString = columnsEditText.getText().toString();

            int rows = 0;
            int columns = 0;

            try {
                rows = Integer.parseInt(rowsString);
                columns = Integer.parseInt(columnsString);
            } catch (NumberFormatException e) {
                Log.e("InputMazeDialogFragment", e.toString());
            }

            // Adjust rows to constrained range
            if (rows < 0) {
                rows = 0;
            } else if (rows > 10) {
                rows = 10;
            }

            // Adjust columns to constrained range
            if (columns < 0) {
                columns = 0;
            } else if (columns > 100) {
                columns = 100;
            }

            onMazeSizeEnteredListener.onMazeSizeEntered(rows, columns);
        }
    }
}
