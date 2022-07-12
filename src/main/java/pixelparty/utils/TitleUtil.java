package pixelparty.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleUtil {

    public static void sendTitle(Player p, String title, String subtitle){
        p.sendTitle(title, subtitle);
    }

    public static void sendTitleToAll(String title, String subtitle){
        for(Player p: Bukkit.getOnlinePlayers()){
            sendTitle(p, title, subtitle);
        }
    }
}
