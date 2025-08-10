package dev.dacommander31.ethereal_expanse.worldgen.biome;

import terrablender.api.EndBiomeRegistry;

public class EETerrablender {
    public static void registerBiomes() {
        EndBiomeRegistry.registerMidlandsBiome(EEBiomes.STARLIT_MEADOW, 4);
    }
}
