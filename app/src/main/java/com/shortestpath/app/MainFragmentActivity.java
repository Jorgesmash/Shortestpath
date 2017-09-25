package com.shortestpath.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
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

        InputMazeSizeDialogFragment inputMazeSizeDialogFragment = InputMazeSizeDialogFragment.newInstance();
        inputMazeSizeDialogFragment.setOnMazeSizeEnteredListener(new InputDialogFragmentOnMazeSizeEnteredListener());
        inputMazeSizeDialogFragment.show(getFragmentManager(), null);
    }

    /**
     * Called after user entered the number of rows and columns for the maze in the dialog fragment.
     *
     * If rowSize < 1, it will be adjusted to 1. If rowSize > 10, it will be adjusted to 10
     * If columnSize < 5, it will be adjusted to 5. if columnSize > 100, it will be adjusted to 100
     * */
    private class InputDialogFragmentOnMazeSizeEnteredListener implements InputMazeSizeDialogFragment.OnMazeSizeEnteredListener {

        @Override
        public void onMazeSizeEntered(int rowSize, int columnSize) {

            if (rowSize > 0 && columnSize > 0) {

                // Adjust for rowSize
                if (rowSize < 1) {
                    rowSize = 1;
                } else if (rowSize > 10) {
                    rowSize = 10;
                }

                // Adjust for columnSize
                if (columnSize < 5) {
                    columnSize = 5;
                } else if (columnSize > 100) {
                    columnSize = 100;
                }

                // Update the current status of the widgets
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

                // Keep the size of the rows and columns in class variable level
                MainFragmentActivity.this.rowSize = rowSize;
                MainFragmentActivity.this.columnSize = columnSize;

                // Initialize the maze bi-dimensional array
                maze = new int[rowSize][columnSize];
            }
        }
    }

    /**
     * Called every time the representing trama of the maze entered by the user changes.
     *
     * Each time the trama changes, it looks for commas (',') and negative signs ('-') in order to determine
     * if the trama sould continue adding numbers in the same row or should it do it in the next one.
     *
     * When a coma is typed, the last entered number before the comma is appended in the maze
     * bi-dimensional array.
     *
     * When a minus sign is typed, the number to be typed after the sign will be negative.
     *
     * Once the total number of rows is reached, the maze is ready to calculate its shortest path.
     * */
    private class MazeEditTextTextWatcher implements TextWatcher {

        // Negative sign control variable
        boolean negative = false;

        // Rows and Columns Control variables
        int lastCommaIndex = -1;
        int commasCount = 0;
        int lastEnterIndex = -1;
        int entersCount = 0;

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            String numberSequenceString = charSequence.toString();

            String lastCharacterString = numberSequenceString.substring(numberSequenceString.length() - 1);
            if (lastCharacterString.equals("-")) { // If the typed character is a minus sign and it was not already typed

                // The number currently being typed is negative
                negative = !negative;

                // Remove the minus sign of the last position in the sequence
                popSubstring(charSequence.length() - 1);

                // Calculate the beginning of the number being currently typed
                String numberString;
                numberString = charSequence.toString().substring(charSequence.toString().lastIndexOf("\n") + 1); // Find the beginning of the last row
                numberString = numberString.substring(numberString.lastIndexOf(" ") + 1); // In the last row, find the last space character

                if (negative) {
                    // If the negative flag is positive, make the number negative
                    numberString = "-" + numberString;
                } else {
                    // Else, remove the negative sign of the number to make it positive
                    numberString = numberString.substring(numberString.lastIndexOf(" ") == -1 ? 1 : numberString.lastIndexOf(" ") + 2);
                }

                // Remove the number and right away add it with its new sign
                if (mazeEditText.getText().toString().lastIndexOf(" ") > mazeEditText.getText().toString().lastIndexOf("\n")) {
                    popSubstring(mazeEditText.getText().toString().lastIndexOf(" ") + 1);
                } else {
                    popSubstring(mazeEditText.getText().toString().lastIndexOf("\n") + 1);
                }
                pushSubstring(numberString);

            } else if (lastCharacterString.equals(",")) { // If the typed character is a comma

                numberSequenceString = charSequence.toString().replace(" ", "");

                commasCount++;
                if (commasCount < columnSize) { // Checks if the new number is still part of the current row

                    // Take the last entered number and add it in maze
                    boolean numberAdded = appendsNumberInMaze(numberSequenceString);
                    if (numberAdded) {

                        // Add a space after the comma in the number sequence
                        pushSubstring(" ");

                        // Update the current maze insertion position
                        positionTextView.setText("[" + entersCount + "x" + commasCount + "]");
                    }

                } else { // Else, the new number is part of the next row

                    // Take the last entered number and add it in maze
                    boolean numberAdded = appendsNumberInMaze(numberSequenceString);
                    if (numberAdded) {

                        // Remove last comma of the number sequence
                        popSubstring(mazeEditText.length() - 1);

                        entersCount++;
                        if (entersCount < rowSize) { // Checks if it's still possible to add more rows

                            // Add an enter at the end of the sequence
                            pushSubstring("\n");

                            // Reset variables for row control
                            lastCommaIndex = -1;
                            commasCount = 0;

                            // Increment lastEnterIndex
                            lastEnterIndex = numberSequenceString.length() - 1;

                        } else { // Else, the maze is ready

                            // Reset variables for both row and column control
                            lastCommaIndex = -1;
                            commasCount = 0;
                            lastEnterIndex = -1;
                            entersCount = 0;

                            // Update the status of the widgets
                            hintTextView.setText(getString(R.string.press_start_button));
                            positionTextView.setVisibility(View.INVISIBLE);
                            mazeSizeTextView.setVisibility(View.VISIBLE);
                            mazeEditText.setEnabled(false);
                            startFloatingButton.setEnabled(true);
                        }
                    }
                }
            }
        }

        /**
         * Appends the last number of a sequence in the maze.
         *
         * The position of the number in the maze will be calculated by the following:
         * 1. The row index is determined by the count of new line characters ('\n') in the sequence string.
         * 2. The column index is determined by the count of commas (',') from the last new line to the end of the sequence string.
         * */
        private boolean appendsNumberInMaze(String numberSequenceString) {

            String lastRowString = numberSequenceString;

            if (numberSequenceString.contains("\n")) {
                lastRowString = numberSequenceString.substring(lastEnterIndex + 1, numberSequenceString.length());
            }

            String numberString = lastRowString.substring(lastCommaIndex + 1, lastRowString.length() - 1);
            int number;
            try {
                number = Integer.parseInt(numberString);
            } catch (NumberFormatException e) {

                if (mazeEditText.length() > 0) {
                    popSubstring(mazeEditText.length() - 1);
                }
                commasCount--;
                return false;
            }

            // Insert the number in the maze bi-dimensional array
            maze[entersCount][commasCount - 1] = number;

            // Update the index of the last comma
            lastCommaIndex = lastRowString.length() - 1;

            // Reset negative sign control variable to false
            negative = false;

            return true;
        }

        /**
         * Pushes new characters at the end of the mazeEditText's text
         * */
        private void pushSubstring(String string) {
            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);
            mazeEditText.append(string);
            mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);
        }

        /**
         * Removes the last character of the mazeEditText's text
         * */
        private String popSubstring(int startIndex) {

            String string = mazeEditText.getText().subSequence(startIndex, mazeEditText.length()).toString();

            mazeEditText.removeTextChangedListener(mazeEditTextTextWatcher);
            mazeEditText.getText().delete(startIndex, mazeEditText.length());
            mazeEditText.addTextChangedListener(mazeEditTextTextWatcher);

            return string;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable editable) { }
    }
}
