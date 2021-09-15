package dev.santieinaki.fagamosbarullo.app.module.login.presenter;

public interface LoginPresenter {

    public void initFlow();

    public void onLoginClicked(String email,
                               String password);

    public void onRegisterClicked();

}
