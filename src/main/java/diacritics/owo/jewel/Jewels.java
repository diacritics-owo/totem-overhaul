package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.minecraft.registry.RegistryKey;

public class Jewels {
  public static final RegistryKey<Jewel> NONE = register("none", new Jewel(0xFFFFFF));

  public static final RegistryKey<Jewel> BLOOD = register("blood", new Jewel(0xB26D6C));
  public static final RegistryKey<Jewel> VOID = register("void", new Jewel(0x505B8B));
  public static final RegistryKey<Jewel> CHORUS = register("chorus", new Jewel(0x8E5EA6));

  public static RegistryKey<Jewel> register(String identifier, Jewel jewel) {
    return TotemOverhaulHelpers.registerJewel(TotemOverhaul.identifier(identifier), jewel);
  }

  public static void initialize() {}
}
