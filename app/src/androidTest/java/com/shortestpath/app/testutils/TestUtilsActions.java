package com.shortestpath.app.testutils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Utility set of convenient custom actions to modified the state of the views during instrumented
 * tests.
 */
public class TestUtilsActions {

    /**
     * ViewAction to modify the enable/disable state of any view.
     * */
    public static ViewAction setEnabled(final boolean enabled) {

        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "set enabled";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                view.setEnabled(enabled);
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    /**
     * ViewAction to modify the focusableInTouchMode state of any view.
     * */
    public static ViewAction setFocusableInTouchMode(final boolean focusable) {

        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "set focusable in touch mode";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                view.setFocusableInTouchMode(focusable);
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    /**
     * ViewAction to modify the requestFocus state of any view.
     * */
    public static ViewAction requestFocus(final boolean focus) {

        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "request focus";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                view.requestFocus();
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

}
