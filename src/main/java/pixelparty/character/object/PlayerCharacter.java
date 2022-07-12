package pixelparty.character.object;

import com.ticxo.modelengine.api.model.ActiveModel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import pixelparty.PPCore;
import pixelparty.character.cosmetics.CharacterBaseColor;
import pixelparty.game.game.Game;
import pixelparty.ppuser.object.PPUser;

import java.util.List;

@Getter
@Setter
public class PlayerCharacter {

    private PlayerCharacterEntity entity;
    private CharacterBaseColor characterColor;
    private PPUser ppUser;
    private int currentTile = 0;
    private int destinationTile = -1;

    public PlayerCharacter(PPUser ppUser){
        this.ppUser = ppUser;
        getCharacterBaseColor();

        new BukkitRunnable(){
            public void run(){
               entity = new PlayerCharacterEntity(ppUser, Game.getGame().getGameMap().getStartLocations().get(ppUser.getPositionInOrder())); // Create new player character entity using ppUser
            }
        }.runTaskLater(PPCore.getCore(), 1);
    }

    public void createNewEntity(){
        entity = new PlayerCharacterEntity(ppUser, Game.getGame().getGameMap().getTileMap()
                .get(ppUser.getCurrentTile()).getCornerLocation(ppUser.getPositionInOrder() + 1));
        //need to make sure that current tile is not connected to entity since entity despawns
    }

    public void getCharacterBaseColor(){
        switch(ppUser.getPositionInOrder()){
            case 0:
                this.characterColor = CharacterBaseColor.RED;
                break;
            case 3:
                this.characterColor = CharacterBaseColor.YELLOW;
                break;
            case 2:
                this.characterColor = CharacterBaseColor.GREEN;
                break;
            case 1:
                this.characterColor = CharacterBaseColor.BLUE;
                break;
        }
    }

    public static void updateAllEntityStartingPositions(){
        List<PPUser> ppUsers = Game.getGame().getPlayers();
        for(PPUser ppUser: ppUsers){
            ppUser.getPlayerCharacter().getEntity().getBukkitEntity().teleport(Game.getGame().getGameMap()
                    .getStartLocations().get(ppUser.getPositionInOrder()));
        }
    }

    public void clearEntity(){
        ppUser.getPlayerCharacter().getEntity().getActiveModel().clearModels();
        //ppUser.getPlayerCharacter().getEntity().getActiveModel().getAllActiveModel().clear();
        if(ppUser.getPlayerCharacter().getEntity().getBukkitEntity() != null){
            ppUser.getPlayerCharacter().getEntity().getBukkitEntity().remove();
        }
    }
}
