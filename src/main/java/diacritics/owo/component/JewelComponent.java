package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import java.util.function.Function;

public class JewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";
  public static final String CAN_HAVE_JEWEL_KEY = "canHaveJewel";

  private final LivingEntity provider;
  private RegistryKey<Jewel> jewelKey;
  private boolean canHaveJewel = true;

  public JewelComponent(LivingEntity provider) {
    this.provider = provider;
  }

  public JewelComponent(LivingEntity provider, RegistryKey<Jewel> jewelKey) {
    this.provider = provider;
    this.setJewelKey(jewelKey);
  }

  public Jewel getJewel() {
    return TotemOverhaulRegistries.JEWEL.get(this.getJewelKey());
  }

  public RegistryKey<Jewel> getJewelKey() {
    if (!this.canHaveJewel) {
      return null;
    }

    return this.jewelKey == null ? TotemOverhaulRegistries.JEWEL.getKey(Jewels.DEFAULT).orElse(null)
        : this.jewelKey;
  }

  public void setJewel(Jewel jewel) {
    this.setJewelKey(TotemOverhaulRegistries.JEWEL.getKey(jewel).orElse(null));
  }

  public void setJewelKey(RegistryKey<Jewel> jewelKey) {
    this.jewelKey = jewelKey;
    TotemOverhaulComponents.JEWEL.sync(this.provider);
  }

  public void killedEntity(ServerWorld world, LivingEntity entity) {
    if (entity instanceof PlayerEntity) {
      this.setJewel(Jewels.RED);
    } else if (entity instanceof MerchantEntity) {
      this.setJewel(Jewels.GREEN);
    } else if (entity instanceof GolemEntity) {
      this.setJewel(Jewels.BLUE);
    }
  }

  public boolean tryUseJewel(DamageSource source) {
    if (this.jewelKey == null || source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      return false;
    }

    Function<LivingEntity, Boolean> effect =
        TotemOverhaulRegistries.JEWEL_EFFECTS.get(this.jewelKey.getValue());
    this.provider.dropItem(
        Registries.ITEM.get(TotemOverhaulRegistries.JEWEL_ITEMS.get(this.jewelKey.getValue())));

    this.canHaveJewel = false;
    this.setJewelKey(null);

    if (effect != null) {
      return effect.apply(this.provider);
    }

    return false;
  }

  @Override
  public void readFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (tag.contains(JEWEL_KEY)) {
      TotemOverhaulRegistries.JEWEL_CODEC
          .parse(registryLookup.getOps(NbtOps.INSTANCE), tag.get(JEWEL_KEY))
          .resultOrPartial(jewelKey -> {
            TotemOverhaul.LOGGER.error("failed to parse registrykey<jewel>: '{}'", jewelKey);
          }).ifPresent(jewelKey -> {
            this.setJewelKey(jewelKey);
          });
    } else {
      this.setJewelKey(null);
    }

    if (tag.contains(CAN_HAVE_JEWEL_KEY)) {
      this.canHaveJewel = tag.getBoolean(CAN_HAVE_JEWEL_KEY);
    } else {
      this.canHaveJewel = true;
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

    tag.putBoolean(CAN_HAVE_JEWEL_KEY, canHaveJewel);
  }
}
