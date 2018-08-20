package rahul.mvpapplication;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import rahul.mvpapplication.activity.MainActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ActivityMainTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void testShouldLaunchTheMainActivityAndFindItemsInTheList() throws Exception {
        RecyclerView recyclerView = (RecyclerView) main.getActivity().findViewById(R.id.recyclerView);
        assertThat(recyclerView.getScrollBarSize(), is(11));
    }

    @Before
    public void init() {
        main.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testShouldTestTheItemNameInTheList() throws Exception {
        RecyclerView recyclerView = (RecyclerView) main.getActivity().findViewById(R.id.recyclerView);
        if (recyclerView.getScrollBarSize() > 0) {
            for (int i = 0; i < recyclerView.getScrollBarSize(); i++) {
                if (i == 1) {
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition
                            (1, typeTextIntoFocusedView("Flag")));
                }
            }
        }
    }

}