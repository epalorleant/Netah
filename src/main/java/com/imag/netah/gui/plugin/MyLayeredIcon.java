/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.plugin;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author epaln
 */
public class MyLayeredIcon extends ImageIcon {

	Set<Icon> iconSet;

	public MyLayeredIcon(Image image) {
	    super(image);
        this.iconSet = new LinkedHashSet<Icon>();
	}

        @Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
        super.paintIcon(c, g, x, y);
        Dimension d = new Dimension(getIconWidth(), getIconHeight());
		for (Icon icon : iconSet) {
			Dimension id = new Dimension(icon.getIconWidth(), icon.getIconHeight());
			int dx = x - (int)Math.round(id.getWidth()/2);//d.width - id.width;
			int dy = y - (int)Math.round(id.getHeight()/2);//d.height - id.height;
			//icon.paintIcon(c, g, x+dx, y+dy);
                        icon.paintIcon(c, g, dx, dy);
		}
	}

	public void add(Icon icon) {
		iconSet.add(icon);
	}

	public boolean remove(Icon icon) {
		return iconSet.remove(icon);
	}
        public void removeAll(){
             iconSet.clear();
        }
}
