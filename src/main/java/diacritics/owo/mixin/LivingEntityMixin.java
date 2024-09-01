package diacritics.owo.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import diacritics.owo.component.TotemOverhaulComponents;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.registry.TotemOverhaulRegistries;
import java.util.function.BiFunction;

@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {
  public LivingEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Shadow
  abstract public ItemStack getStackInHand(Hand hand);

  @WrapOperation(method = "damage", at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/LivingEntity;tryUseTotem(Lnet/minecraft/entity/damage/DamageSource;)Z"))
  private boolean useEvokerJewel(LivingEntity instance, DamageSource source,
      Operation<Boolean> original) {
    if (instance instanceof EvokerEntity evoker
        && TotemOverhaulComponents.JEWEL.get(evoker).tryUseJewel(source)) {
      return true;
    }

    return original.call(instance, source);
  }

  @Overwrite
  /*
   * @reason The totem usage logic is replaced completely, necessitating an overwrite
   */
  private boolean tryUseTotem(DamageSource source) {
    Hand[] heldItems = Hand.values();

    for (int i = 0; i < heldItems.length; ++i) {
      Hand hand = heldItems[i];
      ItemStack held = this.getStackInHand(hand);
      if (held.isOf(Items.TOTEM_OF_UNDYING)
          && held.get(TotemOverhaulDataComponentTypes.JEWEL) != null) {
        BiFunction<LivingEntity, DamageSource, Boolean> effect =
            TotemOverhaulRegistries.JEWEL_EFFECT
                .get(held.get(TotemOverhaulDataComponentTypes.JEWEL).getValue());

        if (effect != null && effect.apply((LivingEntity) (Object) this, source)) {
          if ((Object) this instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
            Criteria.USED_TOTEM.trigger(serverPlayerEntity, held);
            this.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
          }

          held.decrement(1);

          return true;
        }
      }
    }

    return false;
  }
}
