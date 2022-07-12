package pixelparty.game.game.start;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.utils.TitleUtil;

public class GameStart {

    @Getter
    @Setter
    private int timeLeft = 5;
    @Getter
    private Game game;

    public GameStart(Game game){
        game.setGameState(GameState.STARTING);
        startTimerSequence();
        this.game = game;
    }

    public void startTimerSequence(){
        new BukkitRunnable(){
            public void run(){
                if(getTimeLeft() == -1){
                    this.cancel();
                    Game.getGame().setGameStart(null);
                    return;
                }
                else if(getTimeLeft() <= 0){
                    this.cancel();
                    for(Player p: Bukkit.getOnlinePlayers()){
                        TitleUtil.sendTitle(p, "Roll to see who goes first.", "");
                        new BukkitRunnable(){
                            public void run(){
                                game.setGameState(GameState.ROLLTOSTART);
                                TitleUtil.sendTitle(p, Bukkit.getPlayer(Game.getGame().getPlayers().get(0).getUuid()).getName() + " rolls first", "");
                                game.setScoreboardFromState();
                            }
                        }.runTaskLater(PPCore.getCore(), 60);
                    }
                    return;
                }
                game.setScoreboardFromState();
                setTimeLeft(getTimeLeft() - 1);
            }
        }.runTaskTimer(PPCore.getCore(), 20, 20);
    }
}
