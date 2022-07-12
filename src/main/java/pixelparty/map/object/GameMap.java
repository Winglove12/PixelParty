package pixelparty.map.object;

import lombok.Getter;
import mayhem.file.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import pixelparty.PPCore;
import pixelparty.utils.LocationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class GameMap {

    private String mapName;
    private List<GameTile> tileMap = new ArrayList<>();
    private List<Location> startLocations;
    private int maxPlayers;
    private Location camStartLocation;
    private String displayName;
    private List<String> description;
    private File mapDirectory;
    private File tileMapDirectory;
    private File mapFile;

    public GameMap(String mapName, List<GameTile> tileMap, List<Location> startLocation, Location camStartLocation, int maxPlayers,
                   String displayName, List<String> description){
        if(PPCore.getCore().getGameMapManager().getGameMapHashMap().containsKey(mapName)){
            return;
        }
        this.mapName = mapName.toLowerCase();
        this.tileMap = tileMap;
        this.startLocations = startLocation;
        this.camStartLocation = camStartLocation;
        this.maxPlayers  =maxPlayers;
        this.displayName = displayName;
        this.description = description;
        PPCore.getCore().getGameMapManager().getGameMapHashMap().put(mapName, this);
    }
    public GameMap(File mapDirectory){
        this.mapDirectory = mapDirectory;
        if(mapDirectory == null){
            return;
        }
        for(File f: Objects.requireNonNull(mapDirectory.listFiles())){
            if(f.getName().contains(mapDirectory.getName())){
                this.mapFile = f;
            }else if(f.getName().contains("tilemap")){
                this.tileMapDirectory = f;
            }
        }
        if(mapFile == null){
            return;
        }
        this.mapName = mapFile.getName().substring(0, mapFile.getName().length() - 4);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mapFile);
        this.displayName = config.getString("displayname");
        this.description = config.getStringList("description");
        this.maxPlayers = config.getInt("maxplayers");
        this.camStartLocation = LocationUtil.locationFromStringList(config.getStringList("camstartlocation"));
        this.startLocations = new ArrayList<>();
        for(int i =0; i < maxPlayers; i++){
            startLocations.add(LocationUtil.locationFromStringList(config.getStringList("startlocations." + i)));
        }
        tileMap = new ArrayList<>();
        File[] tileFilesSorted = new File[tileMapDirectory.listFiles().length];
        for(File file: tileMapDirectory.listFiles()){
            String fileName = file.getName();
            fileName = fileName.substring(fileName.indexOf("e") + 1, fileName.indexOf("."));
            tileFilesSorted[Integer.parseInt(fileName)] = file;
        }
        for(File f: tileFilesSorted){
            YamlConfiguration tileConfig = YamlConfiguration.loadConfiguration(f);
            TileType tileType = TileBlueprint.getTileFromString(tileConfig.getString("tiletype"));
            Location centerLoc = LocationUtil.locationFromStringList(tileConfig.getStringList("centerlocation"));
            Location camLoc = LocationUtil.locationFromStringList(tileConfig.getStringList("camlocation"));
            new GameTile(this, tileType, centerLoc, camLoc, f);
        }
        PPCore.getCore().getGameMapManager().getGameMapHashMap().put(mapName, this);
    }

    public void createFile(){
        File directory = PPCore.getCore().getGameMapManager().getMapDirectory();
        mapDirectory = FileManager.createFolder(directory, mapName);
        tileMapDirectory= FileManager.createFolder(mapDirectory, "tilemap");
        mapFile = FileManager.createYMLFile(mapDirectory, mapName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mapFile);
        config.set("displayname", displayName);
        config.set("description", description);
        config.set("maxplayers", maxPlayers);
        config.set("camstartlocation", LocationUtil.locationToStringList(camStartLocation));
        Location placeholder = new Location(camStartLocation.getWorld(), 0, 0, 0, 0, 0);
        for(int i =0; i < maxPlayers; i++){
            config.set("startlocations." + i, LocationUtil.locationToStringList(placeholder));
        }
        try{
            config.save(mapFile);
        }catch(IOException ignored){

        }
    }

    public void setSpawnLoc(Location location, int numOfLoc){
        startLocations.set(numOfLoc, location);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mapFile);
        config.set("startlocations." + numOfLoc, LocationUtil.locationToStringList(location));
        try{
            config.save(mapFile);
        }catch(IOException ignored) {
        }
    }

    public void addTile(TileType type, Location centerLoc){

    }

    public static boolean isValidMap(String string){
        return PPCore.getCore().getGameMapManager().getGameMapHashMap().containsKey(string);
    }

    public static GameMap getMap(String string){
        return PPCore.getCore().getGameMapManager().getGameMapHashMap().get(string);
    }

}
