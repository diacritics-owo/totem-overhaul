package diacritics.owo.component;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

// that's a mouthful
public class TotemOverhaulDataComponentTypes {
  public static final ComponentType<Jewel> JEWEL = register("jewel",
      ComponentType.<Jewel>builder().codec(Jewel.CODEC).packetCodec(Jewel.PACKET_CODEC).build());

  public static <T> ComponentType<T> register(String identifier, ComponentType<T> type) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, TotemOverhaul.identifier(identifier),
        type);
  }

  public static void initialize() {}
}
