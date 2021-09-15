package dev.santieinaki.fagamosbarullo.app.domain.user.service;

import android.net.Uri;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.data.user.UserDatasourceImp;
import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthService;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.datasource.UserDatasource;

public class UserServiceImp implements UserService {

    private AuthService mAuthService = new AuthServiceImp();
    private UserDatasource mDatasource = new UserDatasourceImp();

    @Override
    public boolean registerUser(User user) {

        return mDatasource.register(user);
    }

    @Override
    public User getUserInfo(User user) {

        return mDatasource.getUserInfoWithEmail(user.getEmail());
    }

    @Override
    public User getUserInfo() {

        Auth auth = mAuthService.isLoggedIn();
        return (auth == null ? null : mDatasource.getUserInfo(auth.getId()));
    }

    @Override
    public void saveUserInfo(User user) {

        mDatasource.saveUserInfo(user);
    }

    @Override
    public List<User> searchUsers(String name,
                                  List<String> instrumentos,
                                  boolean favs) {

        return mDatasource.searchUsers(name, instrumentos, favs);
    }

    @Override
    public User follow(User user) {

        Auth auth = mAuthService.isLoggedIn();
        User retUser = mDatasource.follow(user, auth.getId());

        return retUser;
    }

    @Override
    public User uploadPhoto(User user,
                            Uri uri,
                            String titulo) {

        return mDatasource.uploadPhoto(user, uri, titulo);
    }

    @Override
    public User uploadMaqueta(User user,
                              Uri uri,
                              Uri image,
                              String titulo) {

        return mDatasource.uploadMaqueta(user, uri, image, titulo);
    }
}
