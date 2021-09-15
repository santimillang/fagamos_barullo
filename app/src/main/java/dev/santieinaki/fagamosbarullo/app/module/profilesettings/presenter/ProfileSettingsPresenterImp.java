package dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter;

import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;

public class ProfileSettingsPresenterImp implements ProfileSettingsPresenter {

    private UserService mUserService = new UserServiceImp();
    private ProfileSettingsView mView;
    private User mUser;

    public ProfileSettingsPresenterImp(ProfileSettingsView mView) {

        this.mView = mView;
    }

    @Override
    public void initFlow() {

        new GetUserInfo().execute();
    }

    @Override
    public boolean instrumentoClicked(String instrumento) {

        if (mUser == null) {
            return false;
        }

        if (mUser.getInstrumento().indexOf(instrumento) != -1) {

            mUser.removeInstrumento(instrumento);
            return false;
        }
        mUser.addInstrumento(instrumento);
        return true;
    }

    @Override
    public void saveInfo(User user) {

        mUser = user;
        new SaveUserInfo().execute();
    }

    @Override
    public void changeDescripcion(String desciption) {

        if (mUser == null) {
            return;
        }
        mUser.setDescripcion(desciption);
    }

    @Override
    public void deletePhoto(User user) {

        new DeletePhoto().execute(user);
        mUser = user;
    }

    private class DeletePhoto extends AsyncTask<User, Void, Void> {

        // seria mejor hacer comprobaciones...
        @Override
        protected Void doInBackground(User... users) {

            mUserService.saveUserInfo(users[0]);
            return null;
        }
    }

    private class GetUserInfo extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... voids) {

            return mUserService.getUserInfo();
        }

        @Override
        protected void onPostExecute(User user) {

            if (user == null) {
                mView.showError();
                return;
            }
            mUser = user;
            mView.setUserInfo(mUser);
        }
    }

    private class SaveUserInfo extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            mUserService.saveUserInfo(mUser);
            return 1;
        }
    }
}
