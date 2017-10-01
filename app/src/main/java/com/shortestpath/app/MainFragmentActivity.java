package com.shortestpath.app;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shortestpath.R;
import com.shortestpath.app.dialog.InputMazeSizeDialogFragment;
import com.shortestpath.app.keyboard.NumericKeyboardView;
import com.shortestpath.app.mazeparser.MazeParser;
import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

/**
 * The presentation activity of the app.
 *
 * Point of interaction with user to get a maze and show the shortest path.
 * */
public class MainFragmentActivity extends FragmentActivity {

    // Maze variables
    private String[][] maze;
    private int rowSize;
    private int columnSize;

    // The maze parser
    private MazeParser mazeParser;

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

    // Numeric Keyboard
    private NumericKeyboardView numericKeyboardView;

    /**
     * Getter of the MazeParser instance to expose it for external classes so they can set a listener
     * to know when the process of calculating the shortest path is finished.
     */
    public MazeParser getMazeParser() {
        return mazeParser;
    }

    /** Constructor */
    public MainFragmentActivity() {

        // Creates a new instance of the MazeParser class to calculate the shortest path
        mazeParser = new MazeParser();

        // Sets an OnShortestPathFoundListener which will be called to inform that the
        // when the shortest path calculation process has finished
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
    }

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
        mazeEditText.setOnFocusChangeListener(new MazeEditTextOnFocusChangeListener());
        mazeEditText.setOnClickListener(new MazeEditTextOnClickListener());
        mazeEditText.setOnKeyListener(new MazeEdiTextOnKeyListener());
        mazeEditText.setFocusable(false);

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

        numericKeyboardView = findViewById(R.id.numericKeyboardView);
        numericKeyboardView.setPreviewEnabled(false);
        Keyboard keyboard = new Keyboard(this, R.xml.keyboard);
        numericKeyboardView.setKeyboard(keyboard);
        numericKeyboardView.setOnKeyboardActionListener(numericKeyboardView.new KeyboardActionListener());

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Hide keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(mazeEditText.getWindowToken(), 0);
    }

    /**
     * Called when user clicks the Start Floating Button.
     *
     * When clicked, the calculation process to find the shortest path is started.
     */
    private class StartFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Hide custom keyboard
            numericKeyboardView.hideCustomKeyboard();

            // Start the calculation process to find the shortest path
            mazeParser.findShortestPath(maze);
        }
    }

    /**
     * Listens if the MazeParser class has finished to findShortestPath the shortest path.
     *
     * Once the process has finished the results of the calculation are displayed in screen.
     */
    private class MazeParserOnShortestPathFoundListener implements MazeParser.OnShortestPathFoundListener {

        @Override
        public void onShortestPathFound(boolean success, Path path) {

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
                successTextView.setText(R.string.yes);

                // Set distance result
                String distanceString = "" + path.getTotalCost();
                distanceTextView.setText(distanceString);

                // Set sequence result
                setSequenceResult(path);

            } else {
                // Set success result as "No"
                successTextView.setText(R.string.no);

                if (path == null) {
                    // Set a message saying that the given matrix is invalid
                    rowSequenceTextView.setText(R.string.invalid_matrix);

                } else if (path.getNodeList().size() == 0) {
                    // Set distance result
                    String distanceString = "" + path.getTotalCost();
                    rowSequenceTextView.setText(distanceString);

                    // Show an empty array
                    rowSequenceTextView.setText("[]");

                } else if (path.getNodeList().size() > 0) {

                    // Set distance result
                    String distanceString = "" + path.getTotalCost();
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

        /**
         * Forms the string which will show the rows of the node which conform the shortest path.
         *
         * The string has the following format: [Node(1).row, Node(2).row, Node(3).row..., Node(n).row],
         * where n is the number of columns in the maze.
         * */
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

    /**
     * Called when user presses the Add FloatingButton.
     *
     * When clicked, the current status of the widgets is firstly reset to the initial UI configuration
     * */
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

    /**
     * Shows dialog to enter the size of the bi-dimensional maze.
     *
     * Creates and shows a InputMazeSizeDialogFragment instance and sets it a OnMazeSizeEnteredListener
     * which is called once the user finishes to enter the maze size.
     * */
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
                if (columnSize < 1) {
                    columnSize = 1;
                } else if (columnSize > 100) {
                    columnSize = 100;
                }

                // Update the current status of the widgets
                mazeEditText.setEnabled(true);
                mazeEditText.setFocusableInTouchMode(true);
                numericKeyboardView.showCustomKeyboard(mazeEditText);
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
                maze = new String[rowSize][columnSize];
            }
        }
    }

    /**
     * Called every time the representing string of the entered maze changes by the user.
     *
     * Each time the string changes, it looks for commas (',') and negative signs ('-') in order to determine
     * if the string should continue adding numbers in the same row or should it do it in the next one.
     *
     * When a coma is typed, the last entered number before the comma is appended in the maze
     * bi-dimensional array.
     *
     * When a minus sign is typed, the number to be typed after the sign will be negative.
     *
     * Once the total number of rows is reached, the maze is ready to calculate its shortest path.
     * */
    private class MazeEdiTextOnKeyListener implements View.OnKeyListener {

        // Negative sign control variable
        private boolean negative = false;

        // Rows and Columns Control variables
        private int commasCount = 0;
        private int entersCount = 0;

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                if (mazeEditText.length() > 0) {

                    char lastChar = mazeEditText.getText().charAt(mazeEditText.length() - 1);
                    if (lastChar == ' ') {
                        mazeEditText.getText().delete(mazeEditText.getText().toString().lastIndexOf(","), mazeEditText.length());
                        commasCount--;

                        // Set the sign of the current number
                        int lastNumber = Integer.parseInt(getLastNumberInMaze());
                        negative = lastNumber < 0;

                        // Update the current maze insertion position
                        positionTextView.setText("[" + entersCount + ", " + commasCount + "]");

                        // The KeyEvent is consumed
                        return true;

                    } else if (lastChar == '\n') {
                        mazeEditText.getText().delete(mazeEditText.getText().toString().lastIndexOf("\n"), mazeEditText.length());
                        entersCount--;
                        commasCount = columnSize - 1;

                        // Set the sign of the current number
                        int lastNumber = Integer.parseInt(getLastNumberInMaze());
                        negative = lastNumber < 0;

                        // Update the current maze insertion position
                        positionTextView.setText("[" + entersCount + ", " + commasCount + "]");

                        // The KeyEvent is consumed
                        return true;

                    } else if (lastChar == '-') {

                        negative = false;

                        // The KeyEvent is not consumed
                        return false;
                    }
                }

            } else if (keyCode == KeyEvent.KEYCODE_MINUS && keyEvent.getAction() == KeyEvent.ACTION_DOWN) { // If the typed character is a minus sign and it was not already typed

                // Invert the sign of the number being currently typed
                negative = !negative;

                // Calculate the beginning of the number being currently typed
                String numberString = mazeEditText.getText().toString().substring(mazeEditText.getText().toString().lastIndexOf("\n") + 1); // Find the beginning of the last row
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
                    mazeEditText.getText().delete(mazeEditText.getText().toString().lastIndexOf(" ") + 1, mazeEditText.length());
                } else {
                    mazeEditText.getText().delete(mazeEditText.getText().toString().lastIndexOf("\n") + 1, mazeEditText.length());
                }
                mazeEditText.append(numberString);

                // The KeyEvent is consumed
                return true;

            } else if (keyCode == KeyEvent.KEYCODE_COMMA && keyEvent.getAction() == KeyEvent.ACTION_DOWN) { // If the typed character is a comma

                commasCount++;
                if (commasCount < columnSize) { // Checks if the new number is still part of the current row

                    // Take the last entered number and add it in maze
                    boolean elementAdded = appendElementInMaze();
                    if (elementAdded) { // If the number was successfully added to the maze

                        // Reset negative sign control variable to false
                        negative = false;

                        // Add a space after the comma in the number sequence
                        mazeEditText.append(", ");
                    }

                } else { // Else, the new number is part of the next row

                    // Take the last entered number and add it in maze
                    boolean elementAdded = appendElementInMaze();
                    if (elementAdded) { // If the number was successfully added to the maze

                        // Reset negative sign control variable to false
                        negative = false;

                        entersCount++;
                        if (entersCount < rowSize) { // Checks if it's still possible to add more rows

                            // Add an enter at the end of the sequence
                            mazeEditText.append("\n");

                            // Reset variables for row control
                            commasCount = 0;

                        } else { // Else, the maze is ready

                            // Reset variables for both row and column control
                            commasCount = 0;
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

                // Update the current maze insertion position
                positionTextView.setText("[" + entersCount + ", " + commasCount + "]");

                // The KeyEvent is consumed
                return true;
            }

            // The KeyEvent is not consumed
            return false;
        }

        /**
         * Appends the last typed element of the sequence in the maze.
         *
         * The position of the element in the maze will be calculated by the following:
         * 1. The row index is determined by the count of new line characters ('\n') in the sequence string.
         * 2. The column index is determined by the count of commas (',') from the last new line to the end of the sequence string.
         * */
        private boolean appendElementInMaze() {

            String elementString = getLastNumberInMaze();
            if (elementString.equals(",") || elementString.equals("-") || elementString.equals("")) {
                commasCount--;
                return false;
            }

            // Insert the number in the maze bi-dimensional array
            maze[entersCount][commasCount - 1] = elementString;

            return true;
        }

        /**
         * Returns the last typed number by the user.
         *
         * The location of the number in the maze is defined by the last position of a
         * ' ' or a '\n' character, until the end of the string.
         * */
        private String getLastNumberInMaze() {

            String lastNumber;

            if (mazeEditText.getText().toString().lastIndexOf(' ') > mazeEditText.getText().toString().lastIndexOf('\n')) {
                lastNumber = mazeEditText.getText().toString().substring(mazeEditText.getText().toString().lastIndexOf(' ') + 1);

            } else {
                lastNumber = mazeEditText.getText().toString().substring(mazeEditText.getText().toString().lastIndexOf('\n') + 1);
            }

            return lastNumber;
        }
    }

    /**
     * Called when mazeEditText gains focus.
     *
     * When mazeEditText gains focus, the custom numeric keyboard is shown.
     * */
    private class MazeEditTextOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                numericKeyboardView.showCustomKeyboard(view);
            } else {
                numericKeyboardView.hideCustomKeyboard();
            }
        }
    }

    /**
     * Called when mazeEditText is clicked.
     *
     * When mazeEditText id clicked, the custom numeric keyboard is shown.
     * */
    private class MazeEditTextOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            numericKeyboardView.showCustomKeyboard(view);
        }
    }

    @Override
    public void onBackPressed() {
        if(numericKeyboardView.getVisibility() == View.VISIBLE) {
            numericKeyboardView.hideCustomKeyboard();
        }  else {
            this.finish();
        }
    }
}
