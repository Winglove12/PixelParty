package pixelparty.minigame;

import lombok.Getter;
import lombok.Setter;
import mayhem.Manager;
import org.bukkit.Bukkit;
import pixelparty.PPCore;
import pixelparty.minigame.listeners.PracticeListener;
import pixelparty.minigame.minigames.LumberjackListener;
import pixelparty.minigame.object.Minigame;
import pixelparty.minigame.object.MinigameName;
@Getter
@Setter
public class MinigameManager extends Manager {
    public MinigameManager() {
        super("Minigame Manager");
        Bukkit.getPluginManager().registerEvents( new PracticeListener(), PPCore.getCore());
        Bukkit.getPluginManager().registerEvents(new LumberjackListener(), PPCore.getCore());
    }

    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }
}
