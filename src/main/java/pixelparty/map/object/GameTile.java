package pixelparty.map.object;

import lombok.Getter;
import lombok.Setter;
import mayhem.file.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.ppuser.object.PPUser;
import pixelparty.utils.LocationUtil;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class GameTile {

    private GameMap gameMap;
    private TileType tileType;
    private Location centerLoc;
    private Location camLoc;
    private int id;
    private File tileFile;

    public GameTile(GameMap gameMap, TileType tileType, Location centerLoc){
        this.tileType = tileType;
        this.centerLoc = centerLoc;
        this.camLoc = new Location(centerLoc.getWorld(), 0, 0, 0);
        this.gameMap = gameMap;
        this.id = gameMap.getTileMap().size();
        createFile();
        gameMap.getTileMap().add(this);
    }
    public GameTile(GameMap gameMap, TileType tileType, Location centerLoc, int position){
        this.tileType = tileType;
        this.centerLoc = centerLoc;
        this.camLoc = new Location(centerLoc.getWorld(), 0, 0, 0);
        this.gameMap = gameMap;
        this.id = gameMap.getTileMap().size();
        createFile();
        gameMap.getTileMap().add(position, this);
        resetFiles(gameMap);
    }

    public GameTile(GameMap gameMap, TileType tileType, Location centerLoc, Location camLoc, File tileFile){
        this.tileType = tileType;
        this.centerLoc = centerLoc;
        this.camLoc = camLoc;
        this.gameMap = gameMap;
        this.tileFile = tileFile;
        this.id = gameMap.getTileMap().size();
        gameMap.getTileMap().add(this);
    }

    public void onLand(PPUser ppUser){
        PPCore.getCore().getGameMapManager().getTileTypeMap().get(tileType).onCustomLand(ppUser);
    }

    private void createFile(){
        File tileMapDirectory = gameMap.getTileMapDirectory();
        this.tileFile = FileManager.createYMLFile(tileMapDirectory, "tile" + id);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(tileFile);
        config.set("tiletype", tileType.toString());
        config.set("centerlocation", LocationUtil.locationToStringList(centerLoc));
        config.set("camlocation", LocationUtil.locationToStringList(camLoc));
        try{
            config.save(tileFile);
        }catch(IOException ignored) {
        }
    }

    public static void removeTile(GameMap gameMap, int id){
        gameMap.getTileMap().remove(id);
        resetFiles(gameMap);
    }

    public static void setTileCam(GameMap gameMap, int id, Location location){
        GameTile tile = gameMap.getTileMap().get(id);
        tile.setCamLoc(location);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(tile.getTileFile());
        config.set("camlocation", LocationUtil.locationToStringList(location));
        System.out.println(config.get("camlocation").toString());
        try{
            config.save(tile.getTileFile());
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetFiles(GameMap gameMap){
        for(File f: gameMap.getTileMapDirectory().listFiles()){
            f.delete();
        }
        for(GameTile gameTile: gameMap.getTileMap()){
            gameTile.setId(gameMap.getTileMap().indexOf(gameTile));
            gameTile.createFile();
        }
    }

    public Location getCornerLocation(int i){
        Location loc = getCenterLoc().clone();
        if(loc.getYaw() == 90) {
            switch (i) {
                case 1:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 2:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 3:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 4:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
            }
        }else if(loc.getYaw() == 0){
            switch (i) {
                case 1:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 2:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 3:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 4:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
            }
        }else if(loc.getYaw() == -90){
            switch (i) {
                case 1:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 2:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 3:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 4:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
            }
        }else if(loc.getYaw() == -180 || loc.getYaw() == 180){
            switch (i) {
                case 1:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 2:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() - 1);
                    break;
                case 3:
                    loc.setX(loc.getX() - 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
                case 4:
                    loc.setX(loc.getX() + 1);
                    loc.setZ(loc.getZ() + 1);
                    break;
            }
        }
        return loc;
    }
}
