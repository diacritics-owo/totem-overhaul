package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EvokerEntity;
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

// TODO: generalise it to all entities?
public class EvokerJewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";

  private final EvokerEntity evoker;
  private RegistryKey<Jewel> jewelKey;

  public EvokerJewelComponent(EvokerEntity evoker) {
    this.evoker = evoker;
  }

  public EvokerJewelComponent(EvokerEntity evoker, RegistryKey<Jewel> jewelKey) {
    this.evoker = evoker;
    this.setJewelKey(jewelKey);
  }

  public Jewel getJewel() {
    return TotemOverhaulRegistries.JEWEL.get(this.getJewelKey());
  }

  public RegistryKey<Jewel> getJewelKey() {
    return this.jewelKey == null ? TotemOverhaulRegistries.JEWEL.getKey(Jewels.DEFAULT).orElse(null)
        : this.jewelKey;
  }

  public void setJewel(Jewel jewel) {
    this.setJewelKey(TotemOverhaulRegistries.JEWEL.getKey(jewel).orElse(null));
  }

  public void setJewelKey(RegistryKey<Jewel> jewelKey) {
    this.jewelKey = jewelKey;
    TotemOverhaulComponents.JEWEL.sync(this.evoker);
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

    // TODO: remove jewel completely (this evoker can't get another)
    this.evoker.dropItem(
        Registries.ITEM.get(TotemOverhaulRegistries.JEWEL_ITEMS.get(this.jewelKey.getValue())));

    this.setJewelKey(null);

    // TODO: effects based on the jewel
    this.evoker.setHealth(1.0F);
    this.evoker.clearStatusEffects();
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    this.evoker.getWorld().sendEntityStatus(this.evoker, (byte) 35);

    return true;
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
