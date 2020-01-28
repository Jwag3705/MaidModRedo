package mmr.maidmodredo.init;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Random;

public class MaidTrades extends VillagerTrades {
    public static final Int2ObjectMap<ITrade[]> field_221240_b = func_221238_a(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new GoldForItemsTrade(Items.SUGAR, 14, 6, 2),new GoldForItemsTrade(Items.EGG, 10, 4, 2),new ItemsForGoldsTrade(Items.PUMPKIN_PIE, 1, 10, 3,3),new ItemsForGoldsTrade(Items.COOKIE, 1, 14, 2,3),new ItemsForGoldsTrade(Items.CAKE, 2, 1, 4, 4),new ItemsForGoldsTrade(Items.SWEET_BERRIES, 1, 12, 3, 3)},
            2, new VillagerTrades.ITrade[]{new GoldForItemsTrade(Items.FEATHER, 8, 4, 3), new ItemsForGoldsTrade(Items.LEATHER, 1, 6, 3, 4), new ItemsForGoldsTrade(Items.RABBIT_FOOT, 6, 3, 3, 3), new ItemsForGoldsTrade(Items.NAME_TAG, 10, 1, 2, 6), new ItemsForGoldsTrade(Items.ZOMBIE_HEAD, 18, 1, 1, 6), new ItemsForGoldsTrade(Items.SKELETON_SKULL, 18, 1, 1, 6)}));

    private static Int2ObjectMap<VillagerTrades.ITrade[]> func_221238_a(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class GoldForItemsTrade implements VillagerTrades.ITrade {
        private final Item field_221183_a;
        private final int field_221184_b;
        private final int field_221185_c;
        private final int field_221186_d;
        private final float field_221187_e;

        public GoldForItemsTrade(IItemProvider p_i50539_1_, int size, int maxUsesIn, int givenEXPIn) {
            this.field_221183_a = p_i50539_1_.asItem();
            this.field_221184_b = size;
            this.field_221185_c = maxUsesIn;
            this.field_221186_d = givenEXPIn;
            this.field_221187_e = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            ItemStack itemstack = new ItemStack(this.field_221183_a, this.field_221184_b);
            return new MerchantOffer(itemstack, new ItemStack(Items.GOLD_NUGGET), this.field_221185_c, this.field_221186_d, this.field_221187_e);
        }
    }

    static class ItemsForGoldsTrade implements VillagerTrades.ITrade {
        private final ItemStack field_221208_a;
        private final int field_221209_b;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public ItemsForGoldsTrade(Block p_i50528_1_, int buySize, int sellSize, int maxUsesIn, int givenEXPIn) {
            this(new ItemStack(p_i50528_1_), buySize, sellSize, maxUsesIn, givenEXPIn);
        }

        public ItemsForGoldsTrade(Item p_i50529_1_, int buySize, int sellSize, int givenEXPIn) {
            this(new ItemStack(p_i50529_1_), buySize, sellSize, 12, givenEXPIn);
        }

        public ItemsForGoldsTrade(Item p_i50530_1_, int buySize, int sellSize, int maxUsesIn, int givenEXPIn) {
            this(new ItemStack(p_i50530_1_), buySize, sellSize, maxUsesIn, givenEXPIn);
        }

        public ItemsForGoldsTrade(ItemStack p_i50531_1_, int buySize, int sellSize, int maxUsesIn, int givenEXPIn) {
            this(p_i50531_1_, buySize, sellSize, maxUsesIn, givenEXPIn, 0.05F);
        }

        public ItemsForGoldsTrade(ItemStack p_i50532_1_, int buySize, int sellSize, int maxUsesIn, int p_i50532_5_, float p_i50532_6_) {
            this.field_221208_a = p_i50532_1_;
            this.field_221209_b = buySize;
            this.field_221210_c = sellSize;
            this.field_221211_d = maxUsesIn;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.GOLD_NUGGET, this.field_221209_b), new ItemStack(this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }
}