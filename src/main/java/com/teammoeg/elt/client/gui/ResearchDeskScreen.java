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
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.capability.ELTCapabilities;
import com.teammoeg.elt.capability.ITeamCapability;
import com.teammoeg.elt.container.ResearchDeskContainer;
import com.teammoeg.elt.research.ELTResearches;
import com.teammoeg.elt.research.ResearchLine;
import com.teammoeg.elt.research.team.ResearchTeamDatabase;
import com.teammoeg.the_seed.api.gui.GuiUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class ResearchDeskScreen extends ContainerScreen<ResearchDeskContainer> {
    // Window texture data
    private static final int
            WIDTH = 252,
            HEIGHT = 140,
            CORNER_SIZE = 30;

    // Inventory texture data
    private static final int
            INV_WIDTH = 252,
            INV_HEIGHT = 87;

    // These represent the distance from the border of the WindowGui to the border of the GameScreen
    private static final int
            SIDE = 10,
            TOP = 10,
            BOTTOM = 87,
            PADDING = 9;

    // Zoom data
    private static final float
            MIN_ZOOM = 1,
            MAX_ZOOM = 2,
            ZOOM_STEP = 0.2F;

    // Texture locations
    private static final ResourceLocation
            WINDOW = new ResourceLocation(ELT.MOD_ID, "textures/gui/vanilla_window.png"),
            INVENTORY = new ResourceLocation(ELT.MOD_ID, "textures/gui/window.png"),
            BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png"),
            RESEARCH_ICONS = new ResourceLocation("textures/gui/advancements/widgets.png"),
            SWITCH_PAGE_BUTTON = new ResourceLocation(ELT.MOD_ID, "textures/gui/research/line_button.png"),
            INSIDE_BACKGROUND = new ResourceLocation("textures/block/netherite_block.png");

    // children
    private ArrayList<ResearchIconGui> widgets = new ArrayList<>();
    private ArrayList<LineIconGui> lineIcons = new ArrayList<>();
    private ResearchLineList lineList;

    // Dragging/Zooming related
    private float zoom = MIN_ZOOM;
    private boolean isScrolling;
    private double scrollX, scrollY;
    private boolean centered;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private boolean isLinePage = true;
    private ResearchLine selectedLine;

    // Animated sidebar related
    private long sidebarAnimationStartTime;
    private boolean sidebarActivatedLastTime = false;
    private float sidebarScrollAmt = 0;


    public ResearchDeskScreen(ResearchDeskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        this.lineList = new ResearchLineList(this, minecraft, 68, height - BOTTOM - TOP, TOP, height - BOTTOM, 18);
        this.children.add(lineList);
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.PALEOLITHIC_AGE, 48, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.NEOLITHIC_AGE, 48 * 2, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.COPPER_AGE, 48 * 3, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.BRONZE_AGE, 48 * 4, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.IRON_AGE, 48 * 5, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.STEEL_AGE, 48 * 6, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.STEAM_AGE, 48 * 7, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.ELECTRIC_AGE, 48 * 8, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.ATOMIC_AGE, 48 * 9, 48));
        addResearchLineIcon(new LineIconGui(this.minecraft, ELTResearches.SPACE_AGE, 48 * 10, 48));

        addResearchIcon(new ResearchIconGui(this.minecraft, ELTResearches.FIRST_RESEARCH, 48, 48));
        addResearchIcon(new ResearchIconGui(this.minecraft, ELTResearches.SECOND_RESEARCH, 48 * 2, 48));
        addResearchIcon(new ResearchIconGui(this.minecraft, ELTResearches.THIRD_RESEARCH, 48 * 3, 48));
        addResearchIcon(new ResearchIconGui(this.minecraft, ELTResearches.WEAPON_RESEARCH, 48 * 4, 48));

        addButton(new ImageButton(width - SIDE - 27, TOP + 5, 20, 10, 0, 0, 10, SWITCH_PAGE_BUTTON, 20, 20, (button) -> {
            this.isLinePage = !this.isLinePage;
        }, StringTextComponent.EMPTY));

        // Super must be before the rest!
        super.init();
        this.imageWidth = INV_WIDTH;
        this.imageHeight = INV_HEIGHT;
        this.leftPos = (width - INV_WIDTH) / 2;
        this.topPos = height - BOTTOM;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderWindow(matrixStack);

        if (this.menu.getSlot(0).hasItem()) {
            this.renderInside(matrixStack, mouseX, mouseY, partialTicks);
            this.lineList.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int buttonIn) {

        if (isLinePage && buttonIn == 0) {

            int deltaX = MathHelper.floor(this.scrollX);
            int deltaY = MathHelper.floor(this.scrollY);

            for (LineIconGui lineIconGui : lineIcons) {
//                this.selectedTab.drawTooltips(matrixStack, mouseX - offsetX - 9, mouseY - offsetY - 18, offsetX, offsetY); todo fix
                if (lineIconGui.isMouseOver(deltaX, deltaY, mouseX, mouseY)) {
                    this.selectedLine = lineIconGui.getResearchLine();
                    return true;
                }
            }

        }

        return super.mouseClicked(mouseX, mouseY, buttonIn);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int allIconHeight = 18 * 6; //this.lineIcons.size() * 18;
        if (delta != 0 && mouseX < SIDE + 68 && mouseX > SIDE && mouseY > TOP && mouseY < height - BOTTOM) {
            this.sidebarScrollAmt += delta;
            this.sidebarScrollAmt = MathHelper.clamp(this.sidebarScrollAmt, 0.0F, allIconHeight);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDeltaX, double mouseDeltaY) {
        int left = SIDE;
        int top = TOP;
        boolean inGui = mouseX < left + width - 2 * SIDE - PADDING && mouseX > left + PADDING && mouseY < top + height - TOP + 1 && mouseY > top + 2 * PADDING;

        if (button != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else if (inGui) {
                this.drag(mouseDeltaX, mouseDeltaY);
            }
            return true;
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {

    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    private void renderAnimatedSidebar(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bind(WINDOW);

        int offsetX = 9, offsetY = 18;
        int left = SIDE;
        int top = TOP;
        int right = width - SIDE;
        int bottom = height - BOTTOM;

        int displacement;

        // mouse not in this frame, display nothing
        if (mouseX >= SIDE) {
            if (this.sidebarActivatedLastTime) {

                // if mouse is within the new range
                if (mouseX < SIDE + 68) {
                    // just make displacement equals zero this time
                    displacement = 0;
                    GuiUtil.renderRepeating(this, matrixStack, left + offsetX + displacement, top + offsetY + (int) this.sidebarScrollAmt, 68, 18, 9, 0, WIDTH - 9 - 9, 18);
                    // we don't reset start time in this case
                } else {
                    // in last frame, moved out this frame
                    // reset start time
                    this.sidebarAnimationStartTime = 0;
                    // display nothing in this case
                    // mouse is not in zone this frame, so it is "not in zone in last frame" for next frame
                    this.sidebarActivatedLastTime = false;
                }
            } else {
                // never in, just display nothing
                // reset start time
                this.sidebarAnimationStartTime = 0;
                // display nothing in this case
                // mouse is not in zone this frame, so it is "not in zone in last frame" for next frame
                this.sidebarActivatedLastTime = false;
            }
        }
        // mouse in this frame, display the sidebar
        else {
            if (this.sidebarActivatedLastTime) {
                // in last frame, still in this frame. We don't reset startTime in this case.
                float time = MathHelper.clamp(System.currentTimeMillis() - this.sidebarAnimationStartTime, 0, 500);
                displacement = (int) (time / 500 * 68);
            } else {
                // not in last frame, moved in this frame. We set the startTime in this case.
                this.sidebarAnimationStartTime = System.currentTimeMillis();
                // first frame in, so there is no replacement, only display.
                displacement = 0;
            }
            // mouse is in zone this frame, so it is "in zone in last frame" for next frame
            this.sidebarActivatedLastTime = true;

            // now we render the sidebar
            // reduce displacement
            displacement -= 68;

            // render line icons
            GuiUtil.renderRepeating(this, matrixStack, left + offsetX + displacement, top + offsetY + (int) this.sidebarScrollAmt, 68, 18, 9, 0, WIDTH - 9 - 9, 18);

//            // top bar
//            this.blit(matrixStack, left + offsetX + displacement, top + offsetY, 0, 0, offsetX, offsetX);
//            GuiUtil.renderRepeating(this, matrixStack, left + offsetX + offsetX + displacement, top + offsetY, 68 - 9 - 9, 9, 9, 0, WIDTH - 9 - 9, 9);
//            this.blit(matrixStack, left + offsetX + 68 - 9 + displacement, top + offsetY, WIDTH - offsetX, 0, offsetX, offsetX);

            // lower bar
//            this.blit(matrixStack, left + offsetX + displacement, bottom - offsetY, 0, HEIGHT - 9, offsetX, offsetX);
//            GuiUtil.renderRepeating(this, matrixStack, left + offsetX + offsetX + displacement, bottom - offsetY, 68 - 9 - 9, 9, 9, HEIGHT - 9, WIDTH - 9 - 9, 9);
//            this.blit(matrixStack, left + offsetX + 68 - 9 + displacement, bottom - offsetY, WIDTH - offsetX, HEIGHT - 9, offsetX, offsetX);

        }
    }


    /**
     * 渲染研究窗口的框，以及背包，和标题
     */
    private void renderWindow(MatrixStack matrixStack) {
        int left = SIDE;
        int top = TOP;
        int right = width - SIDE;
        int bottom = height - BOTTOM;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderHelper.setupForFlatItems();
        this.minecraft.getTextureManager().bind(WINDOW);

        // 研究界面边框
        // Top left corner
        this.blit(matrixStack, left, top, 0, 0, CORNER_SIZE, CORNER_SIZE);
        // Top side
        GuiUtil.renderRepeating(this, matrixStack, left + CORNER_SIZE, top, width - CORNER_SIZE - 2 * SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, 0, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Top right corner
        this.blit(matrixStack, right - CORNER_SIZE, top, WIDTH - CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE);
        // Left side
        GuiUtil.renderRepeating(this, matrixStack, left, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Right side
        GuiUtil.renderRepeating(this, matrixStack, right - CORNER_SIZE, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, WIDTH - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Bottom left corner
        this.blit(matrixStack, left, bottom - CORNER_SIZE, 0, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);
        // Bottom side
        GuiUtil.renderRepeating(this, matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, width - CORNER_SIZE - 2 * SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Bottom right corner
        this.blit(matrixStack, right - CORNER_SIZE, bottom - CORNER_SIZE, WIDTH - CORNER_SIZE, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);

        // 标题
        String windowTitle = I18n.get("elt.gui.research");
        this.font.draw(matrixStack, windowTitle, left + 8, top + 6, 4210752);

        // 玩家背包界面
        this.minecraft.getTextureManager().bind(INVENTORY);
        this.blit(matrixStack, (width - INV_WIDTH) / 2, bottom, 0, 141, INV_WIDTH, INV_HEIGHT);

    }

    private void renderInside(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(18.0f, 28.0f, 0.0F);

        int scrollRangeX = width - 2 * SIDE - 2 * PADDING + 2;
        int scrollRangeY = height - TOP - BOTTOM - 2 * PADDING - 6;

        if (!this.centered) {
            this.scrollX = (double) scrollRangeX / 2 - (double) (this.maxX + this.minX) / 2;
            this.scrollY = (double) scrollRangeY / 2 - (double) (this.maxY + this.minY) / 2;
            this.centered = true;
        }

        int deltaX = MathHelper.floor(this.scrollX);
        int deltaY = MathHelper.floor(this.scrollY);

        // render borders and color masks
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrixStack, scrollRangeX, scrollRangeY, 0, 0, -16777216);
        RenderSystem.depthFunc(515);

        // draw inside background
        this.drawInsideBackground(matrixStack, deltaX, deltaY, scrollRangeX, scrollRangeY);

        // draw animated sidebar
        RenderSystem.translatef(-18.0f, -28.0f, 0.0F);
        this.renderAnimatedSidebar(matrixStack, mouseX, mouseY);
        RenderSystem.translatef(18.0f, 28.0f, 0.0F);

        // draw research icons
        if (!isLinePage) {
            this.drawResearchIcons(matrixStack, mouseX, mouseY, deltaX, deltaY);
        } else {
            this.drawResearchLineIcons(matrixStack, mouseX, mouseY, deltaX, deltaY);
        }

        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);

        RenderSystem.popMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    private void drawResearchExperienceBar(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        if (this.inventory.player != null) {
            this.minecraft.getTextureManager().bind(BARS);
            this.minecraft.getProfiler().push("research_desk_exp_bar");

            LazyOptional<ITeamCapability> Cap = this.inventory.player.getCapability(ELTCapabilities.teamCapability);
            Cap.ifPresent((P) -> {
                String team = P.getTeam();
                System.out.println("Team: " + team);
                int researchExpAmt = ResearchTeamDatabase.TEAMS.get(team).getResearchTeamXP();
                this.blit(matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, 0, 0, researchExpAmt, 9);
                this.font.draw(matrixStack, "Research Experience: " + researchExpAmt, left + CORNER_SIZE + 8, bottom - CORNER_SIZE - 9, 0);
            });

            this.minecraft.getProfiler().pop();
        }
    }

    /**
     * 鼠标拖动时调用
     */
    private void drag(double dragX, double dragY) {

        int scrollRangeX = width - 2 * SIDE - 2 * PADDING + 2;
        int scrollRangeY = height - TOP - BOTTOM - 2 * PADDING - 6;

        if (this.maxX - this.minX > scrollRangeX) {
            this.scrollX = MathHelper.clamp(this.scrollX + dragX, -(this.maxX - scrollRangeX), 0.0D);
        }

        if (this.maxY - this.minY > scrollRangeY) {
            this.scrollY = MathHelper.clamp(this.scrollY + dragY, -(this.maxY - scrollRangeY), 0.0D);
        }

    }

    private void addResearchIcon(ResearchIconGui gui) {
        this.widgets.add(gui);
        int i = gui.getX();
        int j = i + 28;
        int k = gui.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);
    }

    private void addResearchLineIcon(LineIconGui gui) {
        this.lineIcons.add(gui);
        int i = gui.getX();
        int j = i + 28;
        int k = gui.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);
    }

    private void drawResearchLineIcons(MatrixStack matrixStack, int mouseX, int mouseY, int deltaX, int deltaY) {
        // bind texture of frames
        this.minecraft.getTextureManager().bind(RESEARCH_ICONS);

        // draw frames
        for (LineIconGui lineIconGui : lineIcons) {
            lineIconGui.draw(matrixStack, mouseX, mouseY, deltaX, deltaY);
        }
    }

    private void drawResearchIcons(MatrixStack matrixStack, int mouseX, int mouseY, int deltaX, int deltaY) {
        // bind texture of frames
        this.minecraft.getTextureManager().bind(RESEARCH_ICONS);

        // draw frames
        for (ResearchIconGui researchIconGui : widgets) {
            // check whether the research's line is equal to the current selected line
            if (researchIconGui.getResearch().getLine() != null && researchIconGui.getResearch().getLine() == this.selectedLine) {
                researchIconGui.draw(matrixStack, mouseX, mouseY, deltaX, deltaY);
            }
        }

        // todo: draw connectivity
    }

    private void drawInsideBackground(MatrixStack matrixStack, int deltaX, int deltaY, int scrollRangeX, int scrollRangeY) {
        // bind texture of background
        this.minecraft.getTextureManager().bind(INSIDE_BACKGROUND);

        int hexReducedDeltaX = deltaX % 16;
        int hexReducedDeltaY = deltaY % 16;

        // draw background from 16 x 16 texture
        for (int i1 = -1; i1 <= scrollRangeX / 16 + 1; ++i1) {
            for (int j1 = -1; j1 <= scrollRangeY / 16 + 1; ++j1) {
                blit(matrixStack, hexReducedDeltaX + 16 * i1, hexReducedDeltaY + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }
    }

    public FontRenderer getFontRenderer() {
        return font;
    }

}
