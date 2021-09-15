package dev.santieinaki.fagamosbarullo.app.domain.auth.datasource;

import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;

public interface AuthDatasource {

    public Auth login(String email,
                      String password);

    public Auth register(String email,
                         String password);

    public Auth isLoggedIn();

    public boolean changeEmail(String newEmail);

    public boolean changePassword(String newPassword);
}
