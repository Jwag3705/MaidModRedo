package mmr.littledelicacies.item;

import mmr.littledelicacies.api.IMaidArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;

public class BaguHatItem extends ArmorItem implements IMaidArmor {
    public BaguHatItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builder) {
        super(materialIn, slot, builder);
    }

    public String getMaidArmorTextureName() {
        return "bagu_hat";
    }

    ;
}
