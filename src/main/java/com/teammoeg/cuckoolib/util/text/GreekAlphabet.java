/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.cuckoolib.util.text;

public class GreekAlphabet {
    private static final String[] UPPERCASE = {"", "\u0391", "\u0392", "\u0393", "\u0394", "\u0395", "\u0396",
            "\u0397", "\u0398", "\u0399", "\u039A", "\u039B", "\u039C", "\u039D", "\u039E", "\u039F", "\u03A0",
            "\u03A1", "\u03A3", "\u03A4", "\u03A5", "\u03A6", "\u03A7", "\u03A8", "\u03A9"};

    private static final String[] LOWERCASE = {"", "\u03B1", "\u03B2", "\u03B3", "\u03B4", "\u03B5", "\u03B6",
            "\u03B7", "\u03B8", "\u03B9", "\u03BA", "\u03BB", "\u03BC", "\u03BD", "\u03BE", "\u03BF", "\u03C0",
            "\u03C1", "\u03C3", "\u03C4", "\u03C5", "\u03C6", "\u03C7", "\u03C8", "\u03C9"};

    public static String getUppercase(int index) {
        return index >= 1 && index <= 24 ? UPPERCASE[index] : index + "";
    }

    public static String getLowercase(int index) {
        return index >= 1 && index <= 24 ? LOWERCASE[index] : index + "";
    }
}