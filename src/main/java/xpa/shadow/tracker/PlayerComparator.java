/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow.tracker;

import java.util.Comparator;

/**
 *
 * @author ShadowXPA
 */
public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        if (o1 == null || o2 == null)
            return 1;

        int o1I = Integer.parseInt(o1.getScore());
        int o2I = Integer.parseInt(o2.getScore());

        return -Integer.compare(o1I, o2I);
    }

}
