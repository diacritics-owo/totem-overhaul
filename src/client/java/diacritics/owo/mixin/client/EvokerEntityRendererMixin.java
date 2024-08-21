package diacritics.owo.mixin.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.EvokerEntityRenderer;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.EvokerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import diacritics.owo.render.entity.feature.EvokerJewelFeatureRenderer;

@Mixin(EvokerEntityRenderer.class)
abstract public class EvokerEntityRendererMixin extends IllagerEntityRenderer<EvokerEntity> {
	protected EvokerEntityRendererMixin(Context ctx, IllagerEntityModel<EvokerEntity> model,
			float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(at = @At("RETURN"), method = "<init>*")
	private void onConstructed(EntityRendererFactory.Context context, CallbackInfo info) {
		this.addFeature(new EvokerJewelFeatureRenderer(this));
	}
}
