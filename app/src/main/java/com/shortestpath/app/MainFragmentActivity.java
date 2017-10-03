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
import com.shortestpath.app.utils.ElementsSequenceMaker;

/**
 * The presentation activity of the app.
 *
 * Point of interaction with user to get a maze and show the shortest path.
 * */
public class MainFragmentActivity extends FragmentActivity {

    // Maze size variables
    private int rowSize = 0;
    private int columnSize = 0;

    // The class which builds up the sequence of maze elements with the user's input
    private ElementsSequenceMaker elementsSequenceMaker;

    // The maze parser which calculates the shortest path
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

    /** Constructor */
    public MainFragmentActivity() {

        // Creates a new instance of the MazeParser class to calculate the shortest path
        mazeParser = new MazeParser();

        // Sets an OnShortestPathFoundListener which will be called to inform that the
        // when the shortest path calculation process has finished
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
    }

    /** Getters and setters */

    /**
     * Setter for Maze Size.
     *
     * Sets the number of rows and columns to determine the size of the maze.
     *
     * Once a maze size is given, a new instance of ElementsSequenceMaker is created.
     * */
    public void setMazeSize(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;

        CharSequence cs = mazeEditText.getText();
        elementsSequenceMaker = new ElementsSequenceMaker(mazeEditText, rowSize, columnSize);
        elementsSequenceMaker.setOnSequencePositionChangedListener(new ElementsSequenceMakerOnSequencePositionChangedListener());
    }

    public ElementsSequenceMaker getElementsSequenceMaker() {
        return elementsSequenceMaker;
    }

    /** Activity Lifecycle */

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
        mazeEditText.setOnKeyListener(new MazeEditTextOnKeyListener());
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
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();

        // Hide keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(mazeEditText.getWindowToken(), 0);
    }

    /**
     * Called any time a new element was added or deleted from the sequence of element.
     * */
    private class ElementsSequenceMakerOnSequencePositionChangedListener implements ElementsSequenceMaker.OnSequencePositionChangedListener {

        @Override
        public void onSequencePositionChanged(int newRow, int newColumn) {

            if (newRow < rowSize || newColumn < columnSize) { // If there are still elements to be inserted

                // Show the new position where the next element will be inserted
                positionTextView.setText("[" + newRow + ", " + newColumn + "]");

            } else { // If there are no more elements to be inserted

                // Update the status of the widgets
                hintTextView.setText(getString(R.string.press_start_button));
                positionTextView.setVisibility(View.INVISIBLE);
                mazeEditText.setEnabled(false);
                startFloatingButton.setEnabled(true);
            }
        }
    }

    /***
     * Creates the mazeArray containing all the typed elements by the user.
     *
     * First, mazeString is split in strings where each one represents a row in the maze array.
     * Then, each string representing a row is split in strings where each one represents an element
     * and this is stored in mazeArray.
     */
    private String[][] createMazeArray(String mazeString) {

        // Eliminate aesthetic spaces between elements
        mazeString = mazeString.replace(" ", "");

        String[][] maze = new String[rowSize][columnSize];

        String[] rows = mazeString.split("\n");
        for (int i = 0; i < rows.length; i++) {

            String[] elements = rows[i].split(",");
            for (int j = 0; j < elements.length; j++) {
                maze[i][j] = elements[j];
            }
        }

        return maze;
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
     * Called when user clicks the Start Floating Button.
     *
     * When clicked, the calculation process to find the shortest path is started.
     */
    private class StartFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Hide custom keyboard
            numericKeyboardView.hideCustomKeyboard();

            // Create the mazeArray from the typedString
            String typedString = mazeEditText.getText().toString();
            String[][] mazeArray = createMazeArray(typedString);

            // Start the calculation process to find the shortest path
            mazeParser.findShortestPath(mazeArray);
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

            // Show InputMazeSizeDialogFragment to enter the size of a new maze
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
        inputMazeSizeDialogFragment.setOnMazeSizeEnteredListener(new InputMazeSizeDialogFragmentOnMazeSizeEnteredListener());
        inputMazeSizeDialogFragment.show(getFragmentManager(), "inputMazeSizeDialogFragment");
    }

    /**
     * Called after user entered the number of rows and columns for the maze in the dialog fragment.
     *
     * If rowSize < 1, it will be adjusted to 1. If rowSize > 10, it will be adjusted to 10
     * If columnSize < 5, it will be adjusted to 5. if columnSize > 100, it will be adjusted to 100
     * */
    private class InputMazeSizeDialogFragmentOnMazeSizeEnteredListener implements InputMazeSizeDialogFragment.OnMazeSizeEnteredListener {

        @Override
        public void onMazeSizeEntered(int rowSize, int columnSize) {

            if (rowSize > 0 && columnSize > 0) {

                // Update the current status of the widgets
                mazeEditText.setEnabled(true);
                mazeEditText.setFocusableInTouchMode(true);
                numericKeyboardView.showKeyboard(mazeEditText);
                hintTextView.setText(getString(R.string.insert_number_for_position_label));
                positionTextView.setVisibility(View.VISIBLE);
                addFloatingButton.setEnabled(false);
                startFloatingButton.setVisibility(View.VISIBLE);
                startFloatingButton.setEnabled(false);
                mazeSizeLabelTextView.setVisibility(View.VISIBLE);
                mazeSizeTextView.setText(rowSize + "x" + columnSize);
                mazeSizeTextView.setVisibility(View.VISIBLE);

                // Keep the size of the maze in class field
                MainFragmentActivity.this.setMazeSize(rowSize, columnSize);
            }
        }
    }

    /**
     * Called every time a new element is entered in mazeEditText by the user.
     * */
    private class MazeEditTextOnKeyListener implements View.OnKeyListener {

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

            boolean consumed = false;

            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                consumed = elementsSequenceMaker.consumeKeyCode(keyCode);
            }

            return consumed;
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
                numericKeyboardView.showKeyboard(view);
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
            numericKeyboardView.showKeyboard(view);
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
