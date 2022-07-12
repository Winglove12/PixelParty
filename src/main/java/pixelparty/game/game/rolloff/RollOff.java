package pixelparty.game.game.rolloff;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import pixelparty.game.game.Game;
import pixelparty.game.game.GameState;
import pixelparty.game.game.board.Board;
import pixelparty.ppuser.object.PPUser;

import javax.xml.stream.Location;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RollOff {

    private HashMap<PPUser, Integer> ppUserHashMap = new HashMap<>();
    private int currentPlayer = 0;
    private Game game;

    public RollOff(Game game){
        this.game = game;
    }

    public void moveToNextPlayerRoll(){
        game.setScoreboardFromState();
        if(currentPlayer + 1 < game.getPlayers().size()){
            currentPlayer++;
        }else{
            int i = 0;
            List<PPUser> newPlayerOrder = new ArrayList<>();
            for(int x =0; x < game.getPlayers().size(); x++){
                newPlayerOrder.add(null);
            }
            System.out.println(ChatColor.RED + ppUserHashMap.toString());
            Map<PPUser, Integer> sortedMap =    ppUserHashMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
            System.out.println(ChatColor.BLUE + sortedMap.toString());
            int y = game.getPlayers().size() - 1;
            for(PPUser ppUser: sortedMap.keySet()){
                newPlayerOrder.set(y, ppUser);
                y--;
            }
            System.out.println(ChatColor.DARK_PURPLE + newPlayerOrder.toString());
            game.setPlayers(newPlayerOrder);
            for(PPUser ppUser: game.getPlayers()){
                if(ppUser.getPlayer() != null){
                    ppUser.getPlayer().teleport(game.getGameMap().getTileMap().get(0).getCamLoc());
                }
                ppUser.getPlayerCharacter().getEntity().getBukkitEntity().teleport(game.getGameMap().getTileMap().get(0)
                .getCornerLocation(i + 1));
                ppUser.getPlayerCharacter().getEntity().setCharacterYaw(game.getGameMap().getTileMap().get(0).getCornerLocation(i + 1).getYaw());
                i++;
                game.setGameState(GameState.BOARDPLAY);
                game.setBoard(new Board(game));
            }
        }
    }

    public void startGame(){

    }
}
