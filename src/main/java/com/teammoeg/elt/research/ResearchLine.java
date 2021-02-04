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

package com.teammoeg.elt.research;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ResearchLine {
    private ResourceLocation id;
    private final TranslationTextComponent name;
    private final TranslationTextComponent desc;
    private final Item icon;

    public ResearchLine(ResourceLocation id, Item icon) {
        this.id = id;
        this.name = new TranslationTextComponent(id.getNamespace() + "." + id.getPath() + ".name");
        this.desc = new TranslationTextComponent(id.getNamespace() + "." + id.getPath() + ".desc");
        this.icon = icon;
    }

    public Item getIcon() {
        return icon;
    }

    public TranslationTextComponent getDesc() {
        return desc;
    }

    public TranslationTextComponent getName() {
        return name;
    }

    public ResourceLocation getId() {
        return id;
    }
}
