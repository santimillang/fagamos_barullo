package dev.santieinaki.fagamosbarullo.app.module.accountsettings.presenter;

import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthService;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;

public class AccountSettingsPresenterImp implements AccountSettingsPresenter {

    private AuthService mService;
    private AccountSettingsView mView;

    public AccountSettingsPresenterImp(AccountSettingsView view) {

        mView = view;
        mService = new AuthServiceImp();
    }

    @Override
    public void updateEmail(String email) {

        new UpdateEmail().execute(email);
        mView.freeze();
    }

    @Override
    public void updatePassword(String password) {

        new UpdatePassword().execute(password);
        mView.freeze();
    }

    private class UpdateEmail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return mService.changeEmail(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean) {
                mView.showMessage("Email actualizado correctamente.");
            } else {
                mView.showMessage("Ocurrio un erro ao actualizar o email.");
            }
            mView.unfreeze();
        }
    }

    private class UpdatePassword extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return mService.changePassword(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean) {
                mView.showMessage("Contrasinal actualizado correctamente.");
            } else {
                mView.showMessage("Ocurrio un erro ao actualizar o contrasinal.");
            }
            mView.unfreeze();
        }
    }
}
