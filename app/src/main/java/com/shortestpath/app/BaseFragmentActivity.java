package com.shortestpath.app;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shortestpath.R;

public class BaseFragmentActivity extends FragmentActivity {

    protected KeyboardView keyboardView;

    private ViewGroup rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ViewGroup viewGroup = this.findViewById(android.R.id.content);
        View view = viewGroup.getChildAt(0);

        rootLayout = (ViewGroup) view;

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        keyboardView = (KeyboardView) inflater.inflate(R.layout.keyboard_layout, null);

        keyboardView.setPreviewEnabled(false);
        Keyboard keyboard = new Keyboard(this, R.xml.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(new KeyboardActionListener());

        rootLayout.addView(keyboardView);

    }

    public class KeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

        @Override
        public void onPress(int primaryCode) { }

        @Override
        public void onRelease(int primaryCode) { }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            long eventTime = System.currentTimeMillis();
            KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
            dispatchKeyEvent(event);
            if(primaryCode == KeyEvent.KEYCODE_NUMPAD_EQUALS){
//                displayCalculatedResult();
            }
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
    };

    public void registerEditText(EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                CharSequence mS = editable.subSequence(0, editable.length());
                if (!mS.toString().equals("") || mS.toString() != null) {
                    if (editable.length() > 0 && mS.toString().contains("=")) {
                        editable.replace(editable.length() - 1, editable.length(), "");
                    }
                }
            }
        });

        // Make the custom keyboard appear
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showCustomKeyboard(view);
                } else {
                    hideCustomKeyboard();
                }
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showCustomKeyboard(view);
            }
        });
    }

    public void hideCustomKeyboard() {
        keyboardView.setVisibility(View.GONE);
        keyboardView.setEnabled(false);
    }

    public void showCustomKeyboard(View view) {
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);

        if(view != null) {
            ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    @Override public void onBackPressed() {
        if(isCustomKeyboardVisible()) {
            hideCustomKeyboard();
        }  else {
            this.finish();
        }
    }
}
