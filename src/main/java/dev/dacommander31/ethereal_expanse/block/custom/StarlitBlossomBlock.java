package dev.dacommander31.ethereal_expanse.block.custom;

import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarlitBlossomBlock extends FlowerBlock {
    private final StarlitBlossomBlock.Type type;
    private final double PLAYER_DIST = 5;
    private final double PLAYER_LAUNCH_MULTIPLIER = 3;

    public StarlitBlossomBlock(StarlitBlossomBlock.Type type, Properties properties) {
        super(MobEffects.LEVITATION, 5f, properties);
        this.type = type;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(EEBlocks.STARLIT_END_STONE);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        List<ServerPlayer> nearbyPlayers = level.getPlayers(player -> player.position().distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= PLAYER_DIST);
        nearbyPlayers.forEach(player -> player.setDeltaMovement(calcUnitVector(pos, player).scale(PLAYER_LAUNCH_MULTIPLIER)));
    }

    private Vec3 calcUnitVector(BlockPos blockPos, ServerPlayer player) {
        double xd = player.getX() - blockPos.getX();
        double yd = player.getY() - blockPos.getY();
        double zd = player.getZ() - blockPos.getZ();
        Vec3 dv = new Vec3(xd, yd, zd);
        double vm = Math.sqrt(
                xd * xd +
                yd * yd +
                zd * zd
        );

        return dv.scale(1/vm);
    }

    @Override
    public @Nullable MobEffectInstance getBeeInteractionEffect() {
        return new MobEffectInstance(MobEffects.LEVITATION, 25);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide()
                && level.getDifficulty() != Difficulty.PEACEFUL
                && entity instanceof Bee bee
                && Bee.attractsBees(state)
                && !bee.hasEffect(MobEffects.POISON)) {
            bee.addEffect(this.getBeeInteractionEffect());
        }
    }

    public enum Type {
        OPEN(true, MobEffects.LEVITATION, 11.0F, SoundEvents.EYEBLOSSOM_OPEN_LONG, SoundEvents.EYEBLOSSOM_OPEN, 16545810),
        CLOSED(false, MobEffects.NAUSEA, 7.0F, SoundEvents.EYEBLOSSOM_CLOSE_LONG, SoundEvents.EYEBLOSSOM_CLOSE, 6250335);

        final boolean open;
        final Holder<MobEffect> effect;
        final float effectDuration;
        final SoundEvent longSwitchSound;
        final SoundEvent shortSwitchSound;
        private final int particleColor;

        Type(boolean open, Holder<MobEffect> effect, float effectDuration, SoundEvent longSwitchSound, SoundEvent shortSwitchSound, int particleColor) {
            this.open = open;
            this.effect = effect;
            this.effectDuration = effectDuration;
            this.longSwitchSound = longSwitchSound;
            this.shortSwitchSound = shortSwitchSound;
            this.particleColor = particleColor;
        }

        public Block block() {
            return this.open ? Blocks.OPEN_EYEBLOSSOM : Blocks.CLOSED_EYEBLOSSOM;
        }

        public BlockState state() {
            return this.block().defaultBlockState();
        }

        public StarlitBlossomBlock.Type transform() {
            return fromBoolean(!this.open);
        }

        public boolean emitSounds() {
            return this.open;
        }

        public static StarlitBlossomBlock.Type fromBoolean(boolean open) {
            return open ? OPEN : CLOSED;
        }

        public void spawnTransformParticle(ServerLevel level, BlockPos pos, RandomSource random) {
            Vec3 vec3 = pos.getCenter();
            double d0 = 0.5 + random.nextDouble();
            Vec3 vec31 = new Vec3(random.nextDouble() - 0.5, random.nextDouble() + 1.0, random.nextDouble() - 0.5);
            Vec3 vec32 = vec3.add(vec31.scale(d0));
            TrailParticleOption trailparticleoption = new TrailParticleOption(vec32, this.particleColor, (int)(20.0 * d0));
            level.sendParticles(trailparticleoption, vec3.x, vec3.y, vec3.z, 1, 0.0, 0.0, 0.0, 0.0);
        }

        public SoundEvent longSwitchSound() {
            return this.longSwitchSound;
        }
    }
}
