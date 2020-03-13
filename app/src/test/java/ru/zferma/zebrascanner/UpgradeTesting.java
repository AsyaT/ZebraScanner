package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Test;

import upgrading.UpgradeHelper;

public class UpgradeTesting
{
    @Test
    public void CheckNewVersion()
    {
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("1.0","1.0"));
        Assert.assertTrue(UpgradeHelper.IsNewVersionAvailable("1.1","1.0"));
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("","1.0"));
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("1.0","1.1"));
        Assert.assertTrue(UpgradeHelper.IsNewVersionAvailable("2.1.1","2.1"));
        Assert.assertTrue(UpgradeHelper.IsNewVersionAvailable("3","2.1.1"));
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("5.74.51.2.8.1","64"));
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("2.1.1","3"));
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("45.7.19.0","45.7.19"));
    }

    @Test(expected = NumberFormatException.class)
    public void CheckNewVersionError()
    {
        Assert.assertFalse(UpgradeHelper.IsNewVersionAvailable("1,0","1.0"));
    }

    @Test
    public void ReadVersionTest()
    {
        Assert.assertEquals(UpgradeHelper.ReadNewVersion("http://192.168.88.217/"), "1.2");
    }

    @Test
    public void Install()
    {

    }
}
