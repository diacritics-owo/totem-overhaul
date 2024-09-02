package diacritics.owo.config;

import diacritics.owo.TotemOverhaul;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import java.util.Map;
import java.util.LinkedHashMap;

public class TotemOverhaulConfig extends Config {
  public TotemOverhaulConfig() {
    super(TotemOverhaul.identifier("config"));
  }

  public BloodJewelSection bloodJewel = new BloodJewelSection();
  public VoidJewelSection voidJewel = new VoidJewelSection();
  public ChorusJewelSection chorusJewel = new ChorusJewelSection();

  public static class BloodJewelSection extends ConfigSection {
    public ValidatedIdentifierMap<Integer> effectDurations = effectDurations(new LinkedHashMap<>() {
      {
        put(id(StatusEffects.SPEED), 500);
        put(id(StatusEffects.STRENGTH), 500);
        put(id(StatusEffects.REGENERATION), 900);
        put(id(StatusEffects.ABSORPTION), 800);
      }
    });
    public ValidatedIdentifierMap<Integer> effectAmplifiers =
        effectAmplifiers(new LinkedHashMap<>() {
          {
            put(id(StatusEffects.SPEED), 2);
            put(id(StatusEffects.STRENGTH), 2);
            put(id(StatusEffects.REGENERATION), 2);
            put(id(StatusEffects.ABSORPTION), 2);
          }
        });
  }

  public static class VoidJewelSection extends ConfigSection {
    public ValidatedInt height = new ValidatedInt(300, 1024, -64);
    public ValidatedIdentifierMap<Integer> effectDurations = effectDurations(new LinkedHashMap<>() {
      {
        put(id(StatusEffects.SLOW_FALLING), 300);
        put(id(StatusEffects.REGENERATION), 900);
        put(id(StatusEffects.ABSORPTION), 100);
      }
    });
    public ValidatedIdentifierMap<Integer> effectAmplifiers =
        effectAmplifiers(new LinkedHashMap<>() {
          {
            put(id(StatusEffects.REGENERATION), 2);
            put(id(StatusEffects.ABSORPTION), 2);
          }
        });
  }

  public static class ChorusJewelSection extends ConfigSection {
    public ValidatedInt radius = new ValidatedInt(8, 64, 0);
    public ValidatedInt rolls = new ValidatedInt(16, 64, 0);
    public ValidatedIdentifierMap<Integer> effectDurations = effectDurations(new LinkedHashMap<>() {
      {
        put(id(StatusEffects.REGENERATION), 900);
        put(id(StatusEffects.ABSORPTION), 100);
        put(id(StatusEffects.FIRE_RESISTANCE), 800);
      }
    });
    public ValidatedIdentifierMap<Integer> effectAmplifiers =
        effectAmplifiers(new LinkedHashMap<>() {
          {
            put(id(StatusEffects.REGENERATION), 2);
            put(id(StatusEffects.ABSORPTION), 2);
          }
        });
  }

  @SuppressWarnings("deprecation")
  public static ValidatedIdentifierMap<Integer> effectDurations(
      Map<Identifier, Integer> defaultValue) {
    return new ValidatedIdentifierMap<>(defaultValue,
        ValidatedIdentifier.ofRegistry(Registries.STATUS_EFFECT), new ValidatedInt(0, 10000, 0));
  }

  @SuppressWarnings("deprecation")
  public static ValidatedIdentifierMap<Integer> effectAmplifiers(
      Map<Identifier, Integer> defaultValue) {
    return new ValidatedIdentifierMap<>(defaultValue,
        ValidatedIdentifier.ofRegistry(Registries.STATUS_EFFECT), new ValidatedInt(0, 8, 0));
  }

  public static Identifier id(RegistryEntry<StatusEffect> effect) {
    return effect.getKey().get().getValue();
  }
}
