package pixelparty.map.object;

import org.bukkit.Location;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.ppuser.object.PPUser;

public abstract class TileBlueprint {

    public abstract TileType tileType();

    public void onLandFinish(PPUser ppUser){
        if(Game.getGame().getPlayers().size() < Game.getGame().getPlayers().indexOf(ppUser)){
            
        }
    }
    public abstract void onCustomLand(PPUser ppUser);

    public TileBlueprint(){
        PPCore.getCore().getGameMapManager().getTileTypeMap().put(tileType(), this);
    }

    public static TileType getTileFromString(String typeString){
        for(TileType type: TileType.values()){
            if(type.toString().equalsIgnoreCase(typeString)){
                return type;
            }
        }
        return TileType.NORMAL;
    }
}
