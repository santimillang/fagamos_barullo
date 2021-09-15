package dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter;

import android.net.Uri;
import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;

public class AddPhotoPresenterImp implements AddPhotoPresenter {

    private AddPhotoView mView;
    private UserService mService = new UserServiceImp();

    public AddPhotoPresenterImp(AddPhotoView view) {

        mView = view;
    }

    @Override
    public void uploadPhoto(User user,
                            Uri imageUri,
                            String titulo) {

        mView.blockView();
        new UploadPhoto(user, titulo, user.getFotos().size()).execute(imageUri);
    }

    private class UploadPhoto extends AsyncTask<Uri, Void, User> {

        private int mPrev;
        private User mUser;
        private String mTitulo;

        public UploadPhoto(User user,
                           String titulo,
                           int prev) {

            mPrev = prev;
            mUser = user;
            mTitulo = titulo;
        }

        @Override
        protected User doInBackground(Uri... uris) {

            return mService.uploadPhoto(mUser, uris[0], mTitulo);
        }

        @Override
        protected void onPostExecute(User user) {

            if (mPrev == mUser.getFotos().size()) {

                mView.unblockView();
                mView.showError();
            } else {

                mView.unblockView();
                mView.closeView(user);
            }
        }
    }
}
