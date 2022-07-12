package pixelparty.game.game.board;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.ppuser.object.PPUser;

@Getter
@Setter
public class Board {

    private Game game;
    private boolean allowRolls;
    private int currentPlayer = 0;

    public Board(Game game){
        this.game = game;
        allowRolls = true;
    }

    public void moveToNextPlayer(){
        if(currentPlayer + 1 < game.getPlayers().size()){
            currentPlayer++;
            int currentTile = game.getPlayers().get(currentPlayer).getCurrentTile();
            game.getCamera().moveCamera(Game.getGame().getGameMap().getTileMap().get(currentTile).getCamLoc());
        }else{
            allowRolls = false;
            currentPlayer = 0;
            game.startMinigame();
        }
    }

    public void normalRoll(PPUser ppUser, int spaces){
        ppUser.getPlayerCharacter().setDestinationTile(ppUser.getPlayerCharacter().getCurrentTile() + spaces);
        game.setScoreboardFromState();
    }

    public void returnToBoard(){
        for(PPUser ppUser: game.getPlayers()){
            ppUser.getPlayerCharacter().createNewEntity();
        }
        new BukkitRunnable(){
            public void run(){
                allowRolls = true;
                int currentTile = game.getPlayers().get(0).getCurrentTile();
                game.getCamera().moveCamera(Game.getGame().getGameMap().getTileMap().get(currentTile).getCamLoc());
                game.setGameState(GameState.BOARDPLAY);
            }
        }.runTaskLater(PPCore.getCore(), 1);
    }
}
