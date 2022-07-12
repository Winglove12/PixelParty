package pixelparty.ppuser.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pixelparty.PPCore;
import pixelparty.character.object.PlayerCharacter;
import pixelparty.coins.Coins;
import pixelparty.game.game.Game;

import java.util.UUID;


@Getter
@Setter
public class PPUser {

    private UUID uuid;
    private String name;
    private Coins coins;
    private PlayerCharacter playerCharacter;
    private boolean frozen;
    //crowns

    public PPUser(UUID uuid){
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(uuid).getName();
        this.coins = new Coins();
        this.frozen = false;
        PPCore.getCore().getPpUserManager().getPPUsers().put(uuid, this);
        Game.getGame().getPlayers().add(this);
        playerCharacter = new PlayerCharacter(this);
    }

    public int getPositionInOrder(){
        return Game.getGame().getPlayers().indexOf(this);
    }

    public static PPUser fromPlayer(Player p){
        return PPCore.getCore().getPpUserManager().getPPUsers().get(p.getUniqueId());
    }

    public Player getPlayer(){
        if(Bukkit.getPlayer(uuid) != null){
            return Bukkit.getPlayer(uuid);
        }else{
            return null;
        }
    }

    public void setPlayerStationary(){
        if(getPlayer() == null){
            return;
        }
        if(frozen == false){
            Player p = getPlayer();
//            p.setFlySpeed(0);
//            p.setWalkSpeed(0);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setInvisible(true);
            frozen = true;
        }else{
            Player p = getPlayer();
            p.setFlySpeed(0.2f);
            p.setWalkSpeed(0.2f);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setInvisible(false);
            frozen = false;
        }
    }

    public void setPlayerStationary(boolean shouldBeFrozen){
        if(getPlayer() == null){
            return;
        }
        if(shouldBeFrozen){
            Player p = getPlayer();
//            p.setFlySpeed(0);
//            p.setWalkSpeed(0);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setInvisible(true);
            frozen = true;
        }else{
            Player p = getPlayer();
            p.setFlySpeed(0.2f);
            p.setWalkSpeed(0.2f);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setInvisible(false);
            frozen = false;
        }
    }

    public int getCurrentTile(){
        return playerCharacter.getCurrentTile();
    }

    public void setCurrentTile(int newTile){
        playerCharacter.setCurrentTile(newTile);
    }
}
