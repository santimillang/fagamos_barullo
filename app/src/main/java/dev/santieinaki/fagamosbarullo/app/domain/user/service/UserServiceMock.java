package dev.santieinaki.fagamosbarullo.app.domain.user.service;

import android.net.Uri;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.data.user.UserDatasourceMock;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.datasource.UserDatasource;

public class UserServiceMock implements UserService {

    private UserDatasource mDatasource = new UserDatasourceMock();

    @Override
    public boolean registerUser(User user) {

        return mDatasource.register(user);
    }

    @Override
    public User getUserInfo(User user) {

        return null;
    }

    @Override
    public User getUserInfo() {

        return null;
    }

    @Override
    public void saveUserInfo(User user) {

        return;
    }

    @Override
    public List<User> searchUsers(String name,
                                  List<String> instrumentos,
                                  boolean favs) {

        return null;
    }

    @Override
    public User follow(User user) {

        return null;
    }

    @Override
    public User uploadPhoto(User user,
                            Uri uri,
                            String titulo) {

        return null;
    }

    @Override
    public User uploadMaqueta(User user,
                              Uri uri,
                              Uri image,
                              String titulo) {

        return null;
    }
}
