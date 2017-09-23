package com.shortestpath.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shortestpath.R;
import com.shortestpath.app.dialog.InputMazeSizeDialogFragment;
import com.shortestpath.app.mazeparser.MazeParser;
import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

public class MainFragmentActivity extends BaseFragmentActivity {

    // Maze variables
    private int[][] maze;
    private int rowSize;
    private int columnSize;

    // Widgets
    private TextView shortestPathLabelTextView;
    private TextView successLabelTextView;
    private TextView successTextView;
    private TextView distanceLabelTextView;
    private TextView distanceTextView;
    private TextView rowSequenceLabelTextView;
    private TextView rowSequenceTextView;

    private TextView hintTextView;
    private TextView positionTextView;

    private EditText mazeEditText;

    private FloatingActionButton startFloatingButton;
    private FloatingActionButton addFloatingButton;

    private TextView mazeSizeLabelTextView;
    private TextView mazeSizeTextView;
    private MazeEditTextTextWatcher mazeEditTextTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_activity);

        shortestPathLabelTextView = findViewById(R.id.shortestPathLabelTextView);

        successLabelTextView = findViewById(R.id.succesLabelTextView);
        successLabelTextView.setVisibility(View.GONE);
        successTextView = findViewById(R.id.successTextView);
        successTextView.setVisibility(View.GONE);

        distanceLabelTextView = findViewById(R.id.distanceLabelTextView);
        distanceLabelTextView.setVisibility(View.GONE);
        distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setVisibility(View.GONE);

        rowSequenceLabelTextView = findViewById(R.id.rowSequenceLabelTextView);
        rowSequenceLabelTextView.setVisibility(View.GONE);
        rowSequenceTextView = findViewById(R.id.rowSequenceTextView);
        rowSequenceTextView.setVisibility(View.GONE);

        hintTextView = findViewById(R.id.hintTextView);

        positionTextView = findViewById(R.id.positionTextView);
        positionTextView.setVisibility(View.INVISIBLE);

        mazeEditText = findViewById(R.id.mazeEditText);
        registerEditText(mazeEditText);
        mazeEditText.setFocusable(false);

        mazeEditTextTextWatcher = new MazeEditTextTextWatcher();

        startFloatingButton = findViewById(R.id.startFloatingButton);
        startFloatingButton.setOnClickListener(new StartFloatingButtonOnClickListener());
        startFloatingButton.setVisibility(View.INVISIBLE);

        addFloatingButton = findViewById(R.id.addFloatingButton);
        addFloatingButton.setOnClickListener(new AddFloatingButtonOnClickListener());
        addFloatingButton.requestFocus();

        mazeSizeLabelTextView = findViewById(R.id.mazeSizeLabelTextView);
        mazeSizeLabelTextView.setVisibility(View.INVISIBLE);

        mazeSizeTextView = findViewById(R.id.mazeSizeTextView);
        mazeSizeTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Hide keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(mazeEditText.getWindowToken(), 0);
    }

    /**
     * Creates a new instance of the MazeParser class to calculate the shortest path.
     * Also sets an OnShortestPathFoundListener which will be called when the process has finished (successful or not)
     * to calculate the shortest path.
     */
    private void findShortestPathInMaze(int[][] maze) {
        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    /**
     * Listens if the MazeParser class has finished to calculate the shortest path
     */
    private class StartFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Hide custom keyboard
            MainFragmentActivity.this.hideCustomKeyboard();

            findShortestPathInMaze(maze);
        }
    }

    /**
     * Listens if the MazeParser class has finished to calculate the shortest path
     */
    private class MazeParserOnShortestPathFoundListener implements MazeParser.OnShortestPathFoundListener {

        @Override
        public void onShortestPathFound(boolean success, Path path) {

            // Remove TextWatcher from mazeEditText
            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);

            // Disable startFloatingButton
            startFloatingButton.setEnabled(false);

            // Enable visibility for title
            shortestPathLabelTextView.setVisibility(View.GONE);

            // Enable visibility for result section
            successLabelTextView.setVisibility(View.VISIBLE);
            successTextView.setVisibility(View.VISIBLE);
            distanceLabelTextView.setVisibility(View.VISIBLE);
            distanceTextView.setVisibility(View.VISIBLE);
            rowSequenceLabelTextView.setVisibility(View.VISIBLE);
            rowSequenceTextView.setVisibility(View.VISIBLE);

            if (success) {
                // Set success result as "Yes"
                successTextView.setText("Yes");

                // Set distance result
                String distanceString = new Integer(path.getDistance()).toString();
                distanceTextView.setText(distanceString);

                // Set sequence result
                setSequenceResult(path);

            } else {
                // Set success result as "No"
                successTextView.setText("No");

                if (path == null) {
                    // Set a message saying that the given matrix is invalid
                    rowSequenceTextView.setText(R.string.invalid_matrix);

                } else if (path.getNodeList().size() == 0) {
                    // Set distance result
                    String distanceString = new Integer(path.getDistance()).toString();
                    rowSequenceTextView.setText(distanceString);

                    // Show an empty array
                    rowSequenceTextView.setText("[]");

                } else if (path.getNodeList().size() > 0) {

                    // Set distance result
                    String distanceString = new Integer(path.getDistance()).toString();
                    rowSequenceTextView.setText(distanceString);

                    // Set sequence result
                    setSequenceResult(path);
                }

            }

            // Change instructions label
            hintTextView.setText(R.string.press_add_button_to_start_again);

            // Enable Add button again
            addFloatingButton.setEnabled(true);
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
            rowSequenceTextView.setText(stringBuilder.toString());
        }
    }

    private class AddFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Reset all the widgets to initial values
            shortestPathLabelTextView.setVisibility(View.VISIBLE);

            successLabelTextView.setVisibility(View.GONE);
            successTextView.setVisibility(View.GONE);
            distanceLabelTextView.setVisibility(View.GONE);
            distanceTextView.setVisibility(View.GONE);
            rowSequenceLabelTextView.setVisibility(View.GONE);
            rowSequenceTextView.setVisibility(View.GONE);

            hintTextView.setText(getString(R.string.press_add_button_to_continue));

            positionTextView.setText("[0, 0]");
            positionTextView.setVisibility(View.INVISIBLE);

            mazeEditText.setText("");

            mazeSizeLabelTextView.setVisibility(View.INVISIBLE);
            mazeSizeTextView.setVisibility(View.INVISIBLE);

            startFloatingButton.setVisibility(View.INVISIBLE);

            showInputMazeSizeDialogFragment();
        }
    }

    private void showInputMazeSizeDialogFragment() {

        InputMazeSizeDialogFragment inputMazeSizeDialogFragment = InputMazeSizeDialogFragment.newInstance(this);
        inputMazeSizeDialogFragment.setOnMazeSizeEnteredListener(new InputDialogFragmentOnMazeSizeEnteredListener());
        inputMazeSizeDialogFragment.show(getFragmentManager(), null);
    }

    private class InputDialogFragmentOnMazeSizeEnteredListener implements InputMazeSizeDialogFragment.OnMazeSizeEnteredListener {

        @Override
        public void onMazeSizeEntered(int rowSize, int columnSize) {

            if (rowSize > 0 && columnSize > 0) {

                if (rowSize < 1) {
                    rowSize = 1;
                } else if (rowSize > 10) {
                    rowSize = 10;
                }

                if (columnSize < 5) {
                    columnSize = 5;
                } else if (columnSize > 100) {
                    columnSize = 100;
                }

                MainFragmentActivity.this.rowSize = rowSize;
                MainFragmentActivity.this.columnSize = columnSize;

                mazeEditText.setEnabled(true);
                mazeEditText.setFocusableInTouchMode(true);
                mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);
                MainFragmentActivity.this.showCustomKeyboard(mazeEditText);

                hintTextView.setText(getString(R.string.insert_number_for_position_label));

                positionTextView.setVisibility(View.VISIBLE);

                addFloatingButton.setEnabled(false);
                startFloatingButton.setVisibility(View.VISIBLE);
                startFloatingButton.setEnabled(false);

                mazeSizeLabelTextView.setVisibility(View.VISIBLE);

                mazeSizeTextView.setText(rowSize + "x" + columnSize);
                mazeSizeTextView.setVisibility(View.VISIBLE);

                initializeMaze(rowSize, columnSize);
            }
        }
    }

    private class MazeEditTextTextWatcher implements TextWatcher {

        // Control variables
        int lastCommaIndex = -1;
        int commasCount = 0;
        int lastEnterIndex = -1;
        int entersCount = 0;

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            String numberSequenceString = charSequence.toString();

            String lastCharacterString = numberSequenceString.substring(numberSequenceString.length() - 1);
            if (lastCharacterString.equals(",")) {

                commasCount++;
                if (commasCount < columnSize) {

                    // Take the last entered number and place it in maze
                    String compactNumberSequenceString = numberSequenceString.replace(" ", "");
                    if (compactNumberSequenceString.contains("\n")) {
                        compactNumberSequenceString = compactNumberSequenceString.substring(lastEnterIndex +1, compactNumberSequenceString.length());
                    }
                    String numberString = compactNumberSequenceString.substring(lastCommaIndex + 1, compactNumberSequenceString.length() - 1);
                    int number;
                    try {
                        number = Integer.parseInt(numberString);
                    } catch (NumberFormatException e) {
                        int index = numberSequenceString.lastIndexOf(',');

                        if (mazeEditText.length() > 0) {
                            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);
                            mazeEditText.getText().delete(mazeEditText.length() - 1, mazeEditText.length());
                            mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);
                        }
                        commasCount--;
                        return;
                    }
                    maze[entersCount][commasCount - 1] = number;
                    lastCommaIndex = compactNumberSequenceString.length() - 1;

                    // Add a space after the comma in the number sequence
                    mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);
                    mazeEditText.append(" ");
                    mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

                    // Update the current insert of the maze
                    positionTextView.setText("[" + entersCount + "x" + commasCount + "]");

                } else {

                    // Take the last entered number and place it in maze
                    String compactNumberSequenceString = numberSequenceString.replace(" ", "");
                    if (compactNumberSequenceString.contains("\n")) {
                        compactNumberSequenceString = compactNumberSequenceString.substring(lastEnterIndex + 1, compactNumberSequenceString.length());
                    }
                    String numberString = compactNumberSequenceString.substring(lastCommaIndex + 1, compactNumberSequenceString.length() - 1);
                    int number = Integer.parseInt(numberString);
                    maze[entersCount][commasCount - 1] = number;
                    lastCommaIndex = compactNumberSequenceString.length() - 1;

                    // Remove last comma in the number sequence
                    mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);
                    int index = numberSequenceString.lastIndexOf(',');
                    mazeEditText.getText().delete(index, numberSequenceString.length());

                    entersCount ++;
                    if (entersCount < rowSize) {

                        // Add an enter at the end of the secuence
                        mazeEditText.append("\n");
                        mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

                        // Reset variables
                        lastCommaIndex = -1;
                        commasCount = 0;

                        // Increment lastEnterIndex
                        compactNumberSequenceString = mazeEditText.getText().toString();
                        compactNumberSequenceString = compactNumberSequenceString.replace(" ", "");
                        lastEnterIndex = compactNumberSequenceString.length() - 1;

                    } else {

                        mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

                        // Reset All variables
                        lastCommaIndex = -1;
                        commasCount = 0;
                        lastEnterIndex = -1;
                        entersCount = 0;

                        hintTextView.setText(getString(R.string.press_start_button));
                        positionTextView.setVisibility(View.INVISIBLE);
                        mazeSizeTextView.setVisibility(View.VISIBLE);
                        mazeEditText.setEnabled(false);
                        startFloatingButton.setEnabled(true);
                    }
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable editable) { }
    }

    private void initializeMaze(int row, int column) {

        maze = new int[row][column];
    }
}
