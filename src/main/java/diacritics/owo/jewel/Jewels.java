package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class Jewels {
  public static final RegistryKey<Jewel> BLUE =
      register(new Jewel(TotemOverhaul.identifier("blue")));

  public static RegistryKey<Jewel> register(Jewel jewel) {
    return TotemOverhaulRegistries.JEWEL
        .getKey(Registry.register(TotemOverhaulRegistries.JEWEL, jewel.identifier(), jewel)).get();
  }

  public static void initialize() {}
}
