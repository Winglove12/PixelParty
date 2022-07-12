package pixelparty.minigame.minigames;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.minigame.object.MinigameName;
import pixelparty.minigame.object.Minigame;
import pixelparty.minigame.object.MinigameState;
import pixelparty.ppuser.object.PPUser;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class LumberjackMinigame extends Minigame {

    private HashMap<PPUser, Integer> blocksBroken = new HashMap<>();
    private HashMap<Material, Material> blockNecessaryTool = new HashMap<>(); // What tool is necessary to break the block

    @Override
    public int maxTime() {
        return 20000;
    }

    @Override
    public MinigameName minigameName() {
        return MinigameName.LUMBERJACK;
    }

    @Override
    public List<Location> spawnLocs() {
        List<Location> locs = new ArrayList<>();
        locs.add(new Location(Bukkit.getWorld("world"), 100, 101 ,92, -90, 0));
        locs.add(new Location(Bukkit.getWorld("world"), 100, 101 ,100, -90, 0));
        return locs;
    }

    @Override
    public Location startLoc() {
        return new Location(Bukkit.getWorld("world"), 100, 100 ,100);
    }

    @Override
    public void customStartGame() {
        for(PPUser ppUser: Game.getGame().getPlayers()){
            blocksBroken.put(ppUser, 0);
            ppUser.getPlayer().getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
        }
        blockNecessaryTool.put(Material.OAK_LOG, Material.WOODEN_AXE);
    }

    @Override
    public void setScoreboard() {
        for(PPUser ppUser: Game.getGame().getPlayers()){
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective obj = board.registerNewObjective(ChatColor.DARK_GREEN + "" +
                    ChatColor.BOLD + "Lumberjack", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score score2 = obj.getScore(ChatColor.WHITE + "Current Order: ");
            score2.setScore(15);
            HashMap<PPUser, Integer> sortedMap = blocksBroken.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> -e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> { throw new AssertionError(); },
                            LinkedHashMap::new
                    ));
            // reverse this somehow
            int n = 1;
            for(PPUser ppUserScore: sortedMap.keySet()){
                Score newScore = obj.getScore(ChatColor.WHITE + "- " + ppUserScore.getName() + " " + sortedMap.get(ppUserScore));
                newScore.setScore(15 - n);
                n++;
            }
            ppUser.getPlayer().setScoreboard(board);
        }
    }

    @Override
    public int teamSize() {
        return 1;
    }

    public void addBlockBroken(Player p){
        PPUser user = PPCore.getCore().getPpUserManager().getPPUsers().get(p.getUniqueId());
        int newAmount = blocksBroken.get(user) + 1;
        blocksBroken.remove(user);
        blocksBroken.put(user, newAmount);
        setScoreboard();
        if(newAmount >= 125){
            if(getMinigameState() == MinigameState.PRACTICE){
                endMinigame();
            }
        }else if(newAmount > 10){
            if(getMinigameState() == MinigameState.GAME){
                endMinigame();
            }
        }
    }
}
