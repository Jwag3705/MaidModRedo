package mmr.maidmodredo.init;

import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleButlerEntity;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.entity.monstermaid.EnderMaidEntity;

import java.util.List;

public class MaidModels {
    public static List<MaidModels> maidModelsList = Lists.newArrayList();

    public static final MaidModels LITTLEMAID = new MaidModels("littlemaid", LittleMaidEntity.class);
    public static final MaidModels LITTLEBUTLER = new MaidModels("littlebutler", LittleButlerEntity.class);
    public static final MaidModels ENDERMAID = new MaidModels("endermaid", EnderMaidEntity.class);

    private final String modelName;
    private final Class entityClass;

    public MaidModels(String modelName, Class entityClass) {
        this.modelName = modelName;
        this.entityClass = entityClass;
    }

    public String getModelName() {
        return modelName;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public static void registerModel() {
        maidModelsList.add(LITTLEMAID);
        maidModelsList.add(LITTLEBUTLER);
        maidModelsList.add(ENDERMAID);
    }
}
