package com.example.chenzhuowei.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0: red 1: yellow 2: empty
    int playerIndex = 0;
    int[] positionArray = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winResultPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    boolean gameActive = true;
    TextView txtResult;
    Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = findViewById(R.id.textView);
        btnPlayAgain = findViewById(R.id.button);
        initGameState();
    }

    public void initGameState() {
        gameActive = true;
        playerIndex = 0;
        for (int i = 0; i < positionArray.length; i++) {
            positionArray[i] = 2;
        }
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
        txtResult.setVisibility(View.INVISIBLE);
        btnPlayAgain.setVisibility(View.INVISIBLE);
    }

    public void onDropIn(View view) {
        ImageView imageView = (ImageView) view;
        int imageTag = Integer.parseInt(imageView.getTag().toString());
        if (positionArray[imageTag] == 2 && gameActive) {
            positionArray[imageTag] = playerIndex;
            if (playerIndex == 0) {
                playerIndex = 1;
                imageView.setImageResource(R.drawable.red);
            } else {
                playerIndex = 0;
                imageView.setImageResource(R.drawable.yellow);
            }
            imageView.setTranslationY(-1500);
            imageView.animate().translationYBy(1500).setDuration(300);

            if (checkSomeoneHasWin()) {
                String message = "";
                if (playerIndex == 0) {
                    message = "Player yellow has win !";
                } else {
                    message = "Player red has win !";
                }
                gameActive = false;
                txtResult.setVisibility(View.VISIBLE);
                txtResult.setText(message);
                btnPlayAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean checkSomeoneHasWin() {
        for (int[] result : winResultPosition) {
            if (positionArray[result[0]] == positionArray[result[1]] &&
                    positionArray[result[0]] == positionArray[result[2]] &&
                    positionArray[result[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    public void onBtnPlayAgain(View view) {
        initGameState();
    }
}
