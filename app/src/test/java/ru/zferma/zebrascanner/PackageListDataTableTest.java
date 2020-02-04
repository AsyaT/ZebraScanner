package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

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

    @Test
    public void IsContainsTest()
    {
        Assert.assertTrue(packageListDataTable.IsContains("111-222-333"));
        Assert.assertTrue(packageListDataTable.IsContains("1234567890"));
    }
}
