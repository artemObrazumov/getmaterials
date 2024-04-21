package com.borsh_team.getmaterials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        root = findViewById(R.id.root);
        root.animate().alpha(1f).translationY(0f).setDuration(500L);
        Handler handler = new Handler();
        handler.postDelayed(() -> root.animate().alpha(0f).translationY(-28f).setDuration(500L)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Intent intent = new Intent(getApplicationContext(), WorkSelectActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {}
                    @Override
                    public void onAnimationRepeat(Animator animator) {}
                }), 2500);

    }
}