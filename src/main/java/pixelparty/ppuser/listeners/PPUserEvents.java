package pixelparty.ppuser.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import pixelparty.PPCore;
import pixelparty.character.object.PlayerCharacter;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.gamescoreboard.PPScoreboardManager;
import pixelparty.ppuser.object.PPUser;

import java.util.HashMap;
import java.util.UUID;

public class PPUserEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        HashMap<UUID, PPUser> ppUsers = PPCore.getCore().getPpUserManager().getPPUsers();
        Player p = e.getPlayer();
        p.setInvisible(false);
        for(Player player: Bukkit.getOnlinePlayers()){
            p.hidePlayer(player);
            player.hidePlayer(p);
            //p.showplayer
        }
        if(!ppUsers.containsKey(p.getUniqueId())){
            PPUser user = new PPUser(p.getUniqueId());
            if(!p.isOp()){
                user.setPlayerStationary();
                Game.getGame().getPpsCoreboardManager().getScoreboardManagerHashMap().get(Game.getGame().getGameState()).setScoreboard(p);
            }else{
                user.setPlayerStationary();
                Game.getGame().getPpsCoreboardManager().getScoreboardManagerHashMap().get(Game.getGame().getGameState()).setScoreboard(p);

            }
        }
        if(Game.getGame().getGameState() == GameState.LOADING){
            p.teleport(Game.getGame().getGameMap().getCamStartLocation());
            if(Game.getGame().getRequiredPlayers() <= Game.getGame().getPlayers().size()){
                if(Game.getGame().getGameStart() == null){
                    Game.getGame().startTimer();
                }
            }
            for(Player player: Bukkit.getOnlinePlayers()){
                Game.getGame().getPpsCoreboardManager().getScoreboardManagerHashMap().get(GameState.LOADING).setScoreboard(player);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerToggleFlightEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        HashMap<UUID, PPUser> ppUsers = PPCore.getCore().getPpUserManager().getPPUsers();
        PPUser ppUser = ppUsers.get(e.getPlayer().getUniqueId());
        if(Game.getGame().getGameState() == GameState.LOADING){
            Game.getGame().getPlayers().remove(ppUser);
            ppUser.getPlayerCharacter().getEntity().getBukkitEntity().remove();
            ppUsers.remove(e.getPlayer().getUniqueId());
            PPScoreboardManager.updateAllScoreboards();
            PlayerCharacter.updateAllEntityStartingPositions();
        }else if(Game.getGame().getGameState() == GameState.STARTING){
            Game.getGame().getPlayers().remove(ppUser);
            ppUser.getPlayerCharacter().getEntity().getBukkitEntity().remove();
            ppUsers.remove(e.getPlayer().getUniqueId());
            PlayerCharacter.updateAllEntityStartingPositions();
            if(Game.getGame().getPlayers().size() < Game.getGame().getRequiredPlayers()){
                Game.getGame().setGameState(GameState.LOADING);
                Game.getGame().getGameStart().setTimeLeft(-1);
                PPScoreboardManager.updateAllScoreboards();
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
//        if(PPUser.fromPlayer(e.getPlayer()).isFrozen()) {
//            if (e.getTo().distance(e.getFrom()) > 0) {
//                Location loc = e.getFrom();
//                loc.setYaw(e.getTo().getYaw());
//                loc.setPitch(e.getTo().getPitch());
//                e.getPlayer().teleport(loc);
//            }
//        }
    }
}
