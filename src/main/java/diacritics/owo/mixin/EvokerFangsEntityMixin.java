package diacritics.owo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import diacritics.owo.component.TotemOverhaulComponents;

@Mixin(EvokerFangsEntity.class)
abstract public class EvokerFangsEntityMixin extends Entity {
  @Shadow
  abstract public LivingEntity getOwner();

  public EvokerFangsEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Override
  public boolean onKilledOther(ServerWorld world, LivingEntity other) {
    if (this.getOwner() instanceof EvokerEntity evoker) {
      TotemOverhaulComponents.JEWEL.get(evoker).killedEntity(world, other);
    }

    return true;
  }
}
