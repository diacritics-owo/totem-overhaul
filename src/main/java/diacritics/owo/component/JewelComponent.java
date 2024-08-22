package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
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
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

public class JewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";

  // TODO: probably not a good solution - replace asap (see todo in evokerjewelfeaturerenderer)
  public static final Map<UUID, Jewel> JEWEL_MAP = new HashMap<>();

  private final EvokerEntity evoker;
  private Jewel jewel;

  public JewelComponent(EvokerEntity evoker) {
    this.evoker = evoker;
  }

  public JewelComponent(EvokerEntity evoker, Jewel jewel) {
    this.evoker = evoker;
    this.setJewel(jewel);
  }

  public Jewel getJewel() {
    return this.jewel == null ? Jewels.DEFAULT : this.jewel;
  }

  public void setJewel(Jewel jewel) {
    this.jewel = jewel;
    JEWEL_MAP.put(evoker.getUuid(), this.getJewel());
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
    if (this.jewel == null || source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      return false;
    }

    // TODO: remove jewel completely (this evoker can't get another)
    // TODO: not a good solution for getting the item
    this.evoker.dropItem(Registries.ITEM.get(this.jewel.identifier().withSuffixedPath("_jewel")));

    this.setJewel(null);

    // TODO: effects based on the jewel
    this.evoker.setHealth(1.0F);
    this.evoker.clearStatusEffects();
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
    this.evoker.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
    this.evoker.getWorld().sendEntityStatus(this.evoker, (byte) 35);

    return true;
  }

  public static Jewel getFor(EvokerEntity evoker) {
    return JEWEL_MAP.get(evoker.getUuid());
  }

  @Override
  public void readFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (tag.contains(JEWEL_KEY)) {
      Jewel.CODEC.parse(registryLookup.getOps(NbtOps.INSTANCE), tag.get(JEWEL_KEY))
          .resultOrPartial(jewel -> {
            TotemOverhaul.LOGGER.error("failed to parse jewel: '{}'", jewel);
          }).ifPresent(jewel -> {
            this.setJewel(jewel);
          });
    } else {
      this.setJewel(null);
    }
  }

  @Override
  public void writeToNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (this.jewel == null) {
      tag.remove(JEWEL_KEY);
    } else {
      tag.put(JEWEL_KEY,
          Jewel.CODEC.encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.jewel).getOrThrow());
    }
  }
}
