package pixelparty.game;

import lombok.Getter;
import mayhem.Manager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.game.game.rolloff.RollListeners;

public class GameManager extends Manager {

    @Getter
    Game game;

    public GameManager() {
        super("Game");
        new BukkitRunnable(){
            public void run(){
                game = new Game(3, PPCore.getCore().getGameMapManager().getGameMapHashMap().get("pixelisland"));
            }
        }.runTaskLater(PPCore.getCore(), 10);
        Bukkit.getPluginManager().registerEvents(new RollListeners(), PPCore.getCore());
    }


    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }
}
