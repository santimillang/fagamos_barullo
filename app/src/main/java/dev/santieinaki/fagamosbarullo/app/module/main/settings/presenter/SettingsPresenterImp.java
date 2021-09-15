package dev.santieinaki.fagamosbarullo.app.module.main.settings.presenter;

public class SettingsPresenterImp implements SettingsPresenter {

    private SettingsView mView;

    public SettingsPresenterImp(SettingsView view) {

        mView = view;
    }

    @Override
    public void initFlow() {

    }

    @Override
    public void logoutClicked() {

        mView.logout();
    }

    @Override
    public void profileClicked() {

        mView.navigateToProfileSettings();
    }

    @Override
    public void cuentaClicked() {

        mView.navigateToAccountSettings();
    }

    @Override
    public void licenzasClicked() {

        mView.navigateToLicenses();
    }
}
