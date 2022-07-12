package pixelparty.map;

import lombok.Getter;
import mayhem.Manager;
import mayhem.file.FileManager;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.map.commands.MapAdminCommand;
import pixelparty.map.object.GameMap;
import pixelparty.map.object.TileBlueprint;
import pixelparty.map.object.TileType;
import pixelparty.map.tiles.AddCoinsTileBlueprint;
import pixelparty.map.tiles.NormalTileBlueprint;
import pixelparty.map.tiles.RemoveCoinsTileBlueprint;
import pixelparty.map.tiles.StartTileBlueprint;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

@Getter
public class GameMapManager extends Manager {

    private File mapDirectory;
    private HashMap<String, GameMap> gameMapHashMap = new HashMap<>();
    private HashMap<TileType, TileBlueprint> tileTypeMap = new HashMap<>();

    public GameMapManager() {
        super("Game Map Manager");
        new BukkitRunnable(){
            public void run(){
                loadSavedMaps();;
                new StartTileBlueprint();
                new NormalTileBlueprint();
                new AddCoinsTileBlueprint();
                new RemoveCoinsTileBlueprint();
            }
        }.runTaskLater(PPCore.getCore(), 2);
        PPCore.getCore().getCommand("ma").setExecutor(new MapAdminCommand());
    }

    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }

    public void loadSavedMaps() {
        mapDirectory = FileManager.createFolder(PPCore.getCore().getDataFolder(), "gamemaps");
        //loop through all files and instantiate Arena object with each file's info.
        for (File directory : Objects.requireNonNull(mapDirectory.listFiles())) {
            new GameMap(directory);
        }
    }

}
