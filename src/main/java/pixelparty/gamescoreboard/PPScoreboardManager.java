package pixelparty.gamescoreboard;

import lombok.Getter;
import mayhem.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.gamescoreboard.boards.LoadingScoreboard;
import pixelparty.gamescoreboard.boards.RolloffScoreboard;
import pixelparty.gamescoreboard.boards.StartScoreboard;
import pixelparty.gamescoreboard.object.PPScoreboard;

import java.util.HashMap;

public class PPScoreboardManager extends Manager {

    @Getter
    private HashMap<GameState, PPScoreboard> scoreboardManagerHashMap = new HashMap<>();

    public PPScoreboardManager(Game game) {
        super("Scoreboard");
        scoreboardManagerHashMap.put(GameState.LOADING, new LoadingScoreboard(game));
        scoreboardManagerHashMap.put(GameState.STARTING, new StartScoreboard(game));
        scoreboardManagerHashMap.put(GameState.ROLLTOSTART, new RolloffScoreboard(game));
    }

    public static void updateAllScoreboards(){
        for(Player p: Bukkit.getOnlinePlayers()){
            Game.getGame().getPpsCoreboardManager().getScoreboardManagerHashMap().get(Game.getGame().getGameState()).setScoreboard(p);
        }
    }


    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }
}
