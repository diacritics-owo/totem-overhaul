package diacritics.owo;

import diacritics.owo.component.EntityJewelComponent;
import diacritics.owo.component.TotemOverhaulComponents;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class TotemOverhaulJadePlugin implements IWailaPlugin {
  @Override
  public void register(IWailaCommonRegistration registration) {
    registration.registerEntityDataProvider(EntityJewelComponentProvider.INSTANCE, Entity.class);
  }

  @Override
  public void registerClient(IWailaClientRegistration registration) {
    registration.registerEntityComponent(EntityJewelComponentProvider.INSTANCE, Entity.class);
  }

  public enum EntityJewelComponentProvider
      implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
      if (accessor.getServerData().contains("jewel")) {
        tooltip.add(Text.translatable(accessor.getServerData().getString("jewel")));
      }
    }

    @Override
    public Identifier getUid() {
      return TotemOverhaul.identifier("jewel");
    }

    @Override
    public void appendServerData(NbtCompound data, EntityAccessor accessor) {
      if (TotemOverhaulComponents.JEWEL
          .getNullable(accessor.getEntity()) instanceof EntityJewelComponent jewelComponent) {
        data.putString("jewel", jewelComponent.getJewelKey().getValue().toTranslationKey("jewel"));
      }
    }
  }
}
