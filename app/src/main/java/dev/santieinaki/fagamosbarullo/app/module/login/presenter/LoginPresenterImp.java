package dev.santieinaki.fagamosbarullo.app.module.login.presenter;

import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthService;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceMock;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView mView;
    private AuthService mAuthService;

    public LoginPresenterImp(LoginView view, boolean isTesting) {

        mView = view;

        if (isTesting) {
            mAuthService = new AuthServiceMock();
        } else {
            mAuthService = new AuthServiceImp();
        }
    }

    @Override
    public void initFlow() {

    }

    @Override
    public void onLoginClicked(String email,
                               String password) {

        mView.showProgressBar();
        new AuthTask().execute(email, password);
    }

    @Override
    public void onRegisterClicked() {

        mView.navigateToRegister();
    }

    private class AuthTask extends AsyncTask<String, Void, Auth> {

        @Override
        protected Auth doInBackground(String... strings) {

            return mAuthService.login(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Auth auth) {

            mView.hideProgressBar();
            if (auth == null) {
                mView.showError();
                return;
            }

            mView.navigateToMain();
        }
    }
}
