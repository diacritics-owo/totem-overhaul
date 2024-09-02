package diacritics.owo.jewel.effect;

import java.util.function.BiFunction;
import diacritics.owo.config.Configs;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.util.Helpers;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;

public class JewelEffects {
  public static BiFunction<LivingEntity, DamageSource, Boolean> VANILLA = (entity, source) -> {
    if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      return false;
    }

    entity.setHealth(1.0F);
    entity.clearStatusEffects();

    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));

    entity.getWorld().sendEntityStatus(entity, (byte) 35);

    return true;
  };

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> BLOOD =
      register(Jewels.BLOOD, (entity, source) -> {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
          return false;
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        Helpers.applyEffects(entity, Configs.config.bloodJewel.effectDurations,
            Configs.config.bloodJewel.effectAmplifiers);

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> VOID =
      register(Jewels.VOID, (entity, source) -> {
        if (!source.getTypeRegistryEntry().getKey().orElse(null).equals(DamageTypes.OUT_OF_WORLD)) {
          return false;
        }

        if (entity.hasVehicle()) {
          entity.detach();
        }

        if (entity.getWorld() instanceof ServerWorld serverWorld) {
          entity.teleportTo(new TeleportTarget(serverWorld,
              new Vec3d(entity.getX(), Configs.config.voidJewel.height.get(), entity.getZ()),
              entity.getVelocity(), entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        Helpers.applyEffects(entity, Configs.config.voidJewel.effectDurations,
            Configs.config.voidJewel.effectAmplifiers);

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> CHORUS =
      register(Jewels.CHORUS, (entity, source) -> {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
          return false;
        }

        if (entity.getWorld() instanceof ServerWorld serverWorld) {
          for (int i = 0; i < Configs.config.chorusJewel.rolls.get(); ++i) {
            double x = entity.getX() + (entity.getRandom().nextDouble() - 0.5)
                * (2 * Configs.config.chorusJewel.radius.get());
            double y = MathHelper.clamp(entity.getY()
                + (double) (entity.getRandom().nextInt(2 * Configs.config.chorusJewel.radius.get())
                    - Configs.config.chorusJewel.radius.get()),
                (double) serverWorld.getBottomY(),
                (double) (serverWorld.getBottomY() + serverWorld.getLogicalHeight() - 1));
            double z = entity.getZ() + (entity.getRandom().nextDouble() - 0.5)
                * (2 * Configs.config.chorusJewel.radius.get());

            if (entity.hasVehicle()) {
              entity.stopRiding();
            }

            Vec3d vec3d = entity.getPos();
            if (entity.teleport(x, y, z, true)) {
              serverWorld.emitGameEvent(GameEvent.TELEPORT, vec3d, Emitter.of(entity));
              SoundCategory soundCategory;
              SoundEvent soundEvent;

              if (entity instanceof FoxEntity) {
                soundEvent = SoundEvents.ENTITY_FOX_TELEPORT;
                soundCategory = SoundCategory.NEUTRAL;
              } else {
                soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                soundCategory = SoundCategory.PLAYERS;
              }

              serverWorld.playSound((PlayerEntity) null, entity.getX(), entity.getY(),
                  entity.getZ(), soundEvent, soundCategory);
              entity.onLanding();

              break;
            }
          }

          if (entity instanceof PlayerEntity player) {
            player.clearCurrentExplosion();
          }
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        Helpers.applyEffects(entity, Configs.config.chorusJewel.effectDurations,
            Configs.config.chorusJewel.effectAmplifiers);

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> register(
      RegistryKey<Jewel> jewel, BiFunction<LivingEntity, DamageSource, Boolean> function) {
    return TotemOverhaulHelpers.registerJewelEffect(jewel, function);
  }

  public static void initialize() {}
}
