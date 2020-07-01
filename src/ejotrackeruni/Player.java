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
public class Player {

    private String Name;
    private String Ping;
    private String Score;
    private final boolean isBot;

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPing(String Ping) {
        this.Ping = Ping;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public String getName() {
        return Name;
    }

    public String getFormattedName() {
        String tmp = "<span class=\"_7\">" + this.Name + "</span>";

        if (this.NameContainsSpcChar()) {
            tmp = tmp.replace("^0", "</span><span class=\"_0\">");
            tmp = tmp.replace("^1", "</span><span class=\"_1\">");
            tmp = tmp.replace("^2", "</span><span class=\"_2\">");
            tmp = tmp.replace("^3", "</span><span class=\"_3\">");
            tmp = tmp.replace("^4", "</span><span class=\"_4\">");
            tmp = tmp.replace("^5", "</span><span class=\"_5\">");
            tmp = tmp.replace("^6", "</span><span class=\"_6\">");
            tmp = tmp.replace("^7", "</span><span class=\"_7\">");
            tmp = tmp.replaceFirst("</span>", "");
            tmp += "</span>";
            return tmp;
        } else {
            return tmp;
        }
    }

    private boolean NameContainsSpcChar() {
        return this.Name.contains("^0") || this.Name.contains("^1") || this.Name.contains("^2")
                || this.Name.contains("^3") || this.Name.contains("^4") || this.Name.contains("^5")
                || this.Name.contains("^6") || this.Name.contains("^7");
    }

    public String getPing() {
        return Ping;
    }

    public String getFormattedPing() {
        String tmp = "<span class=\"_ping\">" + this.Ping + "</span>";
        if (isBot()) {
            tmp += "<span class=\"_bot\"> [Bot]</span>";
        }
        return tmp;
    }

    public String getScore() {
        return Score;
    }

    public String getFormattedScore() {
        return "<span class=\"_score\">" + this.Score + "</span>";
    }

    public boolean isBot() {
        return isBot;
    }

    public String getFormattedPlayer() {
        return getFormattedName() + getFormattedScore() + getFormattedPing();
    }

    public Player(String Name, String Score, String Ping) {
        this.Name = Name;
        this.Score = Score;
        this.Ping = Ping;
        this.isBot = this.Ping.equals("0");
    }
}
