package pixelparty.minigame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pixelparty.game.game.Game;
import pixelparty.minigame.object.Minigame;
import pixelparty.minigame.object.MinigameState;

public class PracticeListener implements Listener {

    @EventHandler
    public void onPracticeChoose(PlayerInteractEvent e){
        Minigame minigame = Game.getGame().getMinigame();
        Player p =  e.getPlayer();
        if(minigame == null){
            return;
        }
        if(minigame.getMinigameState().equals(MinigameState.STARTING)){
            if(!minigame.getPracticeChooseMap().containsKey(p.getUniqueId())){
                ItemStack item = p.getInventory().getItemInMainHand();
                if(item.getItemMeta() == null){
                    return;
                }
                if(item.getItemMeta().getDisplayName().contains("Yes")){
                    minigame.getPracticeChooseMap().put(p.getUniqueId(), true);
                    p.sendMessage(ChatColor.WHITE + "You chose " + ChatColor.GREEN + "" + ChatColor.BOLD + "YES" +
                            ChatColor.WHITE + " to practice.");
                    e.setCancelled(true);
                    p.getInventory().clear();
                }else if(item.getItemMeta().getDisplayName().contains("No")){
                    minigame.getPracticeChooseMap().put(p.getUniqueId(), false);
                    p.sendMessage(ChatColor.WHITE + "You chose " + ChatColor.RED + "" + ChatColor.BOLD + "NO" +
                            ChatColor.WHITE + " to practice.");
                    e.setCancelled(true);
                    p.getInventory().clear();
                }
            }
        }
    }
}
