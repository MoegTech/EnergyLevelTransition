/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.block;

import com.teammoeg.elt.block.world.ELTGrassBlock;
import net.minecraft.block.Block;

public class ELTBlocks {
    public static final Block RESEARCH_DESK = new ResearchDeskBlock("research_desk");
    public static final Block ELTGRASSBLOCK = new ELTGrassBlock("elt_grass_block");

    public static void init() {
    }

}
