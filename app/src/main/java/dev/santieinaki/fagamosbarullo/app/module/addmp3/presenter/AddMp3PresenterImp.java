package dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter;

import android.net.Uri;
import android.os.AsyncTask;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;

public class AddMp3PresenterImp implements AddMp3Presenter {

    private AddMp3View mView;
    private UserService mService = new UserServiceImp();

    public AddMp3PresenterImp(AddMp3View view) {

        mView = view;
    }

    @Override
    public void uploadMaqueta(User user,
                              Uri maqueta,
                              Uri imagen,
                              String titulo) {

        mView.blockView();
        new UploadMaqueta(user, titulo, user.getMaquetas().size()).execute(maqueta, imagen);
    }

    private class UploadMaqueta extends AsyncTask<Uri, Void, User> {

        private int mPrev;
        private User mUser;
        private String mTitulo;

        public UploadMaqueta(User user,
                             String titulo,
                             int prev) {

            mPrev = prev;
            mUser = user;
            mTitulo = titulo;
        }

        @Override
        protected User doInBackground(Uri... uris) {

            return mService.uploadMaqueta(mUser, uris[0], uris[1], mTitulo);
        }

        @Override
        protected void onPostExecute(User user) {

            if (mPrev == mUser.getMaquetas().size()) {

                mView.unblockView();
                mView.showError();
            } else {

                mView.unblockView();
                mView.closeView(user);
            }
        }
    }
}
