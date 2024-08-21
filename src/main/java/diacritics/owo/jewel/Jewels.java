package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.item.TotemOverhaulItems;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class Jewels {
  // the item for the gray jewel won't (shouldn't) be used
  public static final RegistryKey<Jewel> GRAY =
      register(new Jewel(TotemOverhaul.identifier("gray"), TotemOverhaulItems.RED_JEWEL));

  public static final RegistryKey<Jewel> RED =
      register(new Jewel(TotemOverhaul.identifier("red"), TotemOverhaulItems.RED_JEWEL));
  public static final RegistryKey<Jewel> GREEN =
      register(new Jewel(TotemOverhaul.identifier("green"), TotemOverhaulItems.GREEN_JEWEL));
  public static final RegistryKey<Jewel> BLUE =
      register(new Jewel(TotemOverhaul.identifier("blue"), TotemOverhaulItems.BLUE_JEWEL));

  public static final RegistryKey<Jewel> DEFAULT = GRAY;

  public static RegistryKey<Jewel> register(Jewel jewel) {
    return TotemOverhaulRegistries.JEWEL
        .getKey(Registry.register(TotemOverhaulRegistries.JEWEL, jewel.identifier(), jewel)).get();
  }

  public static void initialize() {}
}
