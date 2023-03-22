package org.baricoincore.qt;

import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;

import org.qtproject.qt5.android.bindings.QtActivity;

import java.io.File;

public class BaricoinQtActivity extends QtActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        final File baricoinDir = new File(getFilesDir().getAbsolutePath() + "/.baricoin");
        if (!baricoinDir.exists()) {
            baricoinDir.mkdir();
        }

        super.onCreate(savedInstanceState);
    }
}
