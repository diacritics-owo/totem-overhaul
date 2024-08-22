package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.minecraft.registry.RegistryKey;

public class Jewels {
  // the item for the gray jewel won't (shouldn't) be used (shouldn't matter anyway? registry.get
  // will return null for a null key - see jewelcomponent::tryusejewel)
  public static final RegistryKey<Jewel> GRAY = register("gray", new Jewel(0xFFFFFF));

  public static final RegistryKey<Jewel> RED = register("red", new Jewel(0xFF9C9B));
  public static final RegistryKey<Jewel> GREEN = register("green", new Jewel(0xA7FFC0));
  public static final RegistryKey<Jewel> BLUE = register("blue", new Jewel(0xA6B7FE));

  public static final RegistryKey<Jewel> DEFAULT = GRAY;

  public static RegistryKey<Jewel> register(String identifier, Jewel jewel) {
    return TotemOverhaulHelpers.registerJewel(TotemOverhaul.identifier(identifier), jewel);
  }

  public static void initialize() {}
}
