package dev.santieinaki.fagamosbarullo.app.data.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

import dev.santieinaki.fagamosbarullo.app.domain.auth.Auth;
import dev.santieinaki.fagamosbarullo.app.domain.auth.datasource.AuthDatasource;

public class AuthDatasourceImp implements AuthDatasource {

    @Override
    public boolean changeEmail(String newEmail) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Task<Void> task = firebaseUser.updateEmail(newEmail);

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return task.isSuccessful();
    }

    @Override
    public boolean changePassword(String newPassword) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Task<Void> task = firebaseUser.updatePassword(newPassword);

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return task.isSuccessful();
    }

    @Override
    public Auth login(String email,
                      String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (!task.isSuccessful()) {
            return null;
        }

        AuthResult authResult = task.getResult();
        FirebaseUser firebaseUser = authResult.getUser();

        return new Auth(firebaseUser.getUid());
    }

    @Override
    public Auth register(String email,
                         String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(email, password);

        try {
            Tasks.await(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        if (!task.isSuccessful()) {
            return null;
        }

        AuthResult authResult = task.getResult();
        FirebaseUser firebaseUser = authResult.getUser();

        return new Auth(firebaseUser.getUid());
    }

    @Override
    public Auth isLoggedIn() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            return null;
        }

        Auth auth = new Auth(firebaseUser.getUid());
        auth.setEmail(firebaseUser.getEmail());

        return auth;
    }
}
