package dev.santieinaki.fagamosbarullo.app.domain.user.service;

import android.net.Uri;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface UserService {

    public boolean registerUser(User user);

    public User getUserInfo(User user);

    public User getUserInfo();

    public void saveUserInfo(User user);

    public List<User> searchUsers(String name, List<String> instrumentos, boolean favs);

    public User follow(User user);

    public User uploadPhoto(User user, Uri uri, String titulo);

    public User uploadMaqueta(User user, Uri uri, Uri image, String titulo);
}
