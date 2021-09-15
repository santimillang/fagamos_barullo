package dev.santieinaki.fagamosbarullo.app.module.splashscreen;

import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.main.MainActivity;
import dev.santieinaki.fagamosbarullo.app.module.register.Register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent;
                if (new AuthServiceImp().isLoggedIn() != null) {

                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {

                    intent = new Intent(SplashScreenActivity.this, Register.class);
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 2000);
    }
}