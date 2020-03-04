package mmr.maidmodredo.world.feature;

import com.mojang.datafixers.Dynamic;
import mmr.maidmodredo.init.LittleFeatures;
import mmr.maidmodredo.world.feature.structure.BigTreePieces;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class BigTreeStructure extends Structure<NoFeatureConfig> {
    public BigTreeStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51440_1_) {
        super(p_i51440_1_);
    }

    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = this.getBiomeFeatureDistance(chunkGenerator);
        int j = this.getBiomeFeatureSeparation(chunkGenerator);
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, this.getSeedModifier());
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }

    @Override
    public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome p_225558_6_) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
        if (chunkGen.getSettings() instanceof OverworldGenSettings) {
            if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
                Biome biome = p_225558_1_.getBiome(new BlockPos(chunkPosX * 16 + 9, 0, chunkPosZ * 16 + 9));
                if (chunkGen.hasStructure(biome, this)) {

                    for (int k = chunkPosX - 10; k <= chunkPosX + 10; ++k) {
                        for (int l = chunkPosZ - 10; l <= chunkPosZ + 10; ++l) {
                            if (LittleFeatures.MAIDCAFE.func_225558_a_(p_225558_1_, chunkGen, rand, k, l, p_225558_1_.getBiome(new BlockPos((k << 4) + 9, 0, (l << 4) + 9)))) {
                                return false;
                            }
                        }
                    }


                    return true;
                }
            }
        }


        return false;

    }

    public String getStructureName() {
        return "maidmodredo:bigtree";
    }

    public int getSize() {
        return 3;
    }

    public IStartFactory getStartFactory() {
        return BigTreeStructure.Start::new;
    }

    protected int getSeedModifier() {
        return 12475312;
    }

    protected int getBiomeFeatureDistance(ChunkGenerator<?> chunkGenerator) {
        return 34;
    }

    protected int getBiomeFeatureSeparation(ChunkGenerator<?> chunkGenerator) {
        return 3;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i50460_1_, int p_i50460_2_, int p_i50460_3_, MutableBoundingBox p_i50460_5_, int p_i50460_6_, long p_i50460_7_) {
            super(p_i50460_1_, p_i50460_2_, p_i50460_3_, p_i50460_5_, p_i50460_6_, p_i50460_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {

            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);

            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            BigTreePieces.addStructure(templateManagerIn, blockpos, rotation, this.components, this.rand, biomeIn);
            this.recalculateStructureSize();
        }

        public void func_225565_a_(IWorld p_225565_1_, ChunkGenerator<?> p_225565_2_, Random p_225565_3_, MutableBoundingBox p_225565_4_, ChunkPos p_225565_5_) {
            super.func_225565_a_(p_225565_1_, p_225565_2_, p_225565_3_, p_225565_4_, p_225565_5_);
            int i = this.bounds.minY;

            for (int j = p_225565_4_.minX; j <= p_225565_4_.maxX; ++j) {
                for (int k = p_225565_4_.minZ; k <= p_225565_4_.maxZ; ++k) {
                    BlockPos blockpos = new BlockPos(j, i, k);
                    if (!p_225565_1_.isAirBlock(blockpos) && this.bounds.isVecInside(blockpos)) {
                        boolean flag = false;

                        for (StructurePiece structurepiece : this.components) {
                            if (structurepiece.getBoundingBox().isVecInside(blockpos)) {
                                flag = true;
                                break;
                            }
                        }

                        if (flag) {
                            for (int l = i - 1; l > 1; --l) {
                                BlockPos blockpos1 = new BlockPos(j, l, k);
                                if (!p_225565_1_.isAirBlock(blockpos1) && !p_225565_1_.getBlockState(blockpos1).getMaterial().isLiquid()) {
                                    break;
                                }

                                p_225565_1_.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

        }
    }
}