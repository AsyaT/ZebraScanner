package businesslogic;

public class ServerConnection {
    private String ServerIP;
    private String Database;
    private String Username;
    private String Password;

    public ServerConnection(String serverIP, String database, String username, String password)
    {
        this.ServerIP = serverIP;
        this.Database = database;
        this.Username = username;
        this.Password = password;
    }

    public String GetProductURL()
    {
        //TODO: implement server connection
        return "";
    }

    public String GetBarcodeListURL(String accountingAreaGuid)
    {
        return GenerateUrlBeginning() + "Products/v1/GetList?AccountingAreaGUID="+accountingAreaGuid;
    }

    public String GetOperationTypesURL()
    {
        return GenerateUrlBeginning() + "AccountingArea/v1/GetList?UserName="+ this.Username;

    }

    public String GetOrderProductURL(String accountingAreaGuid, String orderGUID)
    {
        return GenerateUrlBeginning() + "BaseDocument/v1/GetByGUID?AccountingAreaGUID="+accountingAreaGuid+"&GUID="+orderGUID;
    }

    public String getResponseUrl()
    {
        return GenerateUrlBeginning() + "Execute/v1/Execute";
    }

    public String getManufacturersURL()
    {
        return GenerateUrlBeginning() + "Manufacturers/v1/GetList";
    }

    public String GetUsernameAndPassword()
    {
        return this.Username + ":" + this.Password;
    }

    public String GetServerIP() {
        return this.ServerIP;
    }

    public String GetDatabaseName() { return this.Database;}

    public String GetUsername() { return this.Username; }

    public String GetPassword() { return this.Password; }

    private String GenerateUrlBeginning()
    {
        return "http://"+ this.ServerIP +"/"+this.Database+"/hs/TSD_Feed/";
    }
}
