package pixelparty.ppgeneral.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import pixelparty.game.game.Game;

public class InventoryListeners implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(Game.getGame().isCancelDropEvents()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(Game.getGame().isCancelInventoryEvents()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if(Game.getGame().isCancelInventoryEvents()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEquip(PlayerSwapHandItemsEvent e){
        if(Game.getGame().isCancelInventoryEvents()){
            e.setCancelled(true);
        }
    }
}
