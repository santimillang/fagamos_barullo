package dev.santieinaki.fagamosbarullo.app.module.main.search.presenter;

import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthService;
import dev.santieinaki.fagamosbarullo.app.domain.auth.service.AuthServiceImp;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserService;
import dev.santieinaki.fagamosbarullo.app.domain.user.service.UserServiceImp;
import dev.santieinaki.fagamosbarullo.app.module.BaseFragment;
import dev.santieinaki.fagamosbarullo.app.module.main.search.adapter.UserClickListener;
import dev.santieinaki.fagamosbarullo.app.module.main.search.viewmodel.UserModel;
import dev.santieinaki.fagamosbarullo.app.module.profile.Profile;

public class SearchPresenterImp implements SearchPresenter, UserClickListener {

    private SearchView mView;
    private UserService mService;
    private AuthService mAuthService;
    private AsyncTask<String, Void, List<User>> mTask;

    public SearchPresenterImp(SearchView view) {

        mView = view;
        mService = new UserServiceImp();
        mAuthService = new AuthServiceImp();
    }

    @Override
    public void searchUsers(String searchText,
                            List<String> instrumentos,
                            boolean favorites) {

        if (mTask != null) {
            destroy();
        }
        mTask = new GetUsers(instrumentos, favorites).execute(searchText);
    }

    @Override
    public void destroy() {

        mTask.cancel(true);
    }

    private void fillUsers(List<User> users) {

        List<UserModel> userModels = new ArrayList<>();

        for (User user : users) {
            userModels.add(new UserModel(user.getFullName(), user.getEmail(), user.getId()));
        }
        mView.fillList(userModels);
    }

    @Override
    public void onClicked(UserModel userModel) {

        String email = mAuthService.isLoggedIn().getEmail();

        // its us
        if (userModel.getEmail().equals(email)) {

            mView.navigateToProfile();
            return;
        }

        User user = new User(userModel.getfName(), userModel.getEmail(), "", new ArrayList<>(), "", userModel.getId());
        Intent intent = new Intent(((BaseFragment) mView).getActivity(), Profile.class);
        intent.putExtra(Profile.USER_INTENT, user);
        ((BaseFragment) mView).getActivity().startActivity(intent);
    }

    private class GetUsers extends AsyncTask<String, Void, List<User>> {

        private List<String> mList;
        private boolean mFavs;

        public GetUsers(List<String> list, boolean favs) {

            mList = list;
            mFavs = favs;
        }

        @Override
        protected List<User> doInBackground(String... strings) {

            return mService.searchUsers(strings[0], mList, mFavs);
        }

        @Override
        protected void onPostExecute(List<User> users) {

            if (isCancelled()) {
                return;
            }
            fillUsers(users);
        }
    }
}
