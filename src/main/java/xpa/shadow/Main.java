/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow;

import xpa.shadow.tracker.Constants;
import xpa.shadow.tracker.TrackerForm;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author ShadowXPA
 */
public class Main {

    public static TrayIcon tray;
    public static SystemTray sysTray;

    // Lock only 1 instance
    private static final int LOCK_PORT = 60000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            CheckIfItsAlreadyRunning();
            CheckIfThereIsUpdate();
            TrackerForm tracker;
            if (SystemTray.isSupported()) {
                sysTray = SystemTray.getSystemTray();
                tray = new TrayIcon(Toolkit.getDefaultToolkit()
                        .getImage(TrackerForm.class.getClassLoader().getResource("ejo.gif")),
                        Constants.APP_NAME_VERSION);
                tray.setImageAutoSize(true);

                // SYSTEM TRAY POPUP MENU
                PopupMenu pop = new PopupMenu();
                if (Desktop.isDesktopSupported()) {
                    MenuItem EJOSite = new MenuItem("Author...");
                    EJOSite.addActionListener((ActionEvent e) -> {
                        try {
                            Desktop.getDesktop().browse(new URI(Constants.AUTHOR_WEBSITE));
                        } catch (URISyntaxException | IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    MenuItem EJOTrackerSite = new MenuItem("EJO Tracker Website");
                    EJOTrackerSite.addActionListener((ActionEvent e) -> {
                        try {
                            Desktop.getDesktop().browse(new URI(Constants.EJO_TRACKER_LINK));
                        } catch (URISyntaxException | IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    MenuItem EJOPatchNotes = new MenuItem("Patch notes");
                    EJOPatchNotes.addActionListener((ActionEvent e) -> {
                        try {
                            Desktop.getDesktop().browse(new URI(Constants.EJO_APP_PATCH_NOTES));
                        } catch (URISyntaxException | IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    pop.add(EJOSite);
                    pop.addSeparator();
                    pop.add(EJOTrackerSite);
                    pop.add(EJOPatchNotes);
                    pop.addSeparator();
                }
                MenuItem mi = new MenuItem("Exit");
                mi.addActionListener((ActionEvent e) -> {
                    sysTray.remove(tray);
                    System.exit(0);
                });
                pop.add(mi);
                tray.setPopupMenu(pop);
                sysTray.add(tray);
            }

            tracker = new TrackerForm(tray);
            tracker.setLocationRelativeTo(null);
            tracker.setVisible(true);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-2);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-3);
        }
    }

    private static void CheckIfItsAlreadyRunning() {
        try {
            ServerSocket socket = new ServerSocket(LOCK_PORT, 0, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        } catch (BindException ex) {
            JOptionPane.showMessageDialog(new Frame(), "Error: An instance of this application is already running.\nYou may not run multiple instances of this application.", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(3);
        }
    }

    public static void CheckIfThereIsUpdate() {
        try {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) new URL(Constants.EJO_APP_NEWEST_VERSION).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(10000);
            con.setDoInput(true);
            con.connect();
            int code = con.getResponseCode();
            if (code == 200) {
                InputStream IS = con.getInputStream();
                BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
                String data;
                while ((data = BR.readLine()) != null) {
                    sb.append(data);
                }
                String data2 = sb.toString();
                if (!data2.equals(Constants.APP_VERSION)) {
                    try {
                        String[] data2S = data2.split("\\.");
                        String[] appVS = Constants.APP_VERSION.split("\\.");
                        int[] cV = new int[8];
                        for (int i = 0; i < 4; i++) {
                            cV[i] = Integer.parseInt(data2S[i]);
                            cV[i + 4] = Integer.parseInt(appVS[i]);
                        }
                        if ((cV[0] > cV[4]) || (cV[0] == cV[4] && cV[1] > cV[5]) || (cV[0] == cV[4] && cV[1] == cV[5] && cV[2] > cV[6]) || (cV[0] == cV[4] && cV[1] == cV[5] && cV[2] == cV[6] && cV[3] > cV[7])) {
                            JOptionPane.showMessageDialog(new Frame(), "A new version is available.\nPlease download it at:\n" + Constants.DOWNLOAD_LINK + "\n\nThis version: " + Constants.APP_VERSION + "\nNew version: " + data2 + "\n\nTo install, simply delete the old version completely\nand download the new one.", "New Version Available!", JOptionPane.INFORMATION_MESSAGE);
                            if (Desktop.isDesktopSupported()) {
                                try {
                                    Desktop.getDesktop().browse(new URI(Constants.DOWNLOAD_LINK));
                                } catch (URISyntaxException | IOException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, null, ex);
                                }
                            }
                            System.exit(333);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new Frame(), "Error: Could not evaluate version.\nYou may need an internet connection,\nor the website is down.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, null, ex);
            JOptionPane.showMessageDialog(new Frame(), "Error: Could not evaluate version.\nYou may need an internet connection,\nor the website is down.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
