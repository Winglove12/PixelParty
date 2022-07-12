package pixelparty.character.object;

import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.nametag.NametagPart;
import lombok.Getter;
import lombok.Setter;
import mayhem.Core;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.character.models.ModelUtils;
import pixelparty.game.game.Game;
import pixelparty.ppuser.object.PPUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerCharacterEntity extends EntityZombie {
    ;
    private PPUser ppUser;
    private float characterYaw;
    private ModeledEntity activeModel;
    private PlayerCharacter playerCharacter;

    public PlayerCharacterEntity(PPUser user, Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle()); // Super the EntityPig Class
        //this.setPositionRotation(location.getX(), location.getY(), location.getZ(), 90, location.getPitch()); // Sets the location of the CustomPig when we spawn it
        //this.setHeadRotation(location.getYaw());
//        this.setHealth(20.0f); // Sets Health
//        this.setCustomName(new ChatComponentText( ChatColor.getByChar(user.getPlayerCharacter().getCharacterColor().toString()) + user.getPlayer().getName())); // Sets custom name.
//        this.setCustomNameVisible(true); // Makes the name visible to the player in-game
        this.characterYaw = location.getYaw();
        this.ppUser = user;
        this.playerCharacter = ppUser.getPlayerCharacter();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle(); // Creates and NMS world
//        //this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(this); // Adds the entity to the world
        this.getBukkitEntity().setCustomName("This is a test");
        this.getBukkitEntity().setCustomNameVisible(true);
        this.setCustomNameVisible(true);
//        location.setYaw(0);
//        location.setPitch(0);
        //this.getBukkitEntity().teleport(location);
        //this.getBukkitEntity().getLocation().setYaw(location.getYaw());
        this.getBukkitEntity().teleport(location);
        this.setInvulnerable(true);
        String modelName = playerCharacter.getCharacterColor().toString().toLowerCase() + "gameplayer";
        activeModel = ModelUtils.setMobToModel(this.getBukkitEntity(), modelName);
        activeModel.getNametagHandler().setCustomName("nametag", "test");
        activeModel.getNametagHandler().setCustomNameVisibility("nametag", true);
//        activeModel.addState("dicehit", 1, 1,1);
//        new BukkitRunnable(){
//            public void run(){
//            }
//        }.runTaskLater(PPCore.getCore(), 10);
    }

//    public PlayerCharacterEntity(PPUser user, Location location) {
//        super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle()); // Super the EntityPig Class
//        //this.setPositionRotation(location.getX(), location.getY(), location.getZ(), 90, location.getPitch()); // Sets the location of the CustomPig when we spawn it
//        //this.setHeadRotation(location.getYaw());
////        this.setHealth(20.0f); // Sets Health
////        this.setCustomName(new ChatComponentText( ChatColor.getByChar(user.getPlayerCharacter().getCharacterColor().toString()) + user.getPlayer().getName())); // Sets custom name.
////        this.setCustomNameVisible(true); // Makes the name visible to the player in-game
//        this.characterYaw = location.getYaw();
//        this.ppUser = user;
//        WorldServer world = ((CraftWorld) location.getWorld()).getHandle(); // Creates and NMS world
////        //this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
//        world.addEntity(this); // Adds the entity to the world
//        this.getBukkitEntity().setCustomName("This is a test");
//        this.getBukkitEntity().setCustomNameVisible(true);
////        location.setYaw(0);
////        location.setPitch(0);
//        //this.getBukkitEntity().teleport(location);
//        //this.getBukkitEntity().getLocation().setYaw(location.getYaw());
//        this.getBukkitEntity().teleport(location);
//        this.setInvulnerable(true);
//        activeModel = ModelUtils.setMobToModel(this.getBukkitEntity(), "redgameplayer");
//        activeModel.setClamp(0);
////        activeModel.addState("dicehit", 1, 1,1);
////        new BukkitRunnable(){
////            public void run(){
////            }
////        }.runTaskLater(PPCore.getCore(), 10);
//    }

    @Override// <- not necessary but I like putting it here
    public void initPathfinder() {
        this.goalSelector.a(1, new Pathfinder(this, 120, 1F, this));
    }

    public void setPathfinder(int level){

    }

    public void spawnEntity(Location loc){

}

    public void teleportEntity(Location loc){
        this.pitch = loc.getPitch();
        this.lastPitch = loc.getPitch();
        this.yaw = loc.getYaw();;
        this.lastYaw = loc.getYaw();;
        this.aR = loc.getYaw();;
        this.aO = (int) loc.getYaw();
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), this.yaw, this.pitch);
    }


}
