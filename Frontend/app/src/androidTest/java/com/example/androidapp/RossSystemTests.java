package com.example.androidapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.not;

import android.view.KeyEvent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.Clan.ClanItemObject;
import com.example.androidapp.Game.User;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.MainAuth.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RossSystemTests
{
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    public void login()
    {
        onView(withId(R.id.Login_btn)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("John"), closeSoftKeyboard());
        onView(withId(R.id.password_entry)).perform(typeText("JohnPassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
    }
    @Test
    public void checkClans()
    {
    login();
    onView(withId(R.id.clanButton)).perform(click());

    try {
    Thread.sleep(SIMULATED_DELAY_MS);
    }
    catch (InterruptedException e)
    {
    }

    onView(withId(R.id.clanNameInput)).perform(clearText(), closeSoftKeyboard());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        }
        catch (InterruptedException e)
        {
        }
    onView(withId(R.id.clanNameInput)).perform(click(), closeSoftKeyboard());
    onView(withId(R.id.clanNameInput)).perform(typeText("randomClan5"), closeSoftKeyboard());
  //  onView(withId(R.id.clanNameInput)).perform(typeText("randomClan5"
 //   ), closeSoftKeyboard());
        onView(withId(R.id.UserIdInput)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.UserIdInput)).perform(clearText(), closeSoftKeyboard());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        }
        catch (InterruptedException e)
        {
        }
        onView(withId(R.id.UserIdInput)).perform(typeText("1"), closeSoftKeyboard());
    onView(withId(R.id.newClanButton)).perform(click());

    onView(withText(R.string.toast_text)).inRoot(withDecorView(not(testRule.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
