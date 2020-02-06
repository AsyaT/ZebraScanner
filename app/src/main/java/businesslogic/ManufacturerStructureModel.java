package businesslogic;

import java.util.HashMap;

public class ManufacturerStructureModel
{
    public ManufacturerStructureModel()
    {
        Manufacturers = new HashMap<>();
    }

    public String GetManufacturerGuid(Byte id) throws ApplicationException
    {
        return GetManufacturer(id).Guid;
    }
    public String GetManufacturerName(Byte id) throws ApplicationException
    {
        return GetManufacturer(id).Name;
    }

    protected ManufacturerStructureModel.ManufacturerDetails GetManufacturer(Byte id) throws ApplicationException
    {
        try {
            return this.Manufacturers.get(id);
        }
        catch (NullPointerException e)
        {
            throw new ApplicationException("Производитель с номером" + id + " не найден");
        }
    }

    public void Add(Byte id, String name, String guid)
    {
        ManufacturerDetails details = new ManufacturerDetails();
        details.Name = name;
        details.Guid = guid;
        this.Manufacturers.put(id, details);
    }

    HashMap<Byte,ManufacturerDetails> Manufacturers;

    protected class ManufacturerDetails
    {
        String Name;
        String Guid;
    }
}
