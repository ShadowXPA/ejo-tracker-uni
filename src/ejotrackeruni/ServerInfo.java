/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejotrackeruni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author ShadowXPA
 */
public class ServerInfo {

    // 0 - no, 1 - yes, 2 - throw exception please
    private volatile int canGo;

    private String Country;
    private String Region;
    private String CountryCode;
    private String FlagUrl;
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
        return Country;
    }

    public String getRegion() {
        return Region;
    }

    public String getCountryAndRegion() {
        String tmp = "";
        if (!this.Region.equals(SC.Unk)) {
            tmp += this.Region + ", ";
        }

        tmp += this.Country;

        return tmp;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public String getFlagUrl() {
        return FlagUrl;
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
        return this.Mapname.replace("/", "") + ".png";
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

    public ServerInfo(TempInfo ti) throws Exception {
        this(ti, null, null, null, null, null, null);
    }

    public ServerInfo(TempInfo ti, String Maxclients, String Fraglimit, String Timelimit, String Gametime, String Mapname, String Gamename) throws Exception {
        this.canGo = 0;
        this.Country = ti.getCountry();
        this.CountryCode = ti.getCountryCode();
        this.Region = ti.getRegion();
        this.FlagUrl = "ejol0g0.gif";
        if (!this.CountryCode.equals(SC.Unk)) {
            this.FlagUrl = "http://flags.fmcdn.net/data/flags/h20/" + this.CountryCode + ".png";
        }
        this.Hostname = SC.ServerName;
        this.Maxclients = Maxclients;
        this.Fraglimit = Fraglimit;
        this.Timelimit = Timelimit;
        this.Gametype = Gametime;
        this.Mapname = Mapname;
        this.Gamename = Gamename;
        this.Players = new ArrayList<>();
    }

    private void SetCountryAndRegion() throws Exception {
        this.Country = SC.Unk;
        this.Region = SC.Unk;
        this.CountryCode = SC.Unk;

        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL("https://api.ipfind.com/?ip=" + this.IP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);
                connection.connect();
                int cod = connection.getResponseCode();
                if (cod == HttpURLConnection.HTTP_OK) {
                    InputStream IS = connection.getInputStream();
                    BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
                    String data;
                    while ((data = BR.readLine()) != null) {
                        sb.append(data).append("\n");
                    }
                }
                String str = sb.toString();
                JSONObject jo;
                try {
                    jo = new JSONObject(str);
                    String ctry = SC.Unk;
                    String ctryCod = SC.Unk;
                    String reg = SC.Unk;
                    try {
                        ctry = jo.getString("country");
                    } catch (JSONException ignored) {
                    }
                    try {
                        ctryCod = jo.getString("country_code");
                    } catch (JSONException ignored) {
                    }
                    try {
                        reg = jo.getString("region");
                    } catch (JSONException ignored) {
                    }

                    if (!ctry.equals(SC.Unk)) {
                        this.Country = ctry;
                    }
                    if (!ctryCod.equals(SC.Unk)) {
                        this.CountryCode = ctryCod.toLowerCase();
                    }
                    if (!reg.equals(SC.Unk)) {
                        this.Region = reg;
                    }
                } catch (JSONException ignored) {
                }

                this.canGo = 1;
            } catch (IOException | JSONException ignored) {
                this.canGo = 2;
            }
        }).start();

        boolean breakLoop = false;
        while (!breakLoop) {
            switch (canGo) {
                case 1:
                    breakLoop = true;
                    break;
                case 2:
                    throw new Exception("An exception occured...");
                default:
                    break;
            }
        }
    }
}
