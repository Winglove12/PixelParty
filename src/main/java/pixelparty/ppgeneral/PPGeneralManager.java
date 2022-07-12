package pixelparty.ppgeneral;

import lombok.Getter;
import mayhem.Manager;
import org.bukkit.Bukkit;
import pixelparty.PPCore;
import pixelparty.ppgeneral.listeners.GeneralListeners;

public class PPGeneralManager extends Manager {

    public PPGeneralManager() {
        super("PP General");
        Bukkit.getPluginManager().registerEvents(new GeneralListeners(), PPCore.getCore());
    }

    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }
}
