package mmr.littledelicacies.world.feature.structure;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.entity.LittleButlerEntity;
import mmr.littledelicacies.entity.LittleMaidEntity;
import mmr.littledelicacies.init.LittleEntitys;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MaidCafePieces {
    private static final ResourceLocation maidcafe = new ResourceLocation(LittleDelicacies.MODID, "maidcafe");
    private static final ResourceLocation brich_maidcafe = new ResourceLocation(LittleDelicacies.MODID, "brich_maidcafe");


    private static final Map<ResourceLocation, BlockPos> structurePos = ImmutableMap.of(maidcafe, new BlockPos(0, -3, 0), brich_maidcafe, new BlockPos(0, -2, 0));

    public static void addStructure(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> list, Random p_207617_4_, Biome biome) {

        if (biome == Biomes.BIRCH_FOREST || biome == Biomes.BIRCH_FOREST_HILLS || biome == Biomes.TALL_BIRCH_FOREST || biome == Biomes.TALL_BIRCH_HILLS) {
            addGenerate(list, new MaidCafePieces.Piece(templateManager, brich_maidcafe, pos, rotation, 0));
        } else {
            addGenerate(list, new MaidCafePieces.Piece(templateManager, maidcafe, pos, rotation, 0));
        }
    }

    private static MaidCafePieces.Piece addGenerate(List<StructurePiece> p_189935_0_, MaidCafePieces.Piece p_189935_1_) {
        p_189935_0_.add(p_189935_1_);
        return p_189935_1_;
    }


    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation field_207615_d;
        private final Rotation field_207616_e;

        public Piece(TemplateManager p_i49313_1_, ResourceLocation p_i49313_2_, BlockPos p_i49313_3_, Rotation p_i49313_4_, int p_i49313_5_) {
            super(IMaidStructurePieceType.MAIDCAFE, 0);
            this.field_207615_d = p_i49313_2_;
            BlockPos blockpos = MaidCafePieces.structurePos.get(p_i49313_2_);
            this.templatePosition = p_i49313_3_.add(blockpos.getX(), blockpos.getY() - p_i49313_5_, blockpos.getZ());
            this.field_207616_e = p_i49313_4_;
            this.func_207614_a(p_i49313_1_);
        }

        public Piece(TemplateManager p_i50566_1_, CompoundNBT p_i50566_2_) {
            super(IMaidStructurePieceType.MAIDCAFE, p_i50566_2_);
            this.field_207615_d = new ResourceLocation(p_i50566_2_.getString("Template"));
            this.field_207616_e = Rotation.valueOf(p_i50566_2_.getString("Rot"));
            this.func_207614_a(p_i50566_1_);
        }

        private void func_207614_a(TemplateManager p_207614_1_) {
            Template template = p_207614_1_.getTemplateDefaulted(this.field_207615_d);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(MaidCafePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.field_207615_d.toString());
            tagCompound.putString("Rot", this.field_207616_e.name());
        }

        protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random rand, MutableBoundingBox box) {
            if (function.equals("Maid")) {
                LittleMaidEntity entityturret = LittleEntitys.LITTLEMAID.create(world.getWorld());
                entityturret.enablePersistence();
                entityturret.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                entityturret.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                world.addEntity(entityturret);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else if (function.equals("Butler")) {
                LittleButlerEntity entityturret = LittleEntitys.LITTLEBUTLER.create(world.getWorld());
                entityturret.enablePersistence();
                entityturret.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                entityturret.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                world.addEntity(entityturret);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }

        @Override
        public boolean func_225577_a_(IWorld worldIn, ChunkGenerator<?> p_225577_2_, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(MaidCafePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos = MaidCafePieces.structurePos.get(this.field_207615_d);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(blockpos.getX(), 0, blockpos.getZ())));
            int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
            BlockPos blockpos2 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
            boolean flag = super.func_225577_a_(worldIn, p_225577_2_, randomIn, structureBoundingBoxIn, chunkPos);


            this.templatePosition = blockpos2;
            return flag;
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */


    }
}