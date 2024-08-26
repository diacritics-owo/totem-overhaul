package diacritics.owo.util;

import java.util.function.BiFunction;
import diacritics.owo.item.JewelItem;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

// meant to also be used by mods depending on this one
public class TotemOverhaulHelpers {
  public static RegistryKey<Item> registerJewelItem(Identifier identifier, JewelItem item) {
    return Registry.register(TotemOverhaulRegistries.JEWEL_ITEM, item.getJewelKey().getValue(),
        Registries.ITEM.getKey(Registry.register(Registries.ITEM, identifier, item)).get());
  }

  public static RegistryKey<BiFunction<LivingEntity, DamageSource, Boolean>> registerJewelEffect(
      RegistryKey<Jewel> jewel, BiFunction<LivingEntity, DamageSource, Boolean> function) {
    return TotemOverhaulRegistries.JEWEL_EFFECT
        .getKey(Registry.register(TotemOverhaulRegistries.JEWEL_EFFECT, jewel.getValue(), function))
        .get();
  }

  public static RegistryKey<Jewel> registerJewel(Identifier identifier, Jewel jewel) {
    return TotemOverhaulRegistries.JEWEL
        .getKey(Registry.register(TotemOverhaulRegistries.JEWEL, identifier, jewel)).get();
  }
}
