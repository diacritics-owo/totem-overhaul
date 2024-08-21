package diacritics.owo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import diacritics.owo.component.TotemOverhaulComponents;

@Mixin(EvokerEntity.class)
abstract public class EvokerEntityMixin extends Entity {
  public EvokerEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Override
  public boolean onKilledOther(ServerWorld world, LivingEntity other) {
    TotemOverhaulComponents.JEWEL.get(this).killedEntity(world, other);
    return true;
  }
}
