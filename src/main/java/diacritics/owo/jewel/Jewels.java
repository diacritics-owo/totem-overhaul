package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class Jewels {
  public static final RegistryKey<Jewel> GRAY =
      register(new Jewel(TotemOverhaul.identifier("gray")));
  public static final RegistryKey<Jewel> BLUE =
      register(new Jewel(TotemOverhaul.identifier("blue")));
  public static final RegistryKey<Jewel> RED = register(new Jewel(TotemOverhaul.identifier("red")));
  public static final RegistryKey<Jewel> GREEN = register(new Jewel(TotemOverhaul.identifier("green")));

  public static final RegistryKey<Jewel> DEFAULT = GRAY;

  public static RegistryKey<Jewel> register(Jewel jewel) {
    return TotemOverhaulRegistries.JEWEL
        .getKey(Registry.register(TotemOverhaulRegistries.JEWEL, jewel.identifier(), jewel)).get();
  }

  public static void initialize() {}
}
