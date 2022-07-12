package pixelparty.character.models;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;

public class ModelUtils {

    public static ModeledEntity setMobToModel(org.bukkit.entity.Entity ent, String modelName) {
        ActiveModel model = ModelEngineAPI.api.getModelManager().createActiveModel(modelName.toLowerCase());
        if (model == null) {
            return null;
        }
        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(ent);
        if (modeledEntity == null) {
            return null;
        }
        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
        model.setClamp(0);
        return modeledEntity;
    }
}
