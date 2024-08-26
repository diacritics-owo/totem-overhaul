package diacritics.owo.jewel.effect;

import java.util.function.BiFunction;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

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

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 500, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 500, 1));

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

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
          entity.teleportTo(
              new TeleportTarget(serverWorld, new Vec3d(entity.getX(), 300, entity.getZ()),
                  entity.getVelocity(), entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 300, 0));

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> CHORUS =
      register(Jewels.CHORUS, (entity, source) -> {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
          return false;
        }

        if (entity.getWorld() instanceof ServerWorld serverWorld) {
          new ItemStack(Items.CHORUS_FRUIT).finishUsing(serverWorld, entity);
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> register(
      RegistryKey<Jewel> jewel, BiFunction<LivingEntity, DamageSource, Boolean> function) {
    return TotemOverhaulHelpers.registerJewelEffect(jewel, function);
  }

  public static void initialize() {}
}
