package pixelparty.character.object;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pixelparty.game.game.Game;
import pixelparty.map.object.GameTile;
import pixelparty.utils.LocationUtil;

import java.util.ArrayList;
import java.util.List;

public class Pathfinder extends PathfinderGoal {
    private final EntityInsentient entity;
    private final float rangeSquared;
    private final float followSpeed;
    private EntityLiving target;
    private PlayerCharacterEntity playerCharacterEntity;

    public Pathfinder(EntityInsentient entity, float range, float followSpeed, PlayerCharacterEntity playerCharacterEntity) {
        this.entity = entity;
        // Square the range for cheaper distance measurements
        this.rangeSquared = range * range;
        this.followSpeed = followSpeed;
        this.playerCharacterEntity = playerCharacterEntity;
    }

    @Override
    public boolean a() {
        return this.b();
    }

    @Override
    public boolean b() {
        // Check, if we have a target to attack
//        EntityLiving target = entity.getGoalTarget();
//        // Target shouldn't be more than n blocks away (24 in this case)
//        // Using squared distance measurement for performance
//        if (target == null || target.dead || target.h(entity) > 48 * 24) {
//            return false;
//        }
//        // Check if the target is a player and in survival mode
//        if (target instanceof EntityPlayer) {
//            EnumGamemode gamemode = ((EntityPlayer) target).playerInteractManager.getGameMode();
//            if (gamemode != EnumGamemode.SURVIVAL && gamemode == EnumGamemode.ADVENTURE) {
//                return false;
//            }
//        }
//        // Criteria met, let's set the target
//        this.target = target;
        return true;
    }

    @Override
    public void e() {
        // Reset the target & animation
//        entity.getBukkitEntity().teleport(location);
        if(playerCharacterEntity.getPpUser().getPlayer() != null && playerCharacterEntity.getPlayerCharacter().getDestinationTile() == -1){
            if(playerCharacterEntity.getCharacterYaw() != -1) {
                if(playerCharacterEntity.getActiveModel().getAllActiveModel().get(playerCharacterEntity.getPlayerCharacter().
                        getCharacterColor().toString().toLowerCase() + "gameplayer").getStates().contains("slow")){
                    playerCharacterEntity.getActiveModel().getAllActiveModel().get(playerCharacterEntity.getPlayerCharacter().
                            getCharacterColor().toString().toLowerCase() + "gameplayer").getStates().remove("slow");
                }
                Location locFromYaw = LocationUtil.findLocationFromYaw(playerCharacterEntity.getCharacterYaw(), entity);
//                if(Bukkit.getPlayer("Winglove") == null){
//                    return;
//                }
//                locFromYaw = Bukkit.getPlayer("Winglove").getLocation();
                entity.getControllerLook().a(locFromYaw.getX(), locFromYaw.getY(), locFromYaw.getZ(), 1f, 1f);
//                System.out.println("Entity Yaw: " + entity.getBukkitEntity().getLocation().getYaw());
//                System.out.println("Model Body Angle: " + playerCharacterEntity.getActiveModel().getBodyAngle());
//                System.out.println("Entity Head Rotation: " + entity.getHeadRotation());
//                System.out.println("Model Head Angle: " + playerCharacterEntity.getActiveModel().getHeadAngle());
            }
        }else if(playerCharacterEntity.getPpUser().getPlayer() != null && playerCharacterEntity.getPlayerCharacter().getDestinationTile() != -1){
            if(playerCharacterEntity.getActiveModel().getAllActiveModel().get(playerCharacterEntity.getPlayerCharacter().
                    getCharacterColor().toString().toLowerCase() + "gameplayer").getStates().size() == 0){
                playerCharacterEntity.getActiveModel().getAllActiveModel().get(playerCharacterEntity.getPlayerCharacter().
                        getCharacterColor().toString().toLowerCase() + "gameplayer").addState("slow", 1, 1,1);
            }
            int destinationTile = playerCharacterEntity.getPlayerCharacter().getCurrentTile() + 1;
            if(destinationTile >= Game.getGame().getGameMap().getTileMap().size()){
                destinationTile = 0;
                int tileDifference = playerCharacterEntity.getPlayerCharacter().getDestinationTile() -
                        playerCharacterEntity.getPlayerCharacter().getCurrentTile();
                if(tileDifference > 0){
                    playerCharacterEntity.getPlayerCharacter().setDestinationTile(tileDifference - 1);
                }
            }
            System.out.println(ChatColor.YELLOW + "Current tile: " + playerCharacterEntity.getPlayerCharacter().getCurrentTile());
            System.out.println(ChatColor.BLUE + "Destination Tile: " + playerCharacterEntity.getPlayerCharacter().getDestinationTile());
            Location tileLoc = Game.getGame().getGameMap().getTileMap()
                    .get(destinationTile).getCornerLocation(Game.getGame().getPlayers()
                            .indexOf(playerCharacterEntity.getPpUser()) + 1);
            Location formattedTileLoc = getTileDifferential(playerCharacterEntity.getBukkitEntity().getLocation(), tileLoc);
            if(destinationTile > playerCharacterEntity.getPlayerCharacter().getDestinationTile()){
                GameTile tile = Game.getGame().getGameMap().getTileMap().get(playerCharacterEntity.getPlayerCharacter().getDestinationTile());
                playerCharacterEntity.getPlayerCharacter().setDestinationTile(-1);
                playerCharacterEntity.setCharacterYaw(tile.getCenterLoc().getYaw());
                Game.getGame().getBoard().moveToNextPlayer();
                return;
            }
            playerCharacterEntity.getNavigation().a(formattedTileLoc.getX(), formattedTileLoc.getY(), formattedTileLoc.getZ(), 1.25f);
//            Game.getGame().getCamera().smoothMoove(playerCharacterEntity.getPpUser(), playerCharacterEntity.getBukkitEntity().getVelocity());
            if(playerCharacterEntity.getBukkitEntity().getLocation().distance(tileLoc) < .4){
                if(destinationTile == playerCharacterEntity.getPlayerCharacter().getDestinationTile()){
                    playerCharacterEntity.getPlayerCharacter().setCurrentTile(destinationTile);
                    playerCharacterEntity.getPlayerCharacter().setDestinationTile(-1);
                    playerCharacterEntity.setCharacterYaw(tileLoc.getYaw());
                    Game.getGame().getBoard().moveToNextPlayer();
                }else{
                    playerCharacterEntity.getPlayerCharacter().setCurrentTile(destinationTile);
                    destinationTile = playerCharacterEntity.getPlayerCharacter().getCurrentTile() + 1;
                    if(destinationTile >= Game.getGame().getGameMap().getTileMap().size()){
                        destinationTile = 0;
                        int tileDifference = playerCharacterEntity.getPlayerCharacter().getDestinationTile() -
                                playerCharacterEntity.getPlayerCharacter().getCurrentTile();
                        if(tileDifference > 0){
                            playerCharacterEntity.getPlayerCharacter().setDestinationTile(tileDifference - 1);
                        }
                    }
                    tileLoc = Game.getGame().getGameMap().getTileMap()
                            .get(destinationTile).getCornerLocation(Game.getGame().getPlayers()
                                    .indexOf(playerCharacterEntity.getPpUser()) + 1);
                    formattedTileLoc = getTileDifferential(playerCharacterEntity.getBukkitEntity().getLocation(), tileLoc);
                    playerCharacterEntity.getNavigation().a(formattedTileLoc.getX(), formattedTileLoc.getY(), formattedTileLoc.getZ(), 1.25f);
                }
            }
        }
//        if (playerCharacterEntity.getPlayerCharacter().getCurrentTile() == -1 || playerCharacterEntity.getPlayerCharacter().getCurrentTile() ==
//        playerCharacterEntity.getPlayerCharacter().getDestinationTile()) {
//            return;
//        }
//        if(!(target.getBukkitEntity().getType().equals(EntityType.PLAYER))){
//            return;
    }

    public Location getTileDifferential(Location currentLoc, Location destinationTile){
        Location destinationLoc = destinationTile.clone();
        double xDifferential = destinationLoc.getX() - currentLoc.getX();
        double zDifferential = destinationLoc.getZ() - currentLoc.getZ();
        double xAbs = Math.abs(xDifferential);
        double zAbs = Math.abs(zDifferential);
        if(xAbs > zAbs){
            if(xDifferential < 0){
                destinationLoc.setX(destinationLoc.getX() - 1);
            }else{
                destinationLoc.setX(destinationLoc.getX() + 1);
            }
        }else if(xAbs < zAbs){
            if(zDifferential < 0){
                destinationLoc.setZ(destinationLoc.getZ() - 1);
            }else{
                destinationLoc.setZ(destinationLoc.getZ() + 1);
            }
        }else{
            if(zDifferential < 0 && xDifferential < 0){
                destinationLoc.setX(destinationLoc.getX() - .5);
                destinationLoc.setZ(destinationLoc.getZ() - .5);
            }else if(zDifferential > 0 && xDifferential < 0){
                destinationLoc.setX(destinationLoc.getX() - .5);
                destinationLoc.setZ(destinationLoc.getZ() + .5);
            }else if(zDifferential > 0 && xDifferential > 0){
                destinationLoc.setX(destinationLoc.getX() + .5);
                destinationLoc.setZ(destinationLoc.getZ() + .5);
            }else if(zDifferential < 0 && xDifferential > 0) {
                destinationLoc.setX(destinationLoc.getX() + .5);
                destinationLoc.setZ(destinationLoc.getZ() - .5);
            }
        }
        return destinationLoc;
    }


}
