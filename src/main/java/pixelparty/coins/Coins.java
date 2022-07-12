package pixelparty.coins;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coins {

    private int currentCoins = 0;
    private int maxCoins = 0;

    public int removeCoins(int amount){
        if(currentCoins - amount < 0){
            currentCoins = 0;
            return currentCoins;
        }else{
            currentCoins -= amount;
            return amount;
        }
    }

    public void addCoins(int amount){
        currentCoins += amount;
        maxCoins += amount;
    }
}
