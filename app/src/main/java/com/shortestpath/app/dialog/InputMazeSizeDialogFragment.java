package com.shortestpath.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shortestpath.R;

public class InputMazeSizeDialogFragment extends DialogFragment {

    private static Context context;

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
    public static InputMazeSizeDialogFragment newInstance(Context context) {
        InputMazeSizeDialogFragment.context = context;
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

    private class PositiveButtonOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            int rowSize = 0;
            int columnSize = 0;

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(inputDialogFragment.getWindowToken(), 0);

            String rowString = rowsEditText.getText().toString();
            String columnString = columnsEditText.getText().toString();

            try {
                rowSize = Integer.parseInt(rowString);
                columnSize = Integer.parseInt(columnString);
            } catch (NumberFormatException e) {
            }

            onMazeSizeEnteredListener.onMazeSizeEntered(rowSize, columnSize);
        }
    }
}
