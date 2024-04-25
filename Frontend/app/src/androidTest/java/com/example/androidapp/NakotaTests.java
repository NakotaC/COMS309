package com.example.androidapp;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.instanceOf;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.androidapp.MainAuth.MainActivity;
import com.example.androidapp.ShopInventory.EquippedItemInventory;
import com.example.androidapp.ShopInventory.ListItemObjectInventory;
import com.example.androidapp.ShopInventory.ListItemObjectShop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4ClassRunner.class)
//@RunWith(AndroidJUnit4.class)
@LargeTest   // large execution time
public class NakotaTests {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void signupFailTest(){
        onView(withId(R.id.Signup_btn)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("John"), closeSoftKeyboard());
        onView(withId(R.id.password_entry)).perform(typeText("JohnPassword"), closeSoftKeyboard());
        onView(withId(R.id.signup_button)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.user_taken_text)).check(matches(isDisplayed()));
    }

    @Test
    public void loginTest(){
        onView(withId(R.id.Login_btn)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("John"), closeSoftKeyboard());
        onView(withId(R.id.password_entry)).perform(typeText("JohnPassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.home_welcome_text)).check(matches(withText(endsWith("John"))));
    }
    @Test
    public void shopTest(){
        loginTest();
        onView(withId(R.id.shopButton)).perform(click());
        //onView(withId(R.id.bankValTxt)).check(matches(withText(endsWith())));
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onData(instanceOf(ListItemObjectShop.class))
                .inAdapterView(withId(R.id.shopList))
                .atPosition(0)
                .check(matches(hasDescendant(withText("Item1"))));

        onData(instanceOf(ListItemObjectShop.class))
                .inAdapterView(withId(R.id.shopList))
                .atPosition(0)
                .perform(click());


        onView(withId(R.id.respondText)).check(matches(withText("success")));
    }

    @Test
    public void inventoryTest(){
        loginTest();
        onView(withId(R.id.shopButton)).perform(click());

        //onView(withId(R.id.bankValTxt)).check(matches(withText(endsWith())));
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.shopInventoryBtn)).perform(click());

        onData(instanceOf(ListItemObjectInventory.class))
                .inAdapterView(withId(R.id.listView_inventory))
                .atPosition(0)
                .perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        String str = onView(withId(R.id.equip_unequip_txt)).toString();
        onView(withId(R.id.equip_unequip_txt)).check(matches(withText("Equipped")));

        onData(instanceOf(EquippedItemInventory.class))
                .inAdapterView(withId(R.id.listview_equipped))
                .atPosition(0)
                .perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.equip_unequip_txt)).check(matches(withText(endsWith("Unequipped"))));

    }




}
