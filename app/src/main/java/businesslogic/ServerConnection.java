package businesslogic;

public class ServerConnection {
    private String ServerIP;
    private String Username;
    private String Password;

    public ServerConnection(String serverIP, String username, String password)
    {
        this.ServerIP = serverIP;
        this.Username = username;
        this.Password = password;
    }

    public String GetProductURL()
    {
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/Products/v1/GetList";
    }

    public String GetOperationTypesURL()
    {
        return "http://"+ this.ServerIP +"/erp_troyan/hs/TSD_Feed/AccountingArea/v1/GetList?UserName="+ this.Username;

    }

    public String GetUserPass()
    {
        return this.Username + ":" + this.Password;
    }

    public String GetServerIP() {
        return this.ServerIP;
    }

    public String GetUsername() {
        return this.Username;
    }

    public String GetPassword() {
        return this.Password;
    }
}
