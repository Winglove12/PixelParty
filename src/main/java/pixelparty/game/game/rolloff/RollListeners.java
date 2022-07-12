package pixelparty.game.game.rolloff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.ppuser.PPUserManager;
import pixelparty.ppuser.object.PPUser;
import pixelparty.utils.IntegerUtil;
import pixelparty.utils.TitleUtil;

public class RollListeners implements Listener  {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        PPUser ppUser = PPCore.getCore().getPpUserManager().getPPUsers().get(e.getPlayer().getUniqueId());
        if(Game.getGame().getGameState() == GameState.ROLLTOSTART || Game.getGame().getGameState() == GameState.BOARDPLAY) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                int diceRoll = IntegerUtil.randomInt(7, 1); // 7 1
                if(Game.getGame().getGameState() == GameState.ROLLTOSTART){
                    if (Game.getGame().getPlayers().get(Game.getGame().getRollOff().getCurrentPlayer()) == ppUser) {
                        Game.getGame().getRollOff().getPpUserHashMap().put(ppUser, diceRoll);
                        Game.getGame().getRollOff().moveToNextPlayerRoll();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            TitleUtil.sendTitle(p, e.getPlayer().getName() + " rolled a " + diceRoll, "");
                        }
                    }
                }else{
                    if (Game.getGame().getPlayers().get(Game.getGame().getBoard().getCurrentPlayer()) == ppUser &&
                    Game.getGame().getBoard().isAllowRolls()) {
                        Game.getGame().getBoard().normalRoll(ppUser, diceRoll);
                        ppUser.getPlayer().sendMessage(Boolean.toString(Game.getGame().getBoard().isAllowRolls()));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            TitleUtil.sendTitle(p, e.getPlayer().getName() + " rolled a " + diceRoll, "");
                        }
                    }
                }
            }
        }
    }
}
