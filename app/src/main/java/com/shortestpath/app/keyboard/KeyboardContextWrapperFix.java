package com.shortestpath.app.keyboard;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Fixes a bug in Android API 14+ where the AudioManager service should be deactivated during the
 * layout editing.
 * */
public class KeyboardContextWrapperFix extends ContextWrapper {
    private boolean editMode;

    public KeyboardContextWrapperFix(Context context, boolean editMode) {
        super(context);
        this.editMode = editMode;
    }

    public Object getSystemService(String name) {
        if (editMode && Context.AUDIO_SERVICE.equals(name)) {
            return null;
        }
        return super.getSystemService(name);
    }
}
