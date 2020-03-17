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
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/Products/v1/GetList?AccountingAreaGUID="+accountingAreaGuid;
    }

    public String GetOperationTypesURL()
    {
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/AccountingArea/v1/GetList?UserName="+ this.Username;

    }

    public String GetOrderProductURL(String accountingAreaGuid, String orderGUID)
    {
        return "http://"+this.ServerIP+"/erp_troyan/hs/TSD_Feed/BaseDocument/v1/GetByGUID?AccountingAreaGUID="+accountingAreaGuid+"&GUID="+orderGUID;
    }

    public String getResponseUrl()
    {
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/Execute/v1/Execute";
    }

    public String getManufacturersURL()
    {
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/Manufacturers/v1/GetList";
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

    public String GetPassword() {
        return this.Password;
    }
}
