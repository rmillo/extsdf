
/*
 * TestNetCDFPanel.java
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * Copyright (C) 2014 Daynier Cardoso Roque <dcardoso@uclv.edu.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */
package uclv.gvsig.extsdf.properties;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import uclv.gvsig.extsdf.properties.panels.NetCDFPanel;



/**
 * Esta es una prueba que solo sirve para ver la interfaz visual no incluye la logica del panel
 * @author dcardoso
 *
 */
public class TestNetCDFPanel extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public TestNetCDFPanel(){
		setSize(500,390);
		NetCDFPanel panel = new NetCDFPanel();
		add(panel);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		} catch( Exception e ) {
			System.err.println( "No se puede cambiar al LookAndFeel");
		}
		new TestNetCDFPanel();
	}

}
