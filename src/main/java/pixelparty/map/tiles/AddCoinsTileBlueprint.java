package pixelparty.map.tiles;

import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.map.object.TileBlueprint;
import pixelparty.map.object.TileType;
import pixelparty.ppuser.object.PPUser;
import pixelparty.utils.TitleUtil;

public class AddCoinsTileBlueprint extends TileBlueprint {
    @Override
    public TileType tileType() {
        return TileType.ADDCOINS;
    }

    @Override
    public void onCustomLand(PPUser ppUser) {
        ppUser.getCoins().addCoins(4);
        TitleUtil.sendTitleToAll(ppUser.getName() + " has gained 4 coins", "");
        new BukkitRunnable(){
            public void run(){
                Game.getGame().getBoard().moveToNextPlayer();
            }
        }.runTaskLater(PPCore.getCore(), 60);
    }
}
