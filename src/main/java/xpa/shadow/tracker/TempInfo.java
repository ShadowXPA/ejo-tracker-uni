/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow.tracker;

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
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
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
    private int ServerPort;
    private String Country;
    private String CountryCode;
    private String Region;

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
        if (Minutes >= Constants.MIN_MINUTES) {
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
        if (RefreshTime >= Constants.REFRESH_TIME) {
            this.RefreshTime = RefreshTime;
        }
    }

    public String getTrackerMessage() {
        return this.TrackerMessage;
    }

    public String getServerIP() {
        if (this.isAssignedNetworkInfo()) {
            return ServerIP;
        } else {
            return Constants.SERVER_IP;
        }
    }

    public void setServerIP(String serverIP) {
        ServerIP = serverIP;
        SetCountryAndRegion();
    }

    public int getServerPort() {
        if (this.isAssignedNetworkInfo()) {
            return ServerPort;
        } else {
            return Constants.SERVER_PORT;
        }
    }

    public void setServerPort(int serverPort) {
        ServerPort = serverPort;
    }

    public String getCountry() {
        if (this.isAssignedCountryInfo()) {
            return Country;
        } else {
            return Constants.UNKNOWN;
        }
    }

    public String getCountryCode() {
        if (this.isAssignedCountryInfo()) {
            return CountryCode;
        } else {
            return Constants.UNKNOWN;
        }
    }

    public String getRegion() {
        if (this.Region != null && !this.Region.isEmpty()) {
            return Region;
        } else {
            return Constants.UNKNOWN;
        }
    }

    public boolean isAssignedNetworkInfo() {
        return !this.ServerIP.equals(Constants.UNKNOWN);
    }

    public boolean isAssignedCountryInfo() {
        return !this.Country.equals(Constants.UNKNOWN) && !this.CountryCode.equals(Constants.UNKNOWN);
    }

    public void TrySaveInfo() {
        File fl = new File("config.json");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
            JSONObject jo = new JSONObject();

            jo.put("ServerIP", this.ServerIP);
            jo.put("ServerPort", this.ServerPort);
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
        File fl = new File("config.json");

        this.ServerIP = Constants.SERVER_IP;
        this.ServerPort = Constants.SERVER_PORT;
        this.Country = Constants.UNKNOWN;
        this.CountryCode = Constants.UNKNOWN;
        this.Region = Constants.UNKNOWN;

        this.RefreshTime = Constants.REFRESH_TIME;
        this.AllowNotifications = true;
        this.AllowMultipleNotifications = true;
        this.AdvancedNotificationMode = false;
        this.BodyParkedOnServerMode = false;
        this.Minutes = Constants.MIN_MINUTES;
        this.LogPlayers = false;

        // Get from website
        RefreshTI();

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
                    this.ServerIP = jo.getString("ServerIP");
                } catch (JSONException ignored) {
                }
                try {
                    this.ServerPort = jo.getInt("ServerPort");
                } catch (JSONException ignored) {
                }
                try {
                    int tmp1 = jo.getInt("RefreshTime");
                    if (tmp1 >= Constants.REFRESH_TIME) {
                        this.RefreshTime = tmp1;
                    } else {
                        this.RefreshTime = Constants.REFRESH_TIME;
                    }
                } catch (JSONException ignored) {
                    this.RefreshTime = Constants.REFRESH_TIME;
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
                    if (this.Minutes < Constants.MIN_MINUTES) {
                        this.Minutes = Constants.MIN_MINUTES;
                    }
                } catch (JSONException ignored) {
                    this.Minutes = Constants.MIN_MINUTES;
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

        if (isAssignedNetworkInfo()) {
            SetCountryAndRegion();
        }
    }

    public void RefreshTI() {
        try {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) new URL(Constants.EJO_SERVER_MESSAGE).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(10000);
            con.setDoInput(true);
            con.connect();
            int code = con.getResponseCode();
            if (code == 200) {
                InputStream IS = con.getInputStream();
                BufferedReader BR = new BufferedReader(new InputStreamReader(IS, StandardCharsets.UTF_8));
                String data;
                if ((data = BR.readLine()) != null) {
                    sb.append(data);
                }
                this.TrackerMessage = sb.toString();
            }
        } catch (Exception ex) {
            Logger.getLogger(TempInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SetCountryAndRegion() {
        this.Country = Constants.UNKNOWN;
        this.Region = Constants.UNKNOWN;
        this.CountryCode = Constants.UNKNOWN;

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://api.ipfind.com/?ip=" + this.ServerIP);
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
                String ctry = Constants.UNKNOWN;
                String ctryCod = Constants.UNKNOWN;
                String reg = Constants.UNKNOWN;
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

                if (!ctry.equals(Constants.UNKNOWN)) {
                    this.Country = ctry;
                }
                if (!ctryCod.equals(Constants.UNKNOWN)) {
                    this.CountryCode = ctryCod.toLowerCase();
                }
                if (!reg.equals(Constants.UNKNOWN)) {
                    this.Region = reg;
                }
            } catch (JSONException ignored) {
            }

        } catch (IOException | JSONException ignored) {
        }
    }

    public TempInfo() {
        TryAssignInfo();
    }
}
