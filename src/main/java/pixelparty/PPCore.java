package pixelparty;

import lombok.Getter;
import mayhem.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import pixelparty.character.PlayerCharacterManager;
import pixelparty.game.GameManager;
import pixelparty.map.GameMapManager;
import pixelparty.minigame.MinigameManager;
import pixelparty.ppgeneral.PPGeneralManager;
import pixelparty.ppuser.PPUserManager;

@Getter
public class PPCore extends JavaPlugin {

    @Getter
    private static PPCore core;
    private PPUserManager ppUserManager;
    private GameMapManager gameMapManager;
    private GameManager gameManager;
    private PPGeneralManager ppGeneralManager;
    private PlayerCharacterManager playerCharacterManager;
    private MinigameManager minigameManager;

    @EventHandler
    public void onEnable(){
        core = this;
        if(!this.getDataFolder().exists()) {
            try {
                this.getDataFolder().mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.ppUserManager = new PPUserManager();
        this.gameMapManager = new GameMapManager();
        this.gameManager = new GameManager();
        this.ppGeneralManager = new PPGeneralManager();
        this.playerCharacterManager = new PlayerCharacterManager();
        this.minigameManager = new MinigameManager();
    }
}
