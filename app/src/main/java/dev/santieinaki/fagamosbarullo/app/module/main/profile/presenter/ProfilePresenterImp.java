package dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter;

import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;

public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView mView;
    private AsyncTask<User, Void, User> mTask;
    private UserService mUserService = new UserServiceImp();

    public ProfilePresenterImp(ProfileView profileView) {

        mView = profileView;
    }

    @Override
    public void initFlow(User user) {

        mTask = new GetUserInfo().execute(user);
    }

    @Override
    public void follow(User user) {

        new FollowUser().execute(user);
    }

    @Override
    public void destroy() {

        mTask.cancel(true);
    }

    private class FollowUser extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {

            return mUserService.follow(users[0]);
        }

        @Override
        protected void onPostExecute(User user) {

            if (user != null) {
                mView.updateUser(user);
            }
        }
    }

    private class GetUserInfo extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {

            return (users[0] == null ? mUserService.getUserInfo() : mUserService.getUserInfo(users[0]));
        }

        @Override
        protected void onPostExecute(User user) {

            if (isCancelled()) {
                return;
            }

            if (user == null) {
                mView.showError();
                return;
            }
            mView.setUserInfo(user);
        }
    }
}
