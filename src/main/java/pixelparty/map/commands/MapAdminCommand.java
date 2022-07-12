package pixelparty.map.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.map.object.GameMap;
import pixelparty.map.object.GameTile;
import pixelparty.map.object.TileBlueprint;
import pixelparty.utils.IntegerUtil;

import java.util.ArrayList;

public class MapAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp()) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        p.sendMessage(PPCore.getCore().getGameMapManager().getGameMapHashMap().toString());
                    }
                } else if (args.length == 2) {
                    if (GameMap.isValidMap(args[0].toLowerCase())) {
                        GameMap gameMap = GameMap.getMap(args[0].toLowerCase());
                        if (args[1].equalsIgnoreCase("tilelist")) {
                            p.sendMessage(gameMap.getTileMap().toString());
                        }
                    }
                } else if (args.length > 2) {
                    if (GameMap.isValidMap(args[0].toLowerCase())) {
                        GameMap gameMap = GameMap.getMap(args[0].toLowerCase());
                        if (args[1].equalsIgnoreCase("setstartloc")) {
                            int startNum = IntegerUtil.getIntFromString(args[2]);
                            if (startNum < gameMap.getStartLocations().size() && startNum != -1) {
                                gameMap.setSpawnLoc(p.getLocation(), startNum);
                            }
                        } else if (args[1].equalsIgnoreCase("createtile")) {
                            if (args.length == 3) {
                                new GameTile(gameMap, TileBlueprint.getTileFromString(args[2]), p.getLocation());
                            } else if (args.length == 4) {
                                new GameTile(gameMap, TileBlueprint.getTileFromString(args[2]), p.getLocation(), Integer.parseInt(args[3]));
                            }
                        } else if (args[1].equalsIgnoreCase("removetile")) {
                            GameTile.removeTile(gameMap, Integer.parseInt(args[2]));
                        }else if(args[1].equalsIgnoreCase("settilecam")){
                            GameTile.setTileCam(gameMap, Integer.parseInt(args[2]), p.getLocation());
                        }
                    }
                        if (args.length > 3) {
                            if (args[0].equalsIgnoreCase("create")) {
                                GameMap gameMap = new GameMap(args[1], new ArrayList<>(), new ArrayList<>(), p.getLocation(), Integer.parseInt(args[2]),
                                        args[3], new ArrayList<>());
                                gameMap.createFile();
                            }
                        }
                    }
                }
            }
        return false;
    }
}


