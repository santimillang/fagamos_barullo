package dev.santieinaki.fagamosbarullo.app.module.login.presenter;

public interface LoginView {

    public void navigateToRegister();

    public void navigateToMain();

    public void showError();

    public void showProgressBar();

    public void hideProgressBar();
}
