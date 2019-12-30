package businesslogic;

import java.util.HashMap;

public class ManufacturerStructureModel
{
    public ManufacturerStructureModel()
    {
        Manufacturers = new HashMap<>();
    }

    public String GetManufacturerGuid(Byte id)
    {
        return this.Manufacturers.get(id).Guid;
    }

    public void Add(Byte id, String name, String guid)
    {
        ManufacturerDetails details = new ManufacturerDetails();
        details.Name = name;
        details.Guid = guid;
        this.Manufacturers.put(id, details);
    }

    HashMap<Byte,ManufacturerDetails> Manufacturers;

    public class ManufacturerDetails
    {
        String Name;
        String Guid;
    }
}
