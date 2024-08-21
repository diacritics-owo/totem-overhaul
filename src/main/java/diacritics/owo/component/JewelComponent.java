package diacritics.owo.component;

import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class JewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";

  private final EvokerEntity evoker;
  private RegistryKey<Jewel> jewelKey;

  public JewelComponent(EvokerEntity evoker) {
    this.evoker = evoker;
  }

  public JewelComponent(EvokerEntity evoker, RegistryKey<Jewel> jewelKey) {
    this.evoker = evoker;
    this.jewelKey = jewelKey;
  }

  @Nullable
  public RegistryKey<Jewel> getJewelKey() {
    return this.jewelKey;
  }

  @Override
  public void readFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (tag.contains(JEWEL_KEY)) {
      TotemOverhaulRegistries.JEWEL_CODEC
          .parse(registryLookup.getOps(NbtOps.INSTANCE), tag.get(JEWEL_KEY))
          .resultOrPartial(jewelKey -> {
            TotemOverhaul.LOGGER.error("failed to parse jewel registrykey: '{}'", jewelKey);
          }).ifPresent(jewelKey -> {
            this.jewelKey = jewelKey;
          });
    } else {
      this.jewelKey = null;
    }
  }

  @Override
  public void writeToNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (this.jewelKey == null) {
      tag.remove(JEWEL_KEY);
    } else {
      tag.put(JEWEL_KEY, TotemOverhaulRegistries.JEWEL_CODEC
          .encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.jewelKey).getOrThrow());
    }
  }
}
