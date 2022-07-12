package pixelparty.minigame.object;

import lombok.Getter;
import lombok.Setter;
import mayhem.Core;
import mayhem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.ppuser.object.PPUser;
import pixelparty.utils.TitleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Minigame {

    private int practiceTime = 30;
    private HashMap<UUID, Boolean> practiceChooseMap = new HashMap<>();
    private BukkitTask endGameTimer;
    private MinigameState minigameState;
    public abstract int maxTime(); // In seconds
    public abstract MinigameName minigameName();
    public abstract List<Location> spawnLocs();
    public abstract Location startLoc();
    public abstract void customStartGame();
    public abstract void setScoreboard();
    public abstract int teamSize();

    public void onLoad(){
        minigameState = MinigameState.STARTING;
        Game.getGame().setMinigame(this);
        for(PPUser p: Game.getGame().getPlayers()){
            if(p.getPlayer() != null){
                p.getPlayer().getInventory().clear();
                ItemStack yes = new ItemBuilder(Material.GREEN_CONCRETE).setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Yes").build();
                ItemStack no = new ItemBuilder(Material.RED_CONCRETE).setName(ChatColor.RED + "" + ChatColor.BOLD + "No").build();
                p.getPlayer().getInventory().setItem(0, yes);
                p.getPlayer().getInventory().setItem(1, no);
            }
        }
        TitleUtil.sendTitleToAll("Right click on yes to practice or no for no practice", "");
        new BukkitRunnable(){
            public void run(){
                onChooseToPractice();
            }
        }.runTaskLater(PPCore.getCore(), 200);
    }

    public void onChooseToPractice(){
        boolean practice = false;
        if(getNumOfPractice() > (Game.getGame().getPlayers().size() / 2)){
            practice = true;
        }
        if(practice){
            startGame(practiceTime, true);
            minigameState = MinigameState.PRACTICE;
            TitleUtil.sendTitleToAll("Starting practice", "");
        }else{
            startGame(maxTime(), false);
            minigameState = MinigameState.GAME;
            TitleUtil.sendTitleToAll("Starting minigame", "");
        }
    }

    public void startGame(int timeToEnd, boolean isPractice){
        int i = 0;
        for(PPUser p: Game.getGame().getPlayers()){
            if(p.getPlayer() != null) {
                p.getPlayer().teleport(spawnLocs().get(i));
                p.getPlayerCharacter().clearEntity();
                i++;
            }
        }
//        minigameState = MinigameState.GAME;
        customStartGame();
        setScoreboard();
        for(PPUser ppUser: Game.getGame().getPlayers()){
            ppUser.setPlayerStationary(false);
        };
        endGameTimer = new BukkitRunnable(){
            public void run(){
                if(minigameState == MinigameState.PRACTICE){
                    endPractice();
                }else{
                    endMinigame();
                }
            }
        }.runTaskLater(PPCore.getCore(), timeToEnd);
    }

    public void onManualEnd(){
        if(!endGameTimer.isCancelled()){
            endGameTimer.cancel();
        }
        if(minigameState == MinigameState.PRACTICE){
            endPractice();
        }else{

        }
    }

    public void endPractice(){
        startGame(maxTime(), false);
    }

    public void endMinigame(){
        if(minigameState == MinigameState.PRACTICE){
            endPractice();
            return;
        }
        if(minigameState != MinigameState.GAME){
            return;
        }
        for(PPUser ppUser: Game.getGame().getPlayers()){
            ppUser.setPlayerStationary(true);
            Minigame.resetMinigame();
            Game.getGame().getBoard().returnToBoard();
        }
    }

    public int getNumOfPractice(){
        int sum = 0;
        for(Boolean bool: practiceChooseMap.values()){
            if(bool){
                sum++;
            }
        }
        return sum;
    }

    public static Minigame getCurrentMinigame(){
        if(Game.getGame().getMinigame() != null){
            return Game.getGame().getMinigame();
        }
        return null;
    }

    public static void resetMinigame(){
        Game.getGame().setMinigame(null);
    }
}
