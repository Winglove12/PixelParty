package pixelparty.gamescoreboard.boards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pixelparty.game.game.Game;
import pixelparty.gamescoreboard.object.PPScoreboard;
import pixelparty.ppuser.object.PPUser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RolloffScoreboard extends PPScoreboard {
    public RolloffScoreboard(Game game) {
        super(game);
    }

    @Override
    public void setScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Rolloff", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        PPUser currentUser = Game.getGame().getPlayers().get(Game.getGame().getRollOff().getCurrentPlayer());
        Score score = obj.getScore(ChatColor.WHITE + "Current Player: " + currentUser.getPlayer().getName());
        score.setScore(15);
        Score score2 = obj.getScore(ChatColor.WHITE + "Current Order: ");
        score2.setScore(13);
        Map<PPUser, Integer> sortedMap = Game.getGame().getRollOff().getPpUserHashMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        int n = 1;
        for(PPUser ppUser: sortedMap.keySet()){
            Score newScore = obj.getScore(ChatColor.WHITE + "- " + ppUser.getName() + " " + sortedMap.get(ppUser));
            newScore.setScore(13 - n);
            n++;
        }
        p.setScoreboard(board);
    }

    @Override
    public void updateScoreboard() {

    }
}
