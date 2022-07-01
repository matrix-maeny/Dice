package com.matrix_maeny.dice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView diceImage;
    private AppCompatButton rollBtn;
    private TextView numberText;
    int photoNumber = 0;
    final Handler handler = new Handler();

    MediaPlayer player = null;

    private final int[] diceFaceIds = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImage = findViewById(R.id.diceImage);
        rollBtn = findViewById(R.id.rollBtn);
        numberText = findViewById(R.id.numberText);
        numberText.setVisibility(View.INVISIBLE);

    }

    MediaPlayer.OnCompletionListener playerListener = MediaPlayer::release;


    public void Roll(View view) {
        playButtonSound();
        photoNumber = 0;

        handler.post(runnable);

    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            if (photoNumber <= 5) {
                diceImage.setImageResource(diceFaceIds[photoNumber]);
                photoNumber++;
                handler.postDelayed(this, 80);
            } else {
                setTheImage();
            }
        }
    };

    private void setTheImage() {
        Random random = new Random();
        int diceNumber = random.nextInt(6);

        diceImage.setImageResource(diceFaceIds[diceNumber]);
        String txt = "You Got : " + (diceNumber + 1);
        numberText.setText(txt);
        numberText.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        startActivity(new Intent(MainActivity.this, AboutActivity.class));
        return super.onOptionsItemSelected(item);
    }


    private void playButtonSound() {
        stopSound();

        player = MediaPlayer.create(MainActivity.this, R.raw.select_click2);
        player.setOnCompletionListener(playerListener);
        player.setLooping(false);
        player.start();
    }

    private void stopSound() {
        if (player != null) {
            try {
                player.stop();
            } catch (Exception ignored) {
            } finally {
                player.release();
            }

        }
    }

}