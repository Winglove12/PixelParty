package pixelparty.map.tiles;

import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.map.object.TileBlueprint;
import pixelparty.map.object.TileType;
import pixelparty.ppuser.object.PPUser;

public class NormalTileBlueprint extends TileBlueprint {
    @Override
    public TileType tileType() {
        return TileType.NORMAL;
    }

    @Override
    public void onCustomLand(PPUser ppUser) {
        new BukkitRunnable(){
            public void run(){
                Game.getGame().getBoard().moveToNextPlayer();
            }
        }.runTaskLater(PPCore.getCore(), 60);
    }
}
