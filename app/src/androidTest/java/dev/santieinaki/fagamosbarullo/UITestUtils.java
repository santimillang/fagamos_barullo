package dev.santieinaki.fagamosbarullo;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class UITestUtils {

    public static void checkEditTextError(int id,
                                          String errorMessage) {

        onView(withId(id)).check(matches(hasErrorText(errorMessage)));
    }

    public static void pressButton(int id) {

        onView(withId(id)).perform(click());
    }

    public static void typeEditText(int id,
                                    String text) {

        ViewAction action = (text.isEmpty() ? clearText() : typeText(text));
        onView(withId(id)).perform(action);
        closeSoftKeyboard();
    }

    public static String getString(int id) {

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        return context.getResources().getString(id);
    }
}
