package com.example.assignment_one_n.Utilities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_one_n.Logic.GameManager;
import com.example.assignment_one_n.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int LIFE = 3;
    private static final long DELAY = 800L;
    private ExtendedFloatingActionButton main_FAB_right;
    private ExtendedFloatingActionButton main_FAB_left;
    private AppCompatImageView[] main_IMG_hearts;
    private AppCompatImageView[][] grid_obstacles;
    private AppCompatImageView[] grid_dodge;
    private GameManager gameManager;
    private Timer timer;
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignalManager.init(this);
        gameManager = new GameManager(LIFE);
        timerOn = false;

        findViews();
        initViews();
        startTimer();
    }


    private void updateGameUi() {

        gameManager.updateGameLogic();

        for (int i = 0; i < gameManager.getObstacleBoard().length; i++) {
            for (int j = 0; j < gameManager.getObstacleBoard()[0].length; j++) {
                if(gameManager.getObstacleBoard()[i][j] == 1)
                    grid_obstacles[i][j].setVisibility(View.VISIBLE);
                else
                    grid_obstacles[i][j].setVisibility(View.INVISIBLE);
            }

        }

        for (int col = 0; col < gameManager.getDodgeBoard().length; col++) {
            if (gameManager.collisionDetection(col)) {
                SignalManager.getInstance().toast("Asteroid Hit You! 😵‍💫😵‍💫");
                SignalManager.getInstance().vibrate(800);
                if (!gameManager.isGameLost()){
                    main_IMG_hearts[gameManager.getHitNum()].setVisibility(View.INVISIBLE);
                    gameManager.updateHits();
                }else
                    SignalManager.getInstance().toast("You've Lost! \n You Can Keep Playing..");
            }
        }

    }


    private void startTimer() {
        if(!timerOn){
            timer = new Timer();
            timerOn = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateGameUi());
                }
            },0L,DELAY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer != null){
            timerOn = false;
            timer.cancel();
        }
    }

    private void stopTimer() {
        timerOn = false;
        timer.cancel();
    }


    public void changeMovement(int direction) {
        gameManager.dodgeMovement(direction);
        for (int i = 0; i < grid_dodge.length; i++) {
            if(gameManager.getDodgeBoard()[i] == 1)
                grid_dodge[i].setVisibility(View.VISIBLE);
            else
                grid_dodge[i].setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {
        main_FAB_right.setOnClickListener(v -> changeMovement(1));
        main_FAB_left.setOnClickListener(v -> changeMovement(-1));

        for (int i = 0; i < gameManager.getDodgeBoard().length; i++) {
            if(gameManager.getDodgeBoard()[i] == 1)
                grid_dodge[i].setVisibility(View.VISIBLE);
            else
                grid_dodge[i].setVisibility(View.INVISIBLE);
        }

        for (AppCompatImageView[] rows : grid_obstacles)
            for (AppCompatImageView element : rows)
                element.setVisibility(View.INVISIBLE);

    }

    private void findViews() {
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        grid_obstacles = new AppCompatImageView[][]{
                {findViewById(R.id.grid_fall11),
                        findViewById(R.id.grid_fall12),
                        findViewById(R.id.grid_fall13)},

                {findViewById(R.id.grid_fall21),
                        findViewById(R.id.grid_fall22),
                        findViewById(R.id.grid_fall23)},

                {findViewById(R.id.grid_fall31),
                        findViewById(R.id.grid_fall32),
                        findViewById(R.id.grid_fall33)},

                {findViewById(R.id.grid_fall41),
                        findViewById(R.id.grid_fall42),
                        findViewById(R.id.grid_fall43)},

                {findViewById(R.id.grid_fall51),
                        findViewById(R.id.grid_fall52),
                        findViewById(R.id.grid_fall53)
                },

                {findViewById(R.id.grid_fall61),
                        findViewById(R.id.grid_fall62),
                        findViewById(R.id.grid_fall63)},

                {findViewById(R.id.grid_fall71),
                        findViewById(R.id.grid_fall72),
                        findViewById(R.id.grid_fall73)},

                {findViewById(R.id.grid_fall81),
                        findViewById(R.id.grid_fall82),
                        findViewById(R.id.grid_fall83)}

        };

        grid_dodge = new AppCompatImageView[]{
                findViewById(R.id.grid_dodge1),
                findViewById(R.id.grid_dodge2),
                findViewById(R.id.grid_dodge3)
        };
    }
}