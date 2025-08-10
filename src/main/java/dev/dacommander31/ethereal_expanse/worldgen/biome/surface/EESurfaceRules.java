package dev.dacommander31.ethereal_expanse.worldgen.biome.surface;

import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import dev.dacommander31.ethereal_expanse.worldgen.biome.EEBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class EESurfaceRules {
    private static final SurfaceRules.RuleSource STARLIT_END_STONE = makeStateRule(EEBlocks.STARLIT_END_STONE.get());
    private static final SurfaceRules.RuleSource END_STONE = makeStateRule(Blocks.END_STONE);

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(EEBiomes.STARLIT_MEADOW),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, STARLIT_END_STONE)
                ),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, END_STONE)
        );
    }


    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
