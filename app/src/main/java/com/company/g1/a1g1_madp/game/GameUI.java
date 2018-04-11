package com.company.g1.a1g1_madp.game;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.company.g1.a1g1_madp.R;

public class GameUI {
    private Game game;
    private Activity context;

    // UI Components
    private ConstraintLayout uiLayout;
    private ConstraintLayout pauseLayout;
    private ImageButton      pauseButton;
    private PopupWindow      pauseWindow;
    private Button           option1;
    private Button           option2;

    // UI state
    private boolean isImmersive = false;
    private boolean isPaused    = false;

    GameUI(Context _context, Game game) {
        this.context = (Activity)_context;
        this.game = game;

        uiLayout = context.findViewById(R.id.uiLayout);
        pauseButton = context.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(view -> togglePauseUi());
        pauseWindow = new PopupWindow(context);

        // Popup stuff
        pauseLayout = (ConstraintLayout) context.getLayoutInflater().inflate(R.layout.popup_pause,null);
        option1 = pauseLayout.findViewById(R.id.option1);
        option1.setOnClickListener(view -> Spaceship.FIRE_RATE = 100);
        option2 = pauseLayout.findViewById(R.id.option2);
        option2.setOnClickListener(view -> Spaceship.FIRE_RATE = 500);
        pauseWindow.setContentView(pauseLayout);

        setImmersiveUi(true);
    }

    private void setImmersiveUi(boolean immersive) {
        if(immersive) {
            uiLayout.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        else {
            uiLayout.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void togglePauseUi() {
        if (isPaused) {
            // Unpause game
            setImmersiveUi(true);
            setPausedUi(false);
            game.resume();
        }
        else {
            // Pause game
            setImmersiveUi(false);
            setPausedUi(true);
            game.pause();
        }
        // Flip state
        isPaused = !isPaused;
        isImmersive = !isImmersive;

    }

    private void setPausedUi(boolean pause) {
        if(pause) {
            pauseWindow.showAtLocation(uiLayout, Gravity.CENTER, 0, -50);
        }
        else {
            pauseWindow.dismiss();
        }
    }
}
