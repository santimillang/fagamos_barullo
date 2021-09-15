package dev.santieinaki.fagamosbarullo.app.module.register.presenter;

import android.os.AsyncTask;

import java.util.ArrayList;

import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthService;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceMock;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceMock;

public class RegisterPresenterImp implements RegisterPresenter {

    private RegisterView mView;
    private AuthService mAuthService;
    private UserService mUserService;

    public RegisterPresenterImp(RegisterView view,
                                boolean isTesting) {

        mView = view;

        if (isTesting) {
            mAuthService = new AuthServiceMock();
            mUserService = new UserServiceMock();
        } else {
            mAuthService = new AuthServiceImp();
            mUserService = new UserServiceImp();
        }
    }

    @Override
    public void initFlow() {

    }

    @Override
    public void onRegisterClicked(String fullName,
                                  String email,
                                  String password,
                                  String phone) {

        mView.showProgressBar();

        User user = new User(fullName, email, phone, new ArrayList<String>(), "", "");
        new AuthTask(user).execute(email, password);
    }

    @Override
    public void onLoginClicked() {

        mView.navigateToLogin();
    }

    private class AuthTask extends AsyncTask<String, Void, Auth> {

        private User mUser;

        AuthTask(User user) {

            mUser = user;
        }

        @Override
        protected Auth doInBackground(String... strings) {

            return mAuthService.register(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Auth auth) {

            if (auth == null) {
                mView.showError();
                mView.hideProgressBar();
                return;
            }

            mUser.setId(auth.getId());
            new UserTask().execute(mUser);
        }
    }

    private class UserTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... users) {

            return mUserService.registerUser(users[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            mView.hideProgressBar();
            if (aBoolean.booleanValue()) {
                mView.navigateToMain();
                return;
            }

            mView.showError();
        }
    }
}
