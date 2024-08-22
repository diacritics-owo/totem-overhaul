package diacritics.owo.render.entity.feature;

import diacritics.owo.component.JewelComponent;
import diacritics.owo.jewel.Jewel;
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
      // TODO: doesn't update properly when getting it directly via cardinal components

      Jewel jewel = JewelComponent.getFor(evoker);

      if (jewel != null) {
        renderModel(this.getContextModel(), jewel.getFeatureTexture("illager/evoker"), matrixStack,
            vertexConsumerProvider, i, evoker, -1);
      }
    }
  }
}

