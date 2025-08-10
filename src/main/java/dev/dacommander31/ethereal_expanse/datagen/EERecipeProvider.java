package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class EERecipeProvider extends RecipeProvider {
    protected EERecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new EERecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "EE Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TRANSPORTATION, EEItems.GATEWAY_CANISTER)
                .define('Y', Items.ENDER_EYE)
                .define('S', Items.END_STONE)
                .define('N', Items.NETHER_STAR)
                .define('P', Items.ENDER_PEARL)
                .pattern("SYS")
                .pattern("PSP")
                .pattern("SNS")
                .unlockedBy("entered_gateway", inBlock(Blocks.END_GATEWAY))
                .save(this.output);
    }

    private static Criterion<EnterBlockTrigger.TriggerInstance> inBlock(Block block) {
        return EnterBlockTrigger.TriggerInstance.entersBlock(block);
    }
}
