package pixelparty.game.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pixelparty.PPCore;
import pixelparty.camera.Camera;
import pixelparty.game.game.board.Board;
import pixelparty.game.game.rolloff.RollOff;
import pixelparty.game.game.start.GameStart;
import pixelparty.gamescoreboard.PPScoreboardManager;
import pixelparty.map.object.GameMap;
import pixelparty.minigame.minigames.LumberjackMinigame;
import pixelparty.minigame.object.Minigame;
import pixelparty.ppuser.object.PPUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Game {

    private int currentRound;
    private int rounds;
    private List<PPUser> players;
    private int requiredPlayers;
    private GameMap gameMap;
    private GameState gameState;
    private PPScoreboardManager ppsCoreboardManager;
    private GameStart gameStart;
    private RollOff rollOff;
    private Board board;
    private Minigame minigame = null;
    private boolean cancelInventoryEvents;
    private boolean cancelDropEvents;
    private Camera camera;

    public Game(int rounds, GameMap gameMap){
        this.rounds = rounds;
        this.requiredPlayers = gameMap.getMaxPlayers() / 2;
        this.gameMap = gameMap;
        this.players = new ArrayList<>();
        this.camera = new Camera();
        gameState = GameState.LOADING;
        ppsCoreboardManager = new PPScoreboardManager(this);
        rollOff = new RollOff(this);
    }

    public static Game getGame(){
        return PPCore.getCore().getGameManager().getGame();
    }

    public void startTimer(){
        gameStart = new GameStart(this);
    }

    public void startMinigame(){
        gameState = GameState.MINIGAME;
        //get random minigame
        //minigame = ne wMinigame()
        minigame = new LumberjackMinigame();
        minigame.onLoad();
    }

    public void returnToBoard(){

    }

    public void setScoreboardFromState(){
        for(Player p: Bukkit.getOnlinePlayers()){
            if(ppsCoreboardManager.getScoreboardManagerHashMap().containsKey(gameState)){
                ppsCoreboardManager.getScoreboardManagerHashMap().get(gameState).setScoreboard(p);
            }
        }
    }

}
