package com.shortestpath.app.keyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Custom KeyboardView
 *
 * Overrides the KeyboardView in order to fix a bug in a bug in Android API 14+ where
 * the AudioManager service should be deactivated during the layout editing:
 *
 * While editing main_fragment_activity the variable 'inEditMode' should be set to true, in order to
 * avoid the error message shown on the 'Design' tab in the layout editor.
 *
 * When build the app, this variable should be set to true in order to activate sound effects while
 * typing
 * */
public class NumericKeyboardView extends KeyboardView {

    private static boolean inEditMode = false;

    /** Constructors */
    public NumericKeyboardView(Context context, AttributeSet attrs) {
        super(new KeyboardContextWrapperFix(context, inEditMode), attrs);
    }

    public NumericKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(new KeyboardContextWrapperFix(context, inEditMode), attrs, defStyleAttr);
    }

    public NumericKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(new KeyboardContextWrapperFix(context, inEditMode), attrs, defStyleAttr, defStyleRes);
    }

    public void hideCustomKeyboard() {
        setVisibility(View.GONE);
        setEnabled(false);
    }

    public void showCustomKeyboard(View view) {
        setVisibility(View.VISIBLE);
        setEnabled(true);

        if(view != null) {
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Listens for key events on NumericKeyboardView
     *
     * Any time user presses a key, the method on key will be called in order to create a KeyEvent
     * and pass it to 'dispatchKeyEvent' which fires any key listeners.
     * */
    public class KeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

        @Override
        public void onPress(int primaryCode) { }

        @Override
        public void onRelease(int primaryCode) { }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {

            // Play sound effect while typing (only works when inEditMode = false)
            AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                float volume = 0.5f; //This will be half of the default system sound
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume);
            }

            // Create the KeyEvent with the code of the key which user has pressed
            long eventTime = System.currentTimeMillis();
            KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);

            // Dispatch the KeyEvent from the ViewGroup which contains the KeyboardView, in order to follow the focus hierarchy until find the EditText
            ViewGroup viewGroup = (ViewGroup) getParent();
            viewGroup.dispatchKeyEvent(event);
        }

        @Override
        public void onText(CharSequence text) { }

        @Override
        public void swipeLeft() { }

        @Override
        public void swipeRight() { }

        @Override
        public void swipeDown() { }

        @Override
        public void swipeUp() { }
    }
}
