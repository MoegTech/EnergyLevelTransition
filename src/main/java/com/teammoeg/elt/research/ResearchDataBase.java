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

import net.minecraft.nbt.ListNBT;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ResearchDataBase {
    public static final ResearchDataBase RESEARCH_DATA_BASE = new ResearchDataBase();

    public synchronized ListNBT writeProgressToNBT(ListNBT json, @Nullable List<UUID> users) {
/*       for(Research research : this.getEntries()) {
            CompoundNBT jq = new CompoundNBT();
            jq.putString("questID", Research.getId());
            json.appendTag(jq);
        }*/
        return json;
    }
}
