package diacritics.owo.component;

import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.world.ServerWorld;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

public class JewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";

  // TODO: probably not a good solution - replace asap (see todo in evokerjewel feature renderer)
  public static final Map<UUID, RegistryKey<Jewel>> JEWEL_MAP = new HashMap<>();

  private final EvokerEntity evoker;
  private RegistryKey<Jewel> jewelKey;

  public JewelComponent(EvokerEntity evoker) {
    this.evoker = evoker;
  }

  public JewelComponent(EvokerEntity evoker, RegistryKey<Jewel> jewelKey) {
    this.evoker = evoker;
    this.setJewelKey(jewelKey);
  }

  @Nullable
  public RegistryKey<Jewel> getJewelKey() {
    return this.jewelKey;
  }

  public void setJewelKey(RegistryKey<Jewel> jewelKey) {
    this.jewelKey = jewelKey;
    JEWEL_MAP.put(evoker.getUuid(), jewelKey);
  }

  public void killedEntity(ServerWorld world, LivingEntity entity) {
    if (entity instanceof PlayerEntity) {
      this.setJewelKey(Jewels.RED);
    } else if (entity instanceof MerchantEntity) {
      this.setJewelKey(Jewels.GREEN);
    } else if (entity instanceof GolemEntity) {
      this.setJewelKey(Jewels.BLUE);
    }
  }

  @Override
  public void readFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (tag.contains(JEWEL_KEY)) {
      TotemOverhaulRegistries.JEWEL_CODEC
          .parse(registryLookup.getOps(NbtOps.INSTANCE), tag.get(JEWEL_KEY))
          .resultOrPartial(jewelKey -> {
            TotemOverhaul.LOGGER.error("failed to parse jewel registrykey: '{}'", jewelKey);
          }).ifPresent(jewelKey -> {
            this.setJewelKey(jewelKey);
          });
    } else {
      this.setJewelKey(null);
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
