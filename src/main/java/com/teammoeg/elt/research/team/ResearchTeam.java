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

package com.teammoeg.elt.research.team;

import java.util.HashMap;
import java.util.UUID;

public class ResearchTeam {
    HashMap<UUID, Integer> players = new HashMap();

    private final String name;
    private int researchTeamXP = 0;

    public ResearchTeam(String name) {
        this.name = name;
        addToDatabase();
    }

    public int getResearchTeamXP() {
        return researchTeamXP;
    }

    public void setResearchTeamXP(int researchTeamXP) {
        this.researchTeamXP = researchTeamXP;
    }

    public void addResearchTeamXP(int amount) {
        this.researchTeamXP += amount;
    }

    public String getName() {
        return name;
    }

    public void addPlayer(UUID uuid) {
        players.put(uuid, 1);
    }

    public void addToDatabase() {
        ResearchTeamDatabase.TEAMS.put(name, this);
    }
}
