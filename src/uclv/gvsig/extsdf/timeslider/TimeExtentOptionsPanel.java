/*
 * TimeExtentOptionsPanel.java
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
 * Copyright (C) 2014 Rainer Martinez Fraga <rmartinez@uclv.edu.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.timeslider;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.gvsig.raster.dataset.serializer.RmfSerializerException;

import uclv.gvsig.extsdf.NetCDFConfiguration;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 * 
 */
public class TimeExtentOptionsPanel extends NetCDFOptionsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox startTimeField;
	private JComboBox endTimeField;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;

	/**
	 * Create the panel.
	 */
	public TimeExtentOptionsPanel() {
		setLabel("TimeExtent");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 60, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_startTimeLabel = new GridBagConstraints();
		gbc_startTimeLabel.insets = new Insets(5, 5, 5, 5);
		gbc_startTimeLabel.anchor = GridBagConstraints.WEST;
		gbc_startTimeLabel.gridx = 0;
		gbc_startTimeLabel.gridy = 0;
		add(getStartTimeLabel(), gbc_startTimeLabel);

		GridBagConstraints gbc_startTimeField = new GridBagConstraints();
		gbc_startTimeField.weightx = 1.0;
		gbc_startTimeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_startTimeField.insets = new Insets(5, 5, 5, 0);
		gbc_startTimeField.gridx = 1;
		gbc_startTimeField.gridy = 0;
		add(getStartTimeField(), gbc_startTimeField);
		GridBagConstraints gbc_endTimeLabel = new GridBagConstraints();
		gbc_endTimeLabel.insets = new Insets(5, 5, 0, 5);
		gbc_endTimeLabel.anchor = GridBagConstraints.WEST;
		gbc_endTimeLabel.gridx = 0;
		gbc_endTimeLabel.gridy = 1;
		add(getEndTimeLabel(), gbc_endTimeLabel);

		GridBagConstraints gbc_endTimeField = new GridBagConstraints();
		gbc_endTimeField.weightx = 1.0;
		gbc_endTimeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_endTimeField.insets = new Insets(5, 5, 0, 0);
		gbc_endTimeField.gridx = 1;
		gbc_endTimeField.gridy = 1;
		add(getEndTimeField(), gbc_endTimeField);
		setPreferredSize(getPreferredSize());
	}

	/**
	 * @return the startTimeField
	 */
	private JComboBox getStartTimeField() {
		if (startTimeField == null) {
			startTimeField = new JComboBox();
		}
		return startTimeField;
	}

	/**
	 * @return the endTimeField
	 */
	private JComboBox getEndTimeField() {
		if (endTimeField == null) {
			endTimeField = new JComboBox();
		}
		return endTimeField;
	}

	private JLabel getStartTimeLabel() {
		if (startTimeLabel == null) {
			startTimeLabel = new JLabel(PluginServices.getText(this,
					"start_time")); //$NON-NLS-1$
		}
		return startTimeLabel;
	}

	private JLabel getEndTimeLabel() {
		if (endTimeLabel == null) {
			endTimeLabel = new JLabel(PluginServices.getText(this, "end_time")); //$NON-NLS-1$
		}
		return endTimeLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#accept()
	 */
	@Override
	public void accept() {
		apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {
		configuration.setStartTime(startTimeField.getSelectedIndex());
		configuration.setEndTime(endTimeField.getSelectedIndex());
		
		try {
			dataset.saveObjectToRmf(NetCDFConfiguration.class, configuration);
		} catch (RmfSerializerException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#cancel()
	 */
	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#selected()
	 */
	@Override
	public void selected() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#initialize()
	 */
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclv.gvsig.extsdf.timeslider.NetCDFOptionsPanel#postInitialize()
	 */
	@Override
	protected void postInitialize() {
		try {
			startTimeField.setModel(new DefaultComboBoxModel(controller
					.getParameterForCoordinateSystem(
							controller.getCoordinateSystems()[controller
									.getCoordinateSystemIndex()]).getTimeDates()));
			startTimeField.setSelectedIndex(configuration.getStartTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			endTimeField.setModel(new DefaultComboBoxModel(controller
					.getParameterForCoordinateSystem(
							controller.getCoordinateSystems()[controller
									.getCoordinateSystemIndex()]).getTimeDates()));
			endTimeField.setSelectedIndex(configuration.getEndTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
