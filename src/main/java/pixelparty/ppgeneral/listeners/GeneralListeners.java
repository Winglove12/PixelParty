package pixelparty.ppgeneral.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GeneralListeners implements Listener {

    @EventHandler
    public void mobSpawn(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) return;
        e.setCancelled(true);
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
}
