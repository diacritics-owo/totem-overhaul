package diacritics.owo;

import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.jewel.Jewel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Items;

public class TotemOverhaulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if (tintIndex == 1) {
				if (stack.get(TotemOverhaulDataComponentTypes.JEWEL) instanceof Jewel jewel) {
					return 0xFF000000 + jewel.color();
				}

				return 0x00000000;
			}

			return 0xFFFFFFFF;
		}, Items.TOTEM_OF_UNDYING);
	}
}
