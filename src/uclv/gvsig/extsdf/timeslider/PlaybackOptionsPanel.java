/*
 * PlaybackOptionsPanel.java
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.iver.andami.PluginServices;

/**
 * Panel designed to adjust the Playback options of the animation.
 * 
 * @author rmartinez
 * 
 */
public class PlaybackOptionsPanel extends NetCDFOptionsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JRadioButton displayForEachRB;
	private JSlider speedSlider;
	private JPanel panel_1;
	private JLabel lblSpeed;
	private JRadioButton playSpecifiedRB;
	private JPanel panel_2;
	private JSpinner durationSpinner;
	private JComboBox actionAfterPlayingCB;
	private JLabel afterPlayingOnceLabel;

	/**
	 * Create the panel.
	 */
	public PlaybackOptionsPanel() {
		setLabel(PluginServices.getText(this, "playback")); //$NON-NLS-1$
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null,
				"", TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(5, 5, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_displayForEachRB = new GridBagConstraints();
		gbc_displayForEachRB.anchor = GridBagConstraints.WEST;
		gbc_displayForEachRB.insets = new Insets(5, 5, 5, 5);
		gbc_displayForEachRB.gridx = 0;
		gbc_displayForEachRB.gridy = 0;
		panel.add(getDisplayForEachRB(), gbc_displayForEachRB);

		panel_1 = new JPanel();
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		lblSpeed = new JLabel(PluginServices.getText(this, "speed")); //$NON-NLS-1$
		panel_1.add(lblSpeed);

		panel_1.add(getSpeedSlider());

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(5, 5, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		panel_2.add(getPlaySpecifiedRB());
		panel_2.add(getDurationSpinner());

		afterPlayingOnceLabel = new JLabel(PluginServices.getText(this,
				"after_playing_once")); //$NON-NLS-1$
		GridBagConstraints gbc_afterPlayingOnceLabel = new GridBagConstraints();
		gbc_afterPlayingOnceLabel.insets = new Insets(5, 5, 5, 5);
		gbc_afterPlayingOnceLabel.anchor = GridBagConstraints.WEST;
		gbc_afterPlayingOnceLabel.gridx = 0;
		gbc_afterPlayingOnceLabel.gridy = 1;
		add(afterPlayingOnceLabel, gbc_afterPlayingOnceLabel);

		GridBagConstraints gbc_actionAfterPlayingCB = new GridBagConstraints();
		gbc_actionAfterPlayingCB.insets = new Insets(5, 5, 5, 0);
		gbc_actionAfterPlayingCB.anchor = GridBagConstraints.WEST;
		gbc_actionAfterPlayingCB.gridx = 1;
		gbc_actionAfterPlayingCB.gridy = 1;
		add(getActionAfterPlayingCB(), gbc_actionAfterPlayingCB);

		getPlayButtonGroup();

		setPreferredSize(getPreferredSize());
	}

	/**
	 * @return the speed slider
	 */
	private Component getSpeedSlider() {
		if (speedSlider == null) {
			speedSlider = new JSlider();
			speedSlider.setMaximum(1500);
			speedSlider.setEnabled(displayForEachRB.isSelected());
		}
		return speedSlider;
	}

	/**
	 * @return the displayForEachRB
	 */
	private JRadioButton getDisplayForEachRB() {
		if (displayForEachRB == null) {
			displayForEachRB = new JRadioButton(PluginServices.getText(this,
					"display_for_each_timestamp")); //$NON-NLS-1$
			displayForEachRB
					.addItemListener(new DisplayForEachRBItemListener());
			displayForEachRB.setSelected(true);
		}
		return displayForEachRB;
	}

	/**
	 * @return the playSpecifiedRB
	 */
	private JRadioButton getPlaySpecifiedRB() {
		if (playSpecifiedRB == null) {
			playSpecifiedRB = new JRadioButton(PluginServices.getText(this,
					"play_in_specified_duration")); //$NON-NLS-1$
			playSpecifiedRB.addItemListener(new PlaySpecifiedRBItemListener());
		}
		return playSpecifiedRB;
	}

	/**
	 * @return the durationSpinner
	 */
	private JSpinner getDurationSpinner() {
		if (durationSpinner == null) {
			durationSpinner = new JSpinner();
			durationSpinner.setModel(new SpinnerNumberModel(new Integer(1),
					new Integer(1), null, new Integer(1)));
			durationSpinner
					.setMaximumSize(new Dimension(50, Integer.MAX_VALUE));
			durationSpinner.setEnabled(playSpecifiedRB.isSelected());
		}
		return durationSpinner;
	}

	private ButtonGroup playButtonGroup;

	/**
	 * @return the actionAfterPlayingCB
	 */
	private JComboBox getActionAfterPlayingCB() {
		if (actionAfterPlayingCB == null) {
			actionAfterPlayingCB = new JComboBox(AnimationBehaviour.values());
		}
		return actionAfterPlayingCB;
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

	private int minimum_delay = 99;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {
		if (displayForEachRB.isSelected()) {
			configuration
					.setAnimationBehaviour((AnimationBehaviour) getActionAfterPlayingCB()
							.getSelectedItem());
			int delay = speedSlider.getMaximum() + minimum_delay
					- speedSlider.getValue();
			configuration.setDelayPeriod(delay);
		} else {
			int seconds = (Integer) durationSpinner.getValue();
			int size = configuration.getEndTime()
					- configuration.getStartTime() + 1;
			float delay = (float) seconds * 1000 / size;
			if(delay <= 0) delay = 1;
			configuration.setDelayPeriod((int) delay);
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

	private class PlaySpecifiedRBItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			getDurationSpinner().setEnabled(getPlaySpecifiedRB().isSelected());
		}
	}

	private class DisplayForEachRBItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			getSpeedSlider().setEnabled(getDisplayForEachRB().isSelected());
		}
	}

	private ButtonGroup getPlayButtonGroup() {
		if (playButtonGroup == null) {
			playButtonGroup = new ButtonGroup();
			playButtonGroup.add(getPlaySpecifiedRB());
			playButtonGroup.add(getDisplayForEachRB());
		}
		return playButtonGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclv.gvsig.extsdf.timeslider.NetCDFOptionsPanel#postInitialize()
	 */
	@Override
	protected void postInitialize() {
		actionAfterPlayingCB.setSelectedItem(configuration
				.getAnimationBehaviour());
		speedSlider.setValue(speedSlider.getMaximum() + minimum_delay
				- configuration.getDelayPeriod());
	}
}
