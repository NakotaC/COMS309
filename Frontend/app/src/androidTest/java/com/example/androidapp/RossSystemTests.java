package com.example.androidapp;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
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

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.not;

import android.view.KeyEvent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.Clan.ClanItemObject;
import com.example.androidapp.Game.User;
import com.example.androidapp.Leaderboard.LeaderboardItemObject;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.MainAuth.MainActivity;
import com.example.androidapp.ShopInventory.ListItemObjectShop;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RossSystemTests
{
    private static final int SIMULATED_DELAY_MS = 500;
    private static final int FIRST_ITEM = 1;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLoginWorksProperly()
    {
        onView(withId(R.id.Login_btn)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("John"), closeSoftKeyboard());
        onView(withId(R.id.password_entry)).perform(typeText("JohnPassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try
        {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e)
        {
        }

        onView(withId(R.id.home_welcome_text)).check(matches(withText(endsWith("John"))));
    }
    @Test
    public void checkClansFirstItemIsCorrect()
    {
    testLoginWorksProperly();
    onView(withId(R.id.clanButton)).perform(click());

    try
    {
    Thread.sleep(SIMULATED_DELAY_MS);
    } catch (InterruptedException e)
    {
    }

    onView(ViewMatchers.withId(R.id.idRVCourses))
            .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_ITEM, click()));

        String itemElementText = getApplicationContext().getResources().getString(
                R.string.noClan);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void checkLeaderboardWorksProperly()
    {
    testLoginWorksProperly();
    onView(withId(R.id.statsButton)).perform(click());

    onData(instanceOf(LeaderboardItemObject.class))
            .inAdapterView(withId(R.id.list1))
            .atPosition(0)
            .check(matches(hasDescendant(withText("John"))));

    onData(instanceOf(LeaderboardItemObject.class))
            .inAdapterView(withId(R.id.list1))
            .atPosition(1)
            .check(matches(hasDescendant(withText("Tom"))));
    }

    @Test
    public void checkClanLeaderboardWorksProperly()
    {
    testLoginWorksProperly();
    onView(withId(R.id.clanButton)).perform(click());

    try
    {
    Thread.sleep(SIMULATED_DELAY_MS);
    } catch (InterruptedException e)
    {
    }


    onView(ViewMatchers.withId(R.id.idRVCourses))
            .check(matches(isDisplayed()));
    }






}
