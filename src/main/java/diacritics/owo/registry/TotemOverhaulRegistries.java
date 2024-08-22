package diacritics.owo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import java.util.function.Function;

public class TotemOverhaulRegistries {
  public static final Registry<Jewel> JEWEL = new SimpleRegistry<>(
      RegistryKey.ofRegistry(TotemOverhaul.identifier("jewel")), Lifecycle.stable());
  public static final Registry<RegistryKey<Item>> JEWEL_ITEMS = new SimpleRegistry<>(
      RegistryKey.ofRegistry(TotemOverhaul.identifier("jewel_items")), Lifecycle.stable());
  public static final Registry<Function<LivingEntity, Boolean>> JEWEL_EFFECTS =
      new SimpleRegistry<>(RegistryKey.ofRegistry(TotemOverhaul.identifier("jewel_effects")),
          Lifecycle.stable());

  public static final Codec<RegistryKey<Jewel>> JEWEL_CODEC =
      RegistryKey.createCodec(JEWEL.getKey());

  public static void initialize() {}
}
