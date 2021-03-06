/*
 * AnimationBehaviour.java
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

import com.iver.andami.PluginServices;

/**
 * Indica el comportamiento de la animación.
 * 
 * @author rmartinez
 * 
 */
public enum AnimationBehaviour {

	/**
	 * Establece que la animación continuará reproduciendo indefinidamente hasta
	 * que sea detenida explícitamente.
	 */
	REPEAT(PluginServices.getText(AnimationBehaviour.class, "repeat")),
	/**
	 * Detiene la animación al consumirse el último instante de tiempo.
	 */
	STOP(PluginServices.getText(AnimationBehaviour.class, "stop")),
	/**
	 * Reproduce en orden inverso al actual al arribar al último o al primer
	 * instante de tiempo.
	 */
	REVERSE(PluginServices.getText(AnimationBehaviour.class, "reverse"));

	private String name;

	/**
	 * Crea una nueva de instancia de AnimationBehaviour. Se usa para introducir
	 * un nuevo comportamiento de la animación.
	 */
	private AnimationBehaviour(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
