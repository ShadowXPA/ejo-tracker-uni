/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejotrackeruni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ShadowXPA
 */
public final class TempInfo {

    private String TrackerMessage;
    // Application settings
    private int RefreshTime;
    private boolean AllowNotifications;                                         // if (SystemTray.isSupported()) Enable yes or no Show notifications on the system tray
    private boolean AllowMultipleNotifications;                                 // if (AllowNotifications && CheckBox.IsChecked)
    private int Minutes;                                                        // Default and minimum 30 minutes
    private boolean AdvancedNotificationMode;                                   // Add option to select notification mode... false: normal mode, true: notify when a new player joins or when a player leaves
    private boolean BodyParkedOnServerMode;                                     // Add option to select parked body mode... false: normal mode, true: notify when 2 players are online (since parked body means one player is on the server [you], if the player is not on the server, it will notify only when 2 players are online)
    private boolean LogPlayers;                                                 // Add option to log players to file when refreshing
    //

    private String ServerIP;
    private String ServerPort;
    private boolean AssignedNetworkInfo;
    private String Country;
    private String CountryCode;
    private String Region;
    private boolean AssignedCountryInfo;

    public boolean isAllowNotifications() {
        return AllowNotifications;
    }

    public void setAllowNotifications(boolean AllowNotifications) {
        this.AllowNotifications = AllowNotifications;
    }

    public boolean isAllowMultipleNotifications() {
        return AllowMultipleNotifications;
    }

    public void setAllowMultipleNotifications(boolean AllowMultipleNotifications) {
        this.AllowMultipleNotifications = AllowMultipleNotifications;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int Minutes) {
        if (Minutes >= SC.MinMinutes) {
            this.Minutes = Minutes;
        }
    }

    public boolean isAdvancedNotificationMode() {
        return AdvancedNotificationMode;
    }

    public void setAdvancedNotificationMode(boolean AdvancedNotificationMode) {
        this.AdvancedNotificationMode = AdvancedNotificationMode;
    }

    public boolean isBodyParkedOnServerMode() {
        return BodyParkedOnServerMode;
    }

    public void setBodyParkedOnServerMode(boolean BodyParkedOnServerMode) {
        this.BodyParkedOnServerMode = BodyParkedOnServerMode;
    }

    public boolean isLogPlayers() {
        return LogPlayers;
    }

    public void setLogPlayers(boolean LogPlayers) {
        this.LogPlayers = LogPlayers;
    }

    public int getRefreshTime() {
        return RefreshTime;
    }

    public void setRefreshTime(int RefreshTime) {
        if (RefreshTime >= SC.RefreshTime) {
            this.RefreshTime = RefreshTime;
        }
    }

    public String getTrackerMessage() {
        return this.TrackerMessage;
    }

    public String getServerIP() {
        if (this.AssignedNetworkInfo) {
            return ServerIP;
        } else {
            return SC.ServerIP;
        }
    }

    public String getServerPort() {
        if (this.AssignedNetworkInfo) {
            return ServerPort;
        } else {
            return "" + SC.ServerPort;
        }
    }

    public String getCountry() {
        if (this.AssignedCountryInfo) {
            return Country;
        } else {
            return SC.Unk;
        }
    }

    public String getCountryCode() {
        if (this.AssignedCountryInfo) {
            return CountryCode;
        } else {
            return SC.Unk;
        }
    }

    public String getRegion() {
        if (this.Region != null && !this.Region.isEmpty()) {
            return Region;
        } else {
            return SC.Unk;
        }
    }

    public boolean isAssignedNetworkInfo() {
        return AssignedNetworkInfo;
    }

    public boolean isAssignedCountryInfo() {
        return AssignedCountryInfo;
    }

    public final void TrySaveInfo() {
        File dir = new File("config");
        File fl = new File("config/trackerconfig.ejo");

        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
            JSONObject jo = new JSONObject();

            jo.put("RefreshTime", this.RefreshTime);
            jo.put("AllowNotifications", this.AllowNotifications);
            jo.put("AllowMultipleNotifications", this.AllowMultipleNotifications);
            jo.put("AdvancedNotificationMode", this.AdvancedNotificationMode);
            jo.put("BodyParkedOnServerMode", this.BodyParkedOnServerMode);
            jo.put("SendNewNotificationTime", this.Minutes);
            jo.put("LogPlayers", this.LogPlayers);

            bw.write(jo.toString(2));
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(TempInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void TryAssignInfo() {
        File dir = new File("config");
        File fl = new File("config/trackerconfig.ejo");

        this.ServerIP = SC.Unk;
        this.ServerPort = SC.Unk;
        this.Country = SC.Unk;
        this.CountryCode = SC.Unk;
        this.Region = SC.Unk;

        this.RefreshTime = SC.RefreshTime;
        this.AllowNotifications = true;
        this.AllowMultipleNotifications = true;
        this.AdvancedNotificationMode = false;
        this.BodyParkedOnServerMode = false;
        this.Minutes = SC.MinMinutes;
        this.LogPlayers = false;

        // Get from website
        RefreshTI();

        if (dir.exists() && dir.isDirectory()) {
            if (fl.exists() && !fl.isDirectory()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fl));

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject jo = new JSONObject(sb.toString());

                    try {
                        int tmp1 = jo.getInt("RefreshTime");
                        if (tmp1 >= SC.RefreshTime) {
                            this.RefreshTime = tmp1;
                        } else {
                            this.RefreshTime = SC.RefreshTime;
                        }
                    } catch (JSONException ignored) {
                        this.RefreshTime = SC.RefreshTime;
                    }
                    try {
                        this.AllowNotifications = jo.getBoolean("AllowNotifications");
                    } catch (JSONException ignored) {
                        this.AllowNotifications = true;
                    }
                    try {
                        this.AllowMultipleNotifications = jo.getBoolean("AllowMultipleNotifications");
                    } catch (JSONException ignored) {
                        this.AllowMultipleNotifications = true;
                    }
                    try {
                        this.AdvancedNotificationMode = jo.getBoolean("AdvancedNotificationMode");
                    } catch (JSONException ignored) {
                        this.AdvancedNotificationMode = false;
                    }
                    try {
                        this.BodyParkedOnServerMode = jo.getBoolean("BodyParkedOnServerMode");
                    } catch (JSONException ignored) {
                        this.BodyParkedOnServerMode = false;
                    }
                    try {
                        this.Minutes = jo.getInt("SendNewNotificationTime");
                        if (this.Minutes < SC.MinMinutes) {
                            this.Minutes = SC.MinMinutes;
                        }
                    } catch (JSONException ignored) {
                        this.Minutes = SC.MinMinutes;
                    }
                    try {
                        this.LogPlayers = jo.getBoolean("LogPlayers");
                    } catch (JSONException ignored) {
                        this.LogPlayers = false;
                    }

                    br.close();
                } catch (IOException ex) {
                }
            }
        }

        if (!this.ServerIP.equals(SC.Unk) && !this.ServerPort.equals(SC.Unk)) {
            this.AssignedNetworkInfo = true;
        }

        if (!this.Country.equals(SC.Unk) && !this.CountryCode.equals(SC.Unk)) {
            this.AssignedCountryInfo = true;
        }
    }

    public void RefreshTI() {
        try {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) new URL(SC.EJO_CHECK_SERVER_INFO).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(10000);
            con.setDoInput(true);
            con.connect();
            int code = con.getResponseCode();
            if (code == 200) {
                InputStream IS = con.getInputStream();
                BufferedReader BR = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                String data;
                while ((data = BR.readLine()) != null) {
                    sb.append(data).append("\n");
                }
                String data2 = sb.toString();
                String[] ServerInfo = data2.split("\n");
                if (ServerInfo.length > 1) {
                    this.ServerIP = ServerInfo[0];
                    this.ServerPort = ServerInfo[1];
                    this.Country = ServerInfo[2];
                    this.CountryCode = ServerInfo[3];
                    if (ServerInfo.length == 6) {
                        this.Region = ServerInfo[4];
                        this.TrackerMessage = ServerInfo[5];
                    } else if (ServerInfo.length == 5) {
                        this.TrackerMessage = ServerInfo[4];
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TempInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TempInfo() {
        this.AssignedNetworkInfo = false;
        this.AssignedCountryInfo = false;
        TryAssignInfo();
    }
}
