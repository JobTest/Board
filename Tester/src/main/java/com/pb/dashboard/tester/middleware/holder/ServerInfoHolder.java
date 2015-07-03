package com.pb.dashboard.tester.middleware.holder;

public class ServerInfoHolder {

    private String serverIP,
            username,
            password;

    private EASInfoHolder[] easInfoHolders;
    private GFInfoHolder[] gfInfoHolders;

    public ServerInfoHolder(String serverIP, String username, String password) {
        this.serverIP = serverIP;
        this.username = username;
        this.password = password;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEasInfoHolders(EASInfoHolder[] easInfoHolders) {
        this.easInfoHolders = easInfoHolders;
    }

    public void setGfInfoHolders(GFInfoHolder[] gfInfoHolders) {
        this.gfInfoHolders = gfInfoHolders;
    }

    public EASInfoHolder[] getEasInfoHolders() {
        return easInfoHolders;
    }

    public GFInfoHolder[] getGfInfoHolders() {
        return gfInfoHolders;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Server: %s%n", serverIP));
        sb.append("********** EAS ************");
        for (EASInfoHolder easInfo : easInfoHolders) {
            sb.append(easInfo.toString());
        }
        sb.append(String.format("-------------------%n%n"));
        sb.append("********** Glassfish ***********");
        for (GFInfoHolder gfInfo : gfInfoHolders) {
            sb.append(gfInfo.toString());
        }

        return sb.toString();
    }

}
