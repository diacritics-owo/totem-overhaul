package diacritics.owo.registry;

import com.mojang.serialization.Lifecycle;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;

public class TotemOverhaulRegistries {
  // TODO: do we really need a registry?
  public static final Registry<Jewel> JEWEL = new SimpleRegistry<>(
      RegistryKey.ofRegistry(TotemOverhaul.identifier("jewel")), Lifecycle.stable());

  public static void initialize() {}
}
