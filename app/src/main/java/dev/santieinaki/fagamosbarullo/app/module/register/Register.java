package dev.santieinaki.fagamosbarullo.app.module.register;

import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.app.module.main.MainActivity;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.login.Login;
import dev.santieinaki.fagamosbarullo.app.module.register.presenter.RegisterPresenter;
import dev.santieinaki.fagamosbarullo.app.module.register.presenter.RegisterPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.register.presenter.RegisterView;

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

public class Register extends BaseActivity implements RegisterView {

    public static final String TEST_TAG = "TESTING_TAG";

    @BindView(R.id.fullName)
    EditText mFullName;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.phone)
    EditText mPhone;

    @BindView(R.id.registerBtn)
    Button mRegisterBtn;

    @BindView(R.id.login)
    TextView mLoginBtn;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mPresenter = new RegisterPresenterImp(this, extras != null && extras.getBoolean(TEST_TAG));
        mPresenter.initFlow();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                register();
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.onLoginClicked();
            }
        });
    }

    private void register() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String fullName = mFullName.getText().toString().trim();
        String phoneNumber = mPhone.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            String fullNameRequired = getResources().getString(R.string.full_name_required);
            mFullName.setError(fullNameRequired);
            return;
        }
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
        if (password.length() < 6) {
            String passwordLength = getResources().getString(R.string.password_length);
            mPassword.setError(passwordLength);
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            String phoneNumberRequired = getResources().getString(R.string.phone_number_required);
            mPhone.setError(phoneNumberRequired);
            return;
        }

        mPresenter.onRegisterClicked(fullName, email, password, phoneNumber);
    }

    @Override
    public void navigateToMain() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigateToLogin() {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void showError() {

        String err = getResources().getString(R.string.error);
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

        mProgressBar.setVisibility(View.VISIBLE);
        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgressBar() {

        mProgressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}