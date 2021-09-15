package dev.santieinaki.fagamosbarullo;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import dev.santieinaki.fagamosbarullo.app.module.register.Register;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterUITest {

    @Rule
    public ActivityTestRule<Register> registerRule = new ActivityTestRule<>(Register.class, true, false);

    @Test
    public void testRegisterUseCase() {
        // don't call firebase
        Intent intent = new Intent();
        intent.putExtra(Register.TEST_TAG, true);
        registerRule.launchActivity(intent);

        // check mandatory full name
        UITestUtils.typeEditText(R.id.fullName, "");
        UITestUtils.pressButton(R.id.registerBtn);
        UITestUtils.checkEditTextError(R.id.fullName, UITestUtils.getString(R.string.full_name_required));
        UITestUtils.typeEditText(R.id.fullName, "Nome completo");

        // check mandatory email
        UITestUtils.typeEditText(R.id.email, "");
        UITestUtils.pressButton(R.id.registerBtn);
        UITestUtils.checkEditTextError(R.id.email, UITestUtils.getString(R.string.email_required));
        UITestUtils.typeEditText(R.id.email, "email@proba.gal");

        // check mandatory password
        UITestUtils.typeEditText(R.id.password, "");
        UITestUtils.pressButton(R.id.registerBtn);
        UITestUtils.checkEditTextError(R.id.password, UITestUtils.getString(R.string.password_required));

        // check password length
        UITestUtils.typeEditText(R.id.password, "segre");
        UITestUtils.pressButton(R.id.registerBtn);
        UITestUtils.checkEditTextError(R.id.password, UITestUtils.getString(R.string.password_length));
        UITestUtils.typeEditText(R.id.password, "do");

        // check phone number
        UITestUtils.typeEditText(R.id.phone, "");
        UITestUtils.pressButton(R.id.registerBtn);
        UITestUtils.checkEditTextError(R.id.phone, UITestUtils.getString(R.string.phone_number_required));
        UITestUtils.typeEditText(R.id.phone, "123456789");

        // check if it mocks the request
        UITestUtils.pressButton(R.id.registerBtn);
    }
}
