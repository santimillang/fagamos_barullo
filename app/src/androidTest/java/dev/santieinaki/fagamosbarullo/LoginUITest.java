package dev.santieinaki.fagamosbarullo;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import dev.santieinaki.fagamosbarullo.app.module.login.Login;
import dev.santieinaki.fagamosbarullo.app.module.register.Register;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginUITest {

    @Rule
    public ActivityTestRule<Login> loginRule = new ActivityTestRule<>(Login.class, true, false);

    @Test
    public void testLoginUseCase() {
        // don't call firebase
        Intent intent = new Intent();
        intent.putExtra(Login.TEST_TAG, true);
        loginRule.launchActivity(intent);

        // check mandatory email
        UITestUtils.typeEditText(R.id.email, "");
        UITestUtils.pressButton(R.id.loginBtn);
        UITestUtils.checkEditTextError(R.id.email, UITestUtils.getString(R.string.email_required));
        UITestUtils.typeEditText(R.id.email, "email@proba.gal");

        // check mandatory password
        UITestUtils.typeEditText(R.id.password, "");
        UITestUtils.pressButton(R.id.loginBtn);
        UITestUtils.checkEditTextError(R.id.password, UITestUtils.getString(R.string.password_required));
        UITestUtils.typeEditText(R.id.password, "segredo");

        // check if it mocks the request
        UITestUtils.pressButton(R.id.loginBtn);
    }
}
