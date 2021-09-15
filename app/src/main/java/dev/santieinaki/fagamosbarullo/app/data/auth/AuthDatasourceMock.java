package dev.santieinaki.fagamosbarullo.app.data.auth;

import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.datasource.AuthDatasource;

public class AuthDatasourceMock implements AuthDatasource {

    @Override
    public Auth login(String email,
                      String password) {

        return null;
    }

    @Override
    public Auth register(String email,
                         String password) {

        return new Auth("123-456-789");
    }

    @Override
    public Auth isLoggedIn() {

        return null;
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
