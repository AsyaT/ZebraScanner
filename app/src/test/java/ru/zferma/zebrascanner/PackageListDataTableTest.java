package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import businesslogic.ApplicationException;
import businesslogic.PackageListDataTable;
import businesslogic.PackageListStructureModel;

public class PackageListDataTableTest
{
    PackageListDataTable packageListDataTable;

    @Before
    public void Init()
    {
        PackageListStructureModel packageListModel = new PackageListStructureModel(
                "111-222-333",
                new Date(2020, 1,30),
                "888-888-888",
                "1234567890");

        packageListDataTable = new PackageListDataTable();
        packageListDataTable.Add(packageListModel);
    }

    @Test(expected = ApplicationException.class)
    public void IsContainsTest() throws ApplicationException
    {
        Assert.assertTrue(packageListDataTable.IsActionAllowedWithPackageList("111-222-333"));
        Assert.assertTrue(packageListDataTable.IsActionAllowedWithPackageList("1234567890"));
    }

    @Test
    public void DoesNotContainsTest() throws ApplicationException
    {
        packageListDataTable.IsActionAllowedWithPackageList("5555555");
    }
}
