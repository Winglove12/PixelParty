package pixelparty.minigame.minigames;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.minigame.object.Minigame;
import pixelparty.minigame.object.MinigameName;

public class LumberjackListener implements Listener {
    @EventHandler
    public void onBlockBreak(PlayerInteractEvent e){
        if(e.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }
        if(Minigame.getCurrentMinigame() == null){
            return;
        }
        if(Minigame.getCurrentMinigame().minigameName() != MinigameName.LUMBERJACK){
            return;
        }
        LumberjackMinigame minigame = (LumberjackMinigame) Minigame.getCurrentMinigame();
        minigame.addBlockBroken(e.getPlayer());
        Block b = e.getClickedBlock();
        b.breakNaturally();
        new BukkitRunnable(){
            public void run(){
                b.setType(Material.OAK_LOG);
            }
        }.runTaskLater(PPCore.getCore(), 2);
    }
}
