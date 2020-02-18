package ru.zferma.zebrascanner;

import android.content.Intent;
import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP, manifest = Config.NONE)
public class FirstScreenActivityTest {

    PreSettingsActivity activity;

    @Before
    public void SetUp() throws Exception
    {
        activity = Robolectric.buildActivity(PreSettingsActivity.class).create().resume().get();
    }

    @Test
    public void ActivityExists()
    {
        Assert.assertNotNull(activity);
    }

    @Test
    public void SelectOperation()
    {
        activity.findViewById(R.id.btnSelectOperation).performClick();
        Intent expectedIntent = new Intent(activity, OperationSelectionActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        // TODO: actual = null . Fix it
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
