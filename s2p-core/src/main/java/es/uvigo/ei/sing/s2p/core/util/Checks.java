/*
 * #%L
 * S2P Core
 * %%
 * Copyright (C) 2016 - 2017 José Luis Capelo Martínez, José Eduardo Araújo, Florentino Fdez-Riverola, Miguel
 * 			Reboiro-Jato, Hugo López-Fernández, and Daniel Glez-Peña
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.s2p.core.util;

import java.util.function.IntFunction;

public class Checks {
	/**
	 * Checks if the provided number is greater than 0.
	 * 
	 * @param number the number to be checked.
	 * @param message the message of the exception thrown if the number is
	 * lower than or equals to 0.
	 * @return the provided number.
	 * @throws IllegalArgumentException if the number is lower than 0 or equals
	 * to 0. The message of this exception will be the provided.
	 */
	public static int requireStrictPositive(int number, String message) {
		return check(number, (int x) -> x > 0, message);
	}

	/**
	 * Checks if the provided number is greater than or 0.
	 * 
	 * @param number the number to be checked.
	 * @return the provided number.
	 * @throws IllegalArgumentException if the number is lower than or equals
	 * to 0.
	 */
	public static int requireStrictPositive(int number) {
		return requireStrictPositive(number, "number must be greater than 0");
	}

	private static int check(
			int value, IntFunction<Boolean> validator, String message
		) {
			if (validator.apply(value)) return value;
			else throw new IllegalArgumentException(message);
		}
}
