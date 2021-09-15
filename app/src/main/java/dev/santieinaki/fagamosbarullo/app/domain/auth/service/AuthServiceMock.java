package dev.santieinaki.fagamosbarullo.app.domain.auth.service;

import dev.santieinaki.fagamosbarullo.app.data.auth.AuthDatasourceMock;
import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.datasource.AuthDatasource;

public class AuthServiceMock implements AuthService {

    private AuthDatasource mDatasource = new AuthDatasourceMock();

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

        return false;
    }

    @Override
    public boolean changePassword(String newPassword) {

        return false;
    }
}
