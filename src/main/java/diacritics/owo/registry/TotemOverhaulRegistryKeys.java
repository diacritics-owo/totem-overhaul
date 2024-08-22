package diacritics.owo.registry;

import java.util.function.Function;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class TotemOverhaulRegistryKeys {
  public static final RegistryKey<Registry<Jewel>> JEWEL = of("jewel");
  public static final RegistryKey<Registry<RegistryKey<Item>>> JEWEL_ITEM = of("jewel_item"); // registryception
  public static final RegistryKey<Registry<Function<LivingEntity, Boolean>>> JEWEL_EFFECT =
      of("jewel_effect");

  public static <T> RegistryKey<Registry<T>> of(String identifier) {
    return RegistryKey.ofRegistry(TotemOverhaul.identifier(identifier));
  }
}
