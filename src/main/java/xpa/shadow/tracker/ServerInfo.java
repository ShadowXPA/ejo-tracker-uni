/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ShadowXPA
 */
public class ServerInfo {

    private TempInfo tempInfo;
    private String IP;
    private String Port;
    private String Maxclients;
    private String Fraglimit;
    private String Timelimit;
    private String Gametype;
    private String Hostname;
    private String Mapname;
    private String Gamename;
    private List<Player> Players;

    public String getCountry() {
        return tempInfo.getCountry();
    }

    public String getRegion() {
        return tempInfo.getRegion();
    }

    public String getCountryAndRegion() {
        String tmp = "";
        if (!this.getRegion().equals(Constants.UNKNOWN)) {
            tmp += this.getRegion() + ", ";
        }

        tmp += this.getCountry();

        return tmp;
    }

    public String getCountryCode() {
        return tempInfo.getCountryCode();
    }

    public String getFlagUrl() {
        if (this.getCountryCode().equals(Constants.UNKNOWN)) {
            return "ejol0g0.gif";
        }

        return "https://flags.fmcdn.net/data/flags/h20/" + this.getCountryCode() + ".png";
    }

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return Port;
    }

    public void setMaxclients(String Maxclients) {
        this.Maxclients = Maxclients;
    }

    public void setFraglimit(String Fraglimit) {
        this.Fraglimit = Fraglimit;
    }

    public void setTimelimit(String Timelimit) {
        this.Timelimit = Timelimit;
    }

    public void setGametype(String Gametype) {
        this.Gametype = Gametype;
    }

    public void setMapname(String Mapname) {
        this.Mapname = Mapname;
    }

    public void setGamename(String Gamename) {
        this.Gamename = Gamename;
    }

    public String getMaxclients() {
        return Maxclients;
    }

    public String getFraglimit() {
        return Fraglimit;
    }

    public String getTimelimit() {
        return Timelimit;
    }

    public String getGametype() {
        return Gametype;
    }

    public String getGametypeStr() {
        String tmp = this.Gametype;

        switch (tmp) {
            case "0":
                tmp = "FFA";
                break;
            case "3":
                tmp = "Duel";
                break;
            case "4":
                tmp = "Power Duel";
                break;
            case "6":
                tmp = "Team FFA";
                break;
            case "7":
                tmp = "Siege";
                break;
            case "8":
                tmp = "CTF";
                break;
            default:
                break;
        }

        return tmp;
    }

    public String getHostname() {
        return Hostname;
    }

    public String getMapname() {
        return Mapname;
    }

    public String getMapnamePic() {
        return "levelshots/" + this.Mapname + ".jpg";
    }

    public String getGamename() {
        return Gamename;
    }

    public int GetNumPlayers() {
        return this.Players.size();
    }

    public int GetNumRealPlayers() {
        return GetNumPlayers() - GetNumBots();
    }

    public int GetNumBots() {
        int i = 0;
        for (Player p : this.Players) {
            if (p.isBot()) {
                i++;
            }
        }
        return i;
    }

    public Player getPlayer(String Name) {
        for (Player p : this.Players) {
            if (p.getName().equals(Name)) {
                return p;
            }
        }
        return null;
    }

    public Player getPlayer(int Index) {
        return this.Players.get(Index);
    }

    protected void AddPlayer(Player Player) {
        this.Players.add(Player);
    }

    public void AddPlayer(String Name, String Score, String Ping) {
        AddPlayer(new Player(Name, Score, Ping));
    }

    public void ClearPlayers() {
        this.Players.clear();
    }

    public void SortPlayers() {
        this.Players.sort(new PlayerComparator());
    }

    public int getPlayersSize() {
        return this.Players.size();
    }

    public void RefreshPlayer(String Name, String Score, String Ping) {
        if (isPlayerOnline(Name)) {
            this.getPlayer(Name).setScore(Score);
            this.getPlayer(Name).setPing(Ping);
        } else {
        }
    }

    public boolean isPlayerOnline(String Name) {
        boolean is = false;
        for (Player p : this.Players) {
            if (p.getName().equals(Name)) {
                is = true;
                break;
            }
        }
        return is;
    }

    public ServerInfo(TempInfo ti) {
        this(ti, null, null, null, null, null, null);
    }

    public ServerInfo(TempInfo ti, String Maxclients, String Fraglimit, String Timelimit, String Gametime, String Mapname, String Gamename) {
        tempInfo = ti;
        this.Hostname = Constants.SERVER_NAME;
        this.Maxclients = Maxclients;
        this.Fraglimit = Fraglimit;
        this.Timelimit = Timelimit;
        this.Gametype = Gametime;
        this.Mapname = Mapname;
        this.Gamename = Gamename;
        this.Players = new ArrayList<>();
    }
}
