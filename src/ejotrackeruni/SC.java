/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejotrackeruni;

/**
 *
 * @author ShadowXPA
 */
public final class SC {
    // Prev 1.2.0.2
    // Next 1.3.2.0
    // Added options menu (can now change settings in real time)
    public static final String APPLICATION_VERSION = "v1.3.2.0";
    public static final int APP_VERSION = Integer.parseInt(SC.APPLICATION_VERSION.substring(1).replaceAll("\\.", ""));
    public static final String Unk = "Unknown";
    public static final String ServerIP = "23.226.135.154";
    public static final int ServerPort = 29070;
    public static final String ServerName = "[EJO]Eternal Jedi Order";
    public static final int RefreshTime = 30000;
    public static final int MinMinutes = 30;
    public static final String AppName = "EJO Tracker Universal " + SC.APPLICATION_VERSION;
    public static final String AppJoinName = SC.AppName + " - ";
    public static final String Credits = "Credits";
    public static final String Options = "Options";
    
    public static final String PLAYER_INFO_HTML_1 = "<html><style>._01 { color: #000000; } ._0 { color: #000000; } ._1 { color: #ff0000; } ._2 { color: #00ff00; }"
            + " ._3 { color: #ffff00; } ._4 { color: #0000ff; }"
            + " ._5 { color: #00ffff; } ._6 { color: #ff00ff; } ._7 { color: #ffffff; } ._bot { color: #000000; font-weight: bold; } </style><body>";
    public static final String PLAYER_INFO_HTML_2 = "</body></html>";
    
    public static final int EJO_IMG_WIDTH = 417;
    public static final int EJO_IMG_HEIGHT = 439;
    
    public static final String EJO_WEBSITE = "http://ejoclan.ga/";
    public static final String DOWNLOAD_LINK = "https://www.mediafire.com/file/z5c6gsuk0e8zsay";
    public static final String DOWNLOAD_LINK2 = "https://ejotracker.000webhostapp.com/Universal/";
    public static final String EJO_TRACKER_LINK = "https://ejotracker.000webhostapp.com/";
    public static final String EJO_CHECK_APP_VERSION = "https://ejotracker.000webhostapp.com/Universal/CurrentVersion";
    public static final String EJO_CHECK_APP_PATCH_NOTES = "https://ejotracker.000webhostapp.com/Universal/PatchNotes";
    public static final String EJO_CHECK_SERVER_INFO = "https://ejotracker.000webhostapp.com/Universal/EJOServerInfo";
    
    private SC() {}
}
