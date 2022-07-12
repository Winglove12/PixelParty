package pixelparty.gamescoreboard.boards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pixelparty.game.game.Game;
import pixelparty.gamescoreboard.object.PPScoreboard;

public class StartScoreboard extends PPScoreboard {

    public StartScoreboard(Game game) {
        super(game);
    }

    @Override
    public void setScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective(ChatColor.translateAlternateColorCodes('&', getGame().getGameMap().getDisplayName()), "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.WHITE + "Time left: " + Game.getGame().getGameStart().getTimeLeft() + "s");
        score.setScore(15);
        Score score2 = obj.getScore(ChatColor.WHITE + "Current Players: " + Game.getGame().getPlayers().size());
        score2.setScore(14);
        Score score3 = obj.getScore(ChatColor.WHITE + "Required Players: " + getGame().getRequiredPlayers());
        score3.setScore(13);
        p.setScoreboard(board);
    }

    @Override
    public void updateScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective(getGame().getGameMap().getDisplayName(), "loadingscoreboard");
        Score score = obj.getScore(ChatColor.WHITE + "STATE: LOADING");
        score.setScore(15);
        Score score2 = obj.getScore(ChatColor.WHITE + "Current Players: " + Bukkit.getServer().getOnlinePlayers().size());
        score2.setScore(14);
        Score score3 = obj.getScore(ChatColor.WHITE + "Required Players: " + getGame().getRequiredPlayers());
        score3.setScore(13);
    }
}
