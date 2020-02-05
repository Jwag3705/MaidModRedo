package mmr.maidmodredo.world.feature.structure;

import mmr.maidmodredo.MaidModRedo;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

public interface IMaidStructurePieceType {
    IStructurePieceType MAIDCAFE = register(MaidCafePieces.Piece::new, MaidModRedo.MODID + ".maidcafe");

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}