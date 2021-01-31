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

package com.teammoeg.elt.util;

import com.teammoeg.the_seed.code.ModData;
import com.teammoeg.the_seed.data.MD;
import net.minecraft.util.ResourceLocation;

/**
 * @author YueSha (GitHub @yuesha-yc)
 * Json Utils
 */
public class ResourceJsonUtil {
    public static String createItemModelJson(ResourceLocation id, String type) {
        return createItemModelJson(MD.ELT, id, type);
    }

    public static String createItemModelJson(ModData aModData, ResourceLocation id, String type) {
        if ("generated".equals(type) || "handheld".equals(type)) {
            //The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.
            return "{\n" +
                    "  \"parent\": \"item/" + type + "\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"" + aModData.mID + ":" + id.getPath() + "\"\n" +
                    "  }\n" +
                    "}";
        } else if ("block".equals(type)) {
            //However, if the item is a block-item, it will have a different model json than the previous two.
            return "{\n" +
                    "  \"parent\": \"" + aModData.mID + ":" + id.getPath() + "\"\n" +
                    "}";
        } else {
            //If the type is invalid, return an empty json string.
            return "";
        }
    }

    public static String createBlockModelJson(ModData aModData, ResourceLocation id, String type) {
        if ("generated".equals(type) || "handheld".equals(type)) {
            //The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.
            return "{\n" +
                    "  \"parent\": \"item/" + type + "\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"" + aModData.mID + ":" + id.getPath() + "\"\n" +
                    "  }\n" +
                    "}";
        } else if ("block".equals(type)) {
            //However, if the item is a block-item, it will have a different model json than the previous two.
            return "{\n" +
                    "  \"parent\": \"" + aModData.mID + ":" + id.getPath() + "\"\n" +
                    "}";
        } else {
            //If the type is invalid, return an empty json string.
            return "";
        }
    }
}
