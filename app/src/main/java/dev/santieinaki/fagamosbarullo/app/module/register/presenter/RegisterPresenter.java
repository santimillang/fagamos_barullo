package dev.santieinaki.fagamosbarullo.app.module.register.presenter;

public interface RegisterPresenter {

    public void initFlow();

    public void onRegisterClicked(String fullName,
                                  String email,
                                  String password,
                                  String phone);

    public void onLoginClicked();
}
