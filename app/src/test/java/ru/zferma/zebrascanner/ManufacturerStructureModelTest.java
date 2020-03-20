package ru.zferma.zebrascanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import businesslogic.ApplicationException;
import models.ManufacturerStructureModel;

public class ManufacturerStructureModelTest
{
    ManufacturerStructureModel model;

    @Before
    public void Init()
    {
        model = new ManufacturerStructureModel();
        model.Add((byte) 5,"УРАЛБРОЙЛЕР ЗАО (Ишалино)","23504297-7ee1-11e6-80d7-e4115bea65d2");
        model.Add((byte) 8,"Уральская мясная компания ООО ИНН 7438028838","a839c521-3ac5-11e6-80d2-e4115bea65d6");
    }

    @Test
    public void FindTest() throws ApplicationException
    {
        Assert.assertEquals("23504297-7ee1-11e6-80d7-e4115bea65d2", model.GetManufacturerGuidById((byte)5) );
        Assert.assertEquals("Уральская мясная компания ООО ИНН 7438028838", model.GetManufacturerNameById((byte)8) );
    }

    @Test(expected = ApplicationException.class)
    public void OutOfRangeTest() throws ApplicationException
    {
        model.GetManufacturerGuidById((byte)2);
    }

    @Test(expected = ApplicationException.class)
    public void OutOfRangeTestBig() throws ApplicationException
    {
        model.GetManufacturerGuidById((byte)100);
    }

    @Test
    public void FindNameByGuid() throws ApplicationException {
        String actual = model.GetManufacturerNameByGuid("23504297-7ee1-11e6-80d7-e4115bea65d2");
        String expected = "УРАЛБРОЙЛЕР ЗАО (Ишалино)";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ApplicationException.class)
    public void FindNameByGuidFail() throws ApplicationException {
        String actual = model.GetManufacturerNameByGuid("000");
    }
}
