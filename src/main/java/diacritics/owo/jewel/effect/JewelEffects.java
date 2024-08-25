package diacritics.owo.jewel.effect;

import java.util.function.Function;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;

public class JewelEffects {
  public static Function<LivingEntity, Boolean> VANILLA = (entity) -> {
    entity.setHealth(1.0F);
    entity.clearStatusEffects();
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    entity.getWorld().sendEntityStatus(entity, (byte) 35);

    return true;
  };

  public static RegistryKey<Function<LivingEntity, Boolean>> BLOOD =
      register(Jewels.BLOOD, (entity) -> {
        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 500, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 500, 1));

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

        entity.getWorld().sendEntityStatus(entity, (byte) 35);

        return true;
      });

  public static RegistryKey<Function<LivingEntity, Boolean>> register(RegistryKey<Jewel> jewel,
      Function<LivingEntity, Boolean> function) {
    return TotemOverhaulHelpers.registerJewelEffect(jewel, function);
  }

  public static void initialize() {}
}
