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

package com.teammoeg.elt.item;

import com.teammoeg.elt.ELT;
import net.minecraft.item.Item;

public class ELTItemBase extends Item {
    public String name;

    public ELTItemBase(String name, Properties properties) {
        super(properties);
        this.name = name;
        this.setRegistryName(name);
        ELT.RegistryEvents.registeredItems.add(this);
    }
}
