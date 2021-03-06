/*
 * TimeSliderWindow.java
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
 * Copyright (C) 2013 Alexis Fajardo Moya <afmoya@uclv.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.timeslider;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.swing.JFileChooser;

import uclv.gvsig.extsdf.NetCDFConfiguration;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * <p>
 * Ventana con los controles y par&aacute;metros para el deslizador de tiempo de
 * la capa raster del formato NetCDF.
 * </p>
 * 
 * @author afmoya
 * @version 1.0.0
 */
public class TimeSliderWindow extends JPanel implements IWindow {
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo para definir la informaci&oacute;n de la ventana.
	 */
	private WindowInfo windowInfo = null;

	/**
	 * Ventana sobre la que act&uacute;a la ventana del deslizador de tiempo.
	 */
	private IWindow relatedWindow = null;

	/**
	 * Capa correspondiente a esta ventana de reproducción.
	 */
	private FLyrRasterSE layer;

	/**
	 * Dataset correspondiente a la capa.
	 */
	private NetCDFRasterDataset dataset = null;

	/**
	 * Controlador del archivo NetCDF correspondiente a la capa.
	 */
	private NetCDFController controller;

	/**
	 * Configuración del NetCDF correspondiente a la capa.
	 */
	private NetCDFConfiguration configuration;

	private JTextField infoField;
	private JButton optionsButton;
	private JButton exportButton;
	private JButton skipBackwardButton;
	private JSlider slider;
	private JButton skipForwardButton;
	private JButton playPauseButton;

	/**
	 * Almacena la animación del NetCDF correspondiente a esta ventana.
	 */
	private NetCDFAnimation animation;
	/**
	 * Establece si se está reproduciendo la animación actualmente.
	 */
	private boolean playing;

	/**
	 * Encargado de dar formato al tiempo de acuerdo a las especificaciones de
	 * la configuración.
	 */
	private SimpleDateFormat formatter;
	/**
	 * Tiempo actual indicado por el índice de la configuración.
	 */
	private Date currentDate;
	/**
	 * Almacena los formatos de tiempo disponibles.
	 */
	private DateTimeFormats formats;

	public TimeSliderWindow() {
		super();
		initialize();
	}

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowInfo()
	 */
	@Override
	public WindowInfo getWindowInfo() {
		// TODO Auto-generated method stub
		if (windowInfo == null) {
			windowInfo = new WindowInfo(WindowInfo.PALETTE);
			windowInfo.setWidth(this.getWidth());
			windowInfo.setHeight(this.getHeight());
			windowInfo.setTitle(PluginServices.getText(this,
					"time_slider_window_title")
					+ " -> "
					+ relatedWindow.getWindowInfo().getTitle());
		}

		return windowInfo;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowProfile()
	 */
	@Override
	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * Asignar a la ventana del deslizador de tiempo la ventana sobre la cual
	 * est&aacute; actuando.
	 * </p>
	 * 
	 * Esto se realiza para controlar si el componente puede estar activo o no
	 * para la correspondiente vista.
	 * 
	 * @param view
	 *            Vista sobre la cual act&uacute;a la ventana del deslizador de
	 *            tiempo.
	 */
	public void setRelatedWindow(IWindow iWindow) {
		relatedWindow = iWindow;
	}

	/**
	 * <p>
	 * Devuelve la ventana asociada a la ventana del deslizador de tiempo.
	 * </p>
	 * 
	 * Esto se realiza para controlar si la extensi&oacute;n estar&aacute;
	 * activo o no para la correspondiente vista.
	 * 
	 * @return IWindow Ventana asociada a la instancia del deslizador de tiempo.
	 */
	public IWindow getRelatedWindow() {
		return relatedWindow;
	}

	/**
	 * @param layer
	 *            the layer to set
	 */
	public void setLayer(FLyrRasterSE layer) {
		this.layer = layer;
		dataset = (NetCDFRasterDataset) layer.getDataSource().getDataset(0)[0];
		controller = dataset.getNetCDFController();
		configuration = dataset.getConfiguration();

		postInitialization();
	}

	/**
	 * Método de postinicialización, cuando la capa esté disponible.
	 */
	private void postInitialization() {
		getAnimationOptionsActionListener().setLayer(layer);

		if (configuration.getEndTime() == -1) {
			int endTime;
			try {
				endTime = (int) controller.getParameterForCoordinateSystem(
						controller.getCoordinateSystems()[controller
								.getCoordinateSystemIndex()]).getSize() - 1;
				configuration.setEndTime(endTime);
			} catch (IOException e) {
				configuration.setEndTime(0);
				e.printStackTrace();
			}
		}

		animation = new NetCDFAnimation(layer);
		animation.addAnimationListener(new SliderAnimationListener());

		slider.setMinimum(configuration.getStartTime());
		slider.setMaximum(configuration.getEndTime() + 1);
		slider.setValue(configuration.getVisualizemoment());

		formats = new DateTimeFormats();
		formatter = new SimpleDateFormat(
				formats.getDates()[configuration.getDateformat()]);
		updateCurrentDate();

		configuration.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource().equals("Time_Bounds")) {
					slider.setMinimum(configuration.getStartTime());
					slider.setMaximum(configuration.getEndTime() + 1);
					slider.setValue(configuration.getVisualizemoment());
					slider.updateUI();
				} else if (e.getSource().equals("Format")) {
					formatter.applyLocalizedPattern(formats.getDates()[configuration
							.getDateformat()]);
					updateCurrentDate();
				}
			}
		});
	}

	private void updateCurrentDate() {
		try {
			currentDate = controller.getParameterForCoordinateSystem(
					controller.getCoordinateSystems()[configuration
							.getSistemacoordenada()]).getTimeDate(
					configuration.getVisualizemoment());
			getInfoField().setText(formatter.format(currentDate));
			getSlider().setValue(configuration.getVisualizemoment());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class SliderAnimationListener implements AnimationListener {

		@Override
		public void animationStateChanged() {
			updateCurrentDate();
		}

	}

	/**
	 * @return the layer
	 */
	public FLyrRasterSE getLayer() {
		return layer;
	}

	/**
	 * @return the dataset
	 */
	public NetCDFRasterDataset getDataset() {
		return dataset;
	}

	/**
	 * <p>
	 * Inicializa los componentes visuales de la ventana.
	 * </p>
	 */
	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.anchor = GridBagConstraints.NORTH;
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		add(toolBar, gbc_toolBar);
		toolBar.add(getOptionsButton());
		toolBar.add(getExportButton());

		toolBar.add(getInfoField());
		getInfoField().setColumns(10);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 54, 200, 54, 44, 0 };
		gbl_panel.rowHeights = new int[] { 25, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		GridBagConstraints gbc_skipBackwardButton = new GridBagConstraints();
		gbc_skipBackwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_skipBackwardButton.insets = new Insets(0, 0, 0, 5);
		gbc_skipBackwardButton.gridx = 0;
		gbc_skipBackwardButton.gridy = 0;
		panel.add(getSkipBackwardButton(), gbc_skipBackwardButton);

		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.weightx = 10.0;
		gbc_slider.insets = new Insets(0, 0, 0, 5);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 0;
		panel.add(getSlider(), gbc_slider);

		GridBagConstraints gbc_skipForwardButton = new GridBagConstraints();
		gbc_skipForwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_skipForwardButton.insets = new Insets(0, 0, 0, 5);
		gbc_skipForwardButton.gridx = 2;
		gbc_skipForwardButton.gridy = 0;
		panel.add(getSkipForwardButton(), gbc_skipForwardButton);

		GridBagConstraints gbc_playPauseButton = new GridBagConstraints();
		gbc_playPauseButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_playPauseButton.gridx = 3;
		gbc_playPauseButton.gridy = 0;
		panel.add(getPlayPauseButton(), gbc_playPauseButton);

		setBounds(0, 0, 600, getPreferredSize().height);
	}

	/**
	 * @return the infoField
	 */
	private JTextField getInfoField() {
		if (infoField == null) {
			infoField = new JTextField();
			infoField.setBackground(Color.WHITE);
			infoField.setEditable(false);
			infoField.setHorizontalAlignment(JTextField.CENTER);
		}
		return infoField;
	}

	/**
	 * @return the optionsButton
	 */
	private JButton getOptionsButton() {
		if (optionsButton == null) {
			optionsButton = new JButton("");
			optionsButton.setAction(getAnimationOptionsActionListener());
		}
		return optionsButton;
	}

	/**
	 * @return the exportButton
	 */
	private JButton getExportButton() {
		if (exportButton == null) {
			exportButton = new JButton("");
			exportButton.addActionListener(new ExportButtonActionListener());
			exportButton.setIcon(PluginServices.getIconTheme()
					.get("video-icon"));
			exportButton.setToolTipText(PluginServices.getText(this,
					"export_video"));
		}
		return exportButton;
	}

	/**
	 * @return the skipBackwardButton
	 */
	private JButton getSkipBackwardButton() {
		if (skipBackwardButton == null) {
			skipBackwardButton = new JButton("");
			skipBackwardButton
					.addActionListener(new SkipBackwardButtonActionListener());
			skipBackwardButton.setIcon(PluginServices.getIconTheme().get(
					"skip-backward-icon"));
		}
		return skipBackwardButton;
	}

	/**
	 * @return the slider
	 */
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.addChangeListener(new SliderChangeListener());
			slider.addMouseListener(new SliderMouseListener());
			slider.addMouseMotionListener(new SliderMouseMotionListener());
		}
		return slider;
	}

	/**
	 * @return the skipForwardButton
	 */
	private JButton getSkipForwardButton() {
		if (skipForwardButton == null) {
			skipForwardButton = new JButton("");
			skipForwardButton
					.addActionListener(new SkipForwardButtonActionListener());
			skipForwardButton.setIcon(PluginServices.getIconTheme().get(
					"skip-forward-icon"));
		}
		return skipForwardButton;
	}

	/**
	 * @return the playPauseButton
	 */
	private JButton getPlayPauseButton() {
		if (playPauseButton == null) {
			playPauseButton = new JButton("");
			playPauseButton
					.addActionListener(new PlayPauseButtonActionListener());
			playPauseButton.setIcon(PluginServices.getIconTheme().get(
					"start-icon"));
		}
		return playPauseButton;
	}

	private AnimationOptionsAction animationOptionsActionListener;

	private AnimationOptionsAction getAnimationOptionsActionListener() {
		if (animationOptionsActionListener == null) {
			animationOptionsActionListener = new AnimationOptionsAction();
		}
		return animationOptionsActionListener;
	}

	private class PlayPauseButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (playing) {
				pause();
			} else {
				play();
			}
		}
	}

	private void pause() {
		playing = false;
		playPauseButton
				.setIcon(PluginServices.getIconTheme().get("start-icon"));
		animation.pause();
	}

	private void play() {
		playing = true;
		playPauseButton
				.setIcon(PluginServices.getIconTheme().get("pause-icon"));
		animation.play();
	}

	private class SkipBackwardButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			animation.moveBackward();
		}
	}

	private class SkipForwardButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			animation.moveForward();
		}
	}

	private boolean dragging = false;

	private class SliderMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			dragging = true;
		}
	}

	private class SliderMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging) {
				dragging = false;
				animation.move(getSlider().getValue());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (dragging == false) {
				animation.move(getSlider().getValue());
			}
		}
	}

	private class SliderChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if (getSlider().getValue() == getSlider().getMaximum() - 1) {
				switch (configuration.getAnimationBehaviour()) {
				case REPEAT:
					break;
				case REVERSE:
					animation.playInReverse();
					break;
				case STOP:
					pause();
					break;
				default:
					break;
				}
			} else if (getSlider().getValue() == getSlider().getMinimum()) {
				if (configuration.getAnimationBehaviour() == AnimationBehaviour.REVERSE) {
					animation.play();
				}
			}
		}
	}

	private class ExportButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("VIDEO_EXPORT",
					new File(""));
			int result = fileChooser.showSaveDialog(TimeSliderWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				animation.setOutputFile(fileChooser.getSelectedFile());
				animation.setRecording(true);
			}
		}
	}

}
