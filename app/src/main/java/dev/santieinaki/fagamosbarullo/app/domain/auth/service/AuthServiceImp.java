package dev.santieinaki.fagamosbarullo.app.domain.auth.service;

import dev.santieinaki.fagamosbarullo.app.data.auth.AuthDatasourceImp;
import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.datasource.AuthDatasource;

public class AuthServiceImp implements AuthService{

    private AuthDatasource mDatasource = new AuthDatasourceImp();

    @Override
    public Auth login(String email,
                      String password) {

        return mDatasource.login(email, password);
    }

    @Override
    public Auth register(String email,
                         String password) {

        return mDatasource.register(email, password);
    }

    @Override
    public Auth isLoggedIn() {

        return mDatasource.isLoggedIn();
    }

    @Override
    public boolean changeEmail(String newEmail) {

        return mDatasource.changeEmail(newEmail);
    }

    @Override
    public boolean changePassword(String newPassword) {

        return mDatasource.changePassword(newPassword);
    }
}
