
package com.teammoeg.elt;


import com.teammoeg.elt.block.ELTBlocks;
<<<<<<< Updated upstream
import com.teammoeg.elt.block.researchdesk.ResearchDeskBlock;
import com.teammoeg.elt.client.settings.ELTKeyBindings;
import com.teammoeg.elt.item.ELTItems;
import com.teammoeg.elt.item.ResearchScroll;
=======
import com.teammoeg.elt.block.researchblock.ResearchDeskBlock;
>>>>>>> Stashed changes
import com.teammoeg.elt.research.Quest;
import com.teammoeg.elt.research.Research;
import com.teammoeg.the_seed.api.modinitializers.ModInitializer;
import com.teammoeg.the_seed.data.legacy.CS;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Mod(ELT.MOD_ID)
public class ELT implements ModInitializer {
    public static final String MOD_ID = CS.ModIDs.ELT;
    public static final String MOD_NAME = "Energy Level Transition";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public ELT() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        MinecraftForge.EVENT_BUS.register(RegistryEvents.class);
        ELTBlocks.ResearchDesk  = new ResearchDeskBlock("researchdesk");
        ELTItems.ResearchScroll  = new ResearchScroll("researchscroll");
    }

    public static final Quest killzombie = new Quest("killzombie");
    public static final Research weaponResearch = new Research("weaponresearch");

    @Override
    public void onModCommonSetup2(FMLCommonSetupEvent aEvent) {
        weaponResearch.addQuest(killzombie);
    }

    @Override
    public void onModClientSetup2(FMLClientSetupEvent aEvent) {
        LOGGER.info("Hello from ELT Client");
        ELTKeyBindings.registerKeyBindings();
    }

    @Override
    public void onModServerSetup2(FMLDedicatedServerSetupEvent event) {

    }

    @Override
    public void onModIMCEnqueue2(InterModEnqueueEvent aEvent) {

    }

    @Override
    public void onModIMCProcess2(InterModProcessEvent aEvent) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        public static List<Item> registeredItems = new ArrayList<>();
        public static List<Block> registeredBlocks = new ArrayList<>();
        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> blockRegistryEvent) {
            for(Block block : registeredBlocks) {
                blockRegistryEvent.getRegistry().register(block);
            }
        }
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> itemRegistryEvent)
        {
            for(Item item : registeredItems) {
                itemRegistryEvent.getRegistry().register(item);
            }
        }
    }

    // Do not modify the codes below

    @Override
    public String getModID() {
        return MOD_ID;
    }

    @Override
    public String getModName() {
        return MOD_NAME;
    }

    @Override
    public String getModNameForLog() {
        return MOD_NAME.toUpperCase().replace(' ', '_') + "_Mod";
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent aEvent) {
        onModCommonSetup(aEvent);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent aEvent) {
        onModClientSetup(aEvent);
    }

    @SubscribeEvent
    public void onServerSetup(FMLDedicatedServerSetupEvent aEvent) {
        onModServerSetup(aEvent);
    }

    @SubscribeEvent
    public void onIMCEnqueue(InterModEnqueueEvent aEvent) {
        onModIMCEnqueue(aEvent);
    }

    @SubscribeEvent
    public void onIMCProcess(InterModProcessEvent aEvent) {
        onModIMCProcess(aEvent);
    }

}
