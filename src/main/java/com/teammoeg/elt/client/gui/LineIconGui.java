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

package com.teammoeg.elt.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.teammoeg.elt.research.ResearchLine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Deprecated
public class LineIconGui extends AbstractGui {
    private static final int ICON_OFFSET = 128, ICON_SIZE = 26;
    private final ResourceLocation FRAMES = new ResourceLocation("textures/gui/advancements/widgets.png");
    private final Minecraft minecraft;
    private final ResearchLine researchLine;
    protected int x;
    protected int y;

    public LineIconGui(Minecraft mc, ResearchLine researchLine, int x, int y) {
        this.minecraft = mc;
        this.researchLine = researchLine;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void draw(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y) {
        int descWidth = this.minecraft.font.width(this.researchLine.getDesc());
        int nameWidth = 29 + this.minecraft.font.width(this.researchLine.getName()) + 8;

        this.minecraft.getTextureManager().bind(FRAMES);
        this.blit(matrixStack, x + this.x + 3, y + this.y, 0, this.isMouseOver(x, y, mouseX, mouseY) ? ICON_OFFSET : ICON_OFFSET + ICON_SIZE, ICON_SIZE, ICON_SIZE);
        if (isMouseOver(x, y, mouseX, mouseY)) {

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();

            this.minecraft.font.drawShadow(matrixStack, this.researchLine.getName(), (float) (x + this.x + 32), (float) (y + this.y + 9), -1);
            this.minecraft.font.drawShadow(matrixStack, this.researchLine.getDesc(), (float) (x + this.x + nameWidth - descWidth - 5), (float) (y + this.y + 9), -1);
        }

        this.minecraft.getItemRenderer().renderAndDecorateFakeItem(new ItemStack(this.researchLine.getIcon()), x + this.x + 8, y + this.y + 5);
    }

    public boolean isMouseOver(int x, int y, double mouseX, double mouseY) {
        int i = x + this.x + ICON_SIZE;
        int j = i + ICON_SIZE;
        int k = y + this.y + ICON_SIZE;
        int l = k + ICON_SIZE;
        return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
    }

    public ResearchLine getResearchLine() {
        return researchLine;
    }
}
