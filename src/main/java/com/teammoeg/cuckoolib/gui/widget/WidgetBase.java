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

package com.teammoeg.cuckoolib.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.cuckoolib.client.render.IWidgetRenderer;
import com.teammoeg.cuckoolib.gui.ISyncedWidgetList;
import com.teammoeg.cuckoolib.gui.ModularGuiInfo;
import com.teammoeg.cuckoolib.util.math.Vector2i;

public class WidgetBase implements IWidget {
    protected ModularGuiInfo guiInfo;
    protected ISyncedWidgetList widgetList;
    protected Vector2i size, position;
    protected IWidgetRenderer renderer;
    protected int id;
    protected boolean isEnable;

    public WidgetBase(int x, int y, int width, int height) {
        this(Vector2i.of(x, y), Vector2i.of(width, height));
    }

    public WidgetBase(Vector2i position, Vector2i size) {
        this.position = position == null ? Vector2i.ORIGIN : position;
        this.size = size == null ? Vector2i.ORIGIN : size;
        this.isEnable = true;
    }

    public WidgetBase setRenderer(IWidgetRenderer texture) {
        this.renderer = texture;
        return this;
    }

    @Override
    public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft,
                                 int guiTop) {
        if (this.isEnable && this.renderer != null) {
            this.renderer.draw(transform, guiLeft + this.position.getX(), guiTop + this.position.getY(),
                    this.size.getX(), this.size.getY());
        }
    }

    @Override
    public ModularGuiInfo getGuiInfo() {
        return this.guiInfo;
    }

    @Override
    public void setGuiInfo(ModularGuiInfo info) {
        this.guiInfo = info;
    }

    @Override
    public ISyncedWidgetList getWidgetList() {
        return this.widgetList;
    }

    @Override
    public void setWidgetList(ISyncedWidgetList list) {
        this.widgetList = list;
    }

    @Override
    public int getWidgetId() {
        return this.id;
    }

    @Override
    public void setWidgetId(int id) {
        this.id = id;
    }

    @Override
    public Vector2i getSize() {
        return this.size;
    }

    @Override
    public void setSize(Vector2i size) {
        this.size = size;
    }

    @Override
    public Vector2i getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2i position) {
        this.position = position;
    }

    @Override
    public boolean isEnable() {
        return this.isEnable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.isEnable = enable;
    }
}