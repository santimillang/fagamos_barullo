package dev.santieinaki.fagamosbarullo.app.module.accountsettings;

import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.accountsettings.presenter.AccountSettingsPresenter;
import dev.santieinaki.fagamosbarullo.app.module.accountsettings.presenter.AccountSettingsPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.accountsettings.presenter.AccountSettingsView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountSettings extends BaseActivity implements AccountSettingsView {

    @BindView(R.id.email_edit)
    EditText mEmail;
    @BindView(R.id.password_edit)
    EditText mPasswordOne;
    @BindView(R.id.password_edit_2)
    EditText mPasswordTwo;
    @BindView(R.id.bt_email)
    Button mEmailButton;
    @BindView(R.id.bt_password)
    Button mPasswordButton;

    private AccountSettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mPresenter = new AccountSettingsPresenterImp(this);

        initView();
    }

    @Override
    public void showMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void freeze() {

        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void unfreeze() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void initView() {

        mEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    showMessage("O email non pode estar baleiro.");
                    return;
                }

                mPresenter.updateEmail(mEmail.getText().toString().trim());
            }
        });

        mPasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String passwordOne = mPasswordOne.getText().toString().trim();
                String passwordTwo = mPasswordTwo.getText().toString().trim();

                if (passwordOne.isEmpty() || passwordTwo.isEmpty()) {
                    showMessage("Os contrasinais non poden estar baleiros.");
                    return;
                }

                if (!passwordOne.equals(passwordTwo)) {
                    showMessage("Os contrasinais deben de ser iguais.");
                    return;
                }
                mPresenter.updatePassword(passwordOne);
            }
        });
    }
}