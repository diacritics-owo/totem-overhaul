package diacritics.owo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import diacritics.owo.component.TotemOverhaulComponents;

@Mixin(VexEntity.class)
abstract public class VexEntityMixin extends Entity {
  @Shadow
  abstract public MobEntity getOwner();

  public VexEntityMixin(EntityType<?> type, World world) {
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
