package diacritics.owo.component;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

// that's a mouthful
public class TotemOverhaulDataComponentTypes {
  public static final ComponentType<RegistryKey<Jewel>> JEWEL = register("jewel",
      ComponentType.<RegistryKey<Jewel>>builder().codec(TotemOverhaulRegistries.JEWEL_CODEC)
          .packetCodec(TotemOverhaulRegistries.JEWEL_PACKET_CODEC).build());

  public static <T> ComponentType<T> register(String identifier, ComponentType<T> type) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, TotemOverhaul.identifier(identifier),
        type);
  }

  public static void initialize() {}
}
