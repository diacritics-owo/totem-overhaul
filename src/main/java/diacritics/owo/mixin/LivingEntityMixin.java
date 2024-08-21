package diacritics.owo.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import diacritics.owo.component.TotemOverhaulComponents;

@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin {
  @WrapOperation(method = "damage", at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/LivingEntity;tryUseTotem(Lnet/minecraft/entity/damage/DamageSource;)Z"))
  private boolean useEvokerJewel(LivingEntity instance, DamageSource source,
      Operation<Boolean> original) {
    if (instance instanceof EvokerEntity evoker && TotemOverhaulComponents.JEWEL.get(evoker).tryUseJewel(source)) {
      return true;
    }

    return original.call(instance, source);
  }
}
