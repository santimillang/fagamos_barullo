package dev.santieinaki.fagamosbarullo.app.module.login;

import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.login.presenter.LoginPresenter;
import dev.santieinaki.fagamosbarullo.app.module.login.presenter.LoginPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.login.presenter.LoginView;
import dev.santieinaki.fagamosbarullo.app.module.main.MainActivity;
import dev.santieinaki.fagamosbarullo.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends BaseActivity implements LoginView {

    public static final String TEST_TAG = "TESTING_TAG";

    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.loginBtn)
    Button mLoginBtn;

    @BindView(R.id.registerBtn)
    TextView mCreateBtn;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mPresenter = new LoginPresenterImp(this, extras != null && extras.getBoolean(TEST_TAG));
        mPresenter.initFlow();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                login();
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mPresenter.onRegisterClicked();
            }
        });
    }

    private void login() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            String emailRequired = getResources().getString(R.string.email_required);
            mEmail.setError(emailRequired);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            String passwordRequired = getResources().getString(R.string.password_required);
            mPassword.setError(passwordRequired);
            return;
        }

        mPresenter.onLoginClicked(email, password);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void navigateToRegister() {

        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void navigateToMain() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showError() {

        String err = getResources().getString(R.string.error);
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgressBar() {

        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}