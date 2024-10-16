/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow.tracker;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ShadowXPA
 */
public class mJPanel extends JPanel {

    private Image img;
    
    public mJPanel(LayoutManager layout, boolean isDoubleBuffered) {
        this("ejol0g0opacity3.png");
    }

    public mJPanel(LayoutManager layout) {
        this("ejol0g0opacity3.png");
    }

    public mJPanel(boolean isDoubleBuffered) {
        this("ejol0g0opacity3.png");
    }

    public mJPanel() {
        this("ejol0g0opacity3.png");
    }
    
    public mJPanel(String imgUrl) {
        super();
        this.img = Toolkit.getDefaultToolkit().getImage(TrackerForm.class.getClassLoader().getResource(imgUrl));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
