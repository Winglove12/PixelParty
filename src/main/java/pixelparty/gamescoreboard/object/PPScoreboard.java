package pixelparty.gamescoreboard.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import pixelparty.game.game.Game;

public abstract class PPScoreboard {

    @Getter
    @Setter
    private Scoreboard scoreboard;
    @Getter
    @Setter
    private Game game;

    public PPScoreboard(Game game){
        this.game = game;
    }

    public abstract void setScoreboard(Player p);

    public abstract void updateScoreboard();
}
