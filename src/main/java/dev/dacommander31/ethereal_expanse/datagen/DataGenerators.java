package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EtherealExpanse.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.createProvider(EEWorldGenProvider::new);
        event.createProvider(EEBlockTagProvider::new);
        event.createProvider(EEModelProvider::new);
        event.createProvider(EERecipeProvider.Runner::new);
//        generator.addProvider(true, new EEWorldGenProvider(packOutput, lookupProvider));
//        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
//                List.of(new LootTableProvider.SubProviderEntry(EEBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
//        generator.addProvider(true, new EEModelProvider(packOutput));
//        generator.addProvider(true, new EEBlockTagProvider(packOutput, lookupProvider));
//        generator.addProvider(true, new EERecipeProvider.Runner(packOutput, lookupProvider));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.createProvider(EEWorldGenProvider::new);
        event.createProvider(EEBlockTagProvider::new);
        event.createProvider(EEModelProvider::new);
        event.createProvider(EERecipeProvider.Runner::new);
//        generator.addProvider(true, new EEWorldGenProvider(packOutput, lookupProvider));
//        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
//                List.of(new LootTableProvider.SubProviderEntry(EEBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
//        generator.addProvider(true, new EEBlockTagProvider(packOutput, lookupProvider));
//        generator.addProvider(true, new EERecipeProvider.Runner(packOutput, lookupProvider));
    }
}
