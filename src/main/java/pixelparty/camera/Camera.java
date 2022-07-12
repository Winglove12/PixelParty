package pixelparty.camera;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pixelparty.character.object.PlayerCharacterEntity;
import pixelparty.game.GameManager;
import pixelparty.game.game.Game;
import pixelparty.map.object.GameMap;
import pixelparty.ppuser.object.PPUser;

@Getter
public class Camera {

    Location currentLocation;

    public void moveCamera(Location location){
        for(Player p: Bukkit.getOnlinePlayers()){
            p.teleport(location);
        }
    }

}
