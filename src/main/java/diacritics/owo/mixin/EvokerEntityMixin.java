package diacritics.owo.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import diacritics.owo.Helpers;
import diacritics.owo.component.TotemOverhaulComponents;

@Mixin(EvokerEntity.class)
abstract public class EvokerEntityMixin extends LivingEntity {
  protected EvokerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  public boolean onKilledOther(ServerWorld world, LivingEntity other) {
    Helpers.onEvokerKilledEntity(TotemOverhaulComponents.JEWEL.get(this), other);
    return true;
  }
}
