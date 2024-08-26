package diacritics.owo.render.entity.feature;

import diacritics.owo.component.EntityJewelComponent;
import diacritics.owo.component.TotemOverhaulComponents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EvokerEntity;

public class EvokerJewelFeatureRenderer
    extends FeatureRenderer<EvokerEntity, IllagerEntityModel<EvokerEntity>> {
  public EvokerJewelFeatureRenderer(
      FeatureRendererContext<EvokerEntity, IllagerEntityModel<EvokerEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      EvokerEntity evoker, float f, float g, float h, float j, float k, float l) {
    if (!evoker.isInvisible()) {
      EntityJewelComponent jewelComponent = TotemOverhaulComponents.JEWEL.get(evoker);

      if (jewelComponent.getJewel() != null) {
        renderModel(this.getContextModel(), jewelComponent.getFeatureTexture("illager/evoker"),
            matrixStack, vertexConsumerProvider, i, evoker, -1);
      }
    }
  }
}

