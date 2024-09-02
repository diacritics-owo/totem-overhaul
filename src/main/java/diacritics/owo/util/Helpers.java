package diacritics.owo.util;

import diacritics.owo.component.EntityJewelComponent;
import diacritics.owo.jewel.Jewels;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.registry.Registries;

public class Helpers {
  public static void onEvokerKilledEntity(EntityJewelComponent component, LivingEntity entity) {
    if (entity instanceof MerchantEntity) {
      component.setJewelKey(Jewels.BLOOD);
    }
  }

  public static void applyEffects(LivingEntity entity, ValidatedIdentifierMap<Integer> durations,
      ValidatedIdentifierMap<Integer> amplifiers) {
    durations.forEach((effect, duration) -> {
      entity
          .addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(effect).get(),
              duration, amplifiers.getOrDefault(effect, 1) - 1));
    });
  }
}
