package dev.dacommander31.ethereal_expanse.worldgen.biome;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;

public class EEBiomes {
    public static final ResourceKey<Biome> STARLIT_MEADOW = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, "starlit_meadow"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(STARLIT_MEADOW, starlitMeadow(context));
    }

    public static void globalEndGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultSprings(builder);
    }

    public static Biome starlitMeadow(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.ENDERMITE, 1, 50));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalEndGeneration(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.5f)
                .temperature(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder()
                        .waterColor(0xb78be0)
                        .waterFogColor(0x8662a8)
                        .fogColor(0x8662a8)
                        .skyColor(0xb78be0)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.02f))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.END)
                        .build()))
                .build();
    }


}
