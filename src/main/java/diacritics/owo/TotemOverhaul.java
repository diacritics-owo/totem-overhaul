package diacritics.owo;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.item.TotemOverhaulItems;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.jewel.effect.JewelEffects;
import diacritics.owo.recipe.TotemOverhaulRecipeSerializers;
import diacritics.owo.registry.TotemOverhaulRegistries;

// TODO: unique gem textures
public class TotemOverhaul implements ModInitializer {
	public static final String MOD_ID = "totem-overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("hello from totem overhaul!");

		TotemOverhaulRegistries.initialize();
		Jewels.initialize();
		TotemOverhaulItems.initialize();
		TotemOverhaulDataComponentTypes.initialize();
		TotemOverhaulRecipeSerializers.initialize();
		JewelEffects.initialize();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
