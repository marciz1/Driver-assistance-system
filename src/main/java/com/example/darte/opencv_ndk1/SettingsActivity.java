package com.example.darte.opencv_ndk1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcin on 08.04.18.
 */

public class SettingsActivity extends Activity {

    private Button button;
    private int sensity;
    private int alarmLength;


    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final int[] cascades = new int[] { R.raw.haar_closed_eye, R.raw.lbpcascade_frontalface_improved, R.raw.haarcascade_eye_tree_eyeglasses};
        final String[] cascadesString = new String[] { "haar_closed_eye.xml", "lbpcascade_frontalface_improved.xml", "haarcascade_eye_tree_eyeglasses.xml"};
        for (int i = 0; i < cascades.length; i++) {
            try {
                String path = Environment.getExternalStorageDirectory() + "/Cascades";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    String strCascadeName = cascadesString[i];
                    CopyRAWtoSDCard(cascades[i], path + File.separator + strCascadeName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        SeekBar sensitySeekBar = findViewById(R.id.sensitySeekBar);
        sensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sensity = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar timeSeekBar = findViewById(R.id.timeSeekBar);
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alarmLength = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.putExtra("sensity", sensity);
            intent.putExtra("sensityMax", sensitySeekBar.getMax());
            intent.putExtra("alarmLength", alarmLength);
            startActivity(intent);
        });

    }
}
