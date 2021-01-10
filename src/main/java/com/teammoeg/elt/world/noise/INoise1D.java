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

package com.teammoeg.elt.world.noise;

/**
 * Wrapper for a 1D Noise Layer
 */
@FunctionalInterface
public interface INoise1D
{
    static INoise1D triangle(float amplitude, float midpoint, float frequency, float phaseShift)
    {
        return q -> triangle(amplitude, midpoint, frequency, phaseShift, q);
    }

    static float triangle(float amplitude, float midpoint, float frequency, float phaseShift, float q)
    {
        float p = phaseShift + frequency * q;
        return midpoint + amplitude * (Math.abs(2f * p + 1f - 4f * NoiseUtil.fastFloor(p / 2f + 0.75f)) - 1f);
    }


    float noise(float in);

    /**
     * Applies a transformation to the input coordinates. This is useful for clamp / scale operations on the input coordinates.
     *
     * @param transform the input function
     * @return a new noise function
     */
    default INoise1D transformed(INoise1D transform)
    {
        return in -> INoise1D.this.noise(transform.noise(in));
    }

    /**
     * Extends to a 2D noise layer
     *
     * @return a new noise function
     */
    default INoise2D extendX()
    {
        return (x, y) -> noise(y);
    }

    /**
     * Extends to a 2D noise layer
     *
     * @return a new noise function
     */
    default INoise2D extendY()
    {
        return (x, y) -> noise(x);
    }
}
