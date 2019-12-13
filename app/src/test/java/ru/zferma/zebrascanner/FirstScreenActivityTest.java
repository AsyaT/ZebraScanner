package ru.zferma.zebrascanner;

import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
}
