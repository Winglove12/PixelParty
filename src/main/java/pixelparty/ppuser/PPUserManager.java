package pixelparty.ppuser;

import lombok.Getter;
import mayhem.Manager;
import org.bukkit.Bukkit;
import pixelparty.PPCore;
import pixelparty.ppuser.listeners.PPUserEvents;
import pixelparty.ppuser.object.PPUser;

import java.util.HashMap;
import java.util.UUID;

public class PPUserManager extends Manager {

    @Getter
    HashMap<UUID, PPUser> PPUsers = new HashMap<>();

    public PPUserManager() {
        super("PPUsers");
        Bukkit.getPluginManager().registerEvents(new PPUserEvents(), PPCore.getCore());
    }

    @Override
    public void setup() {

    }

    @Override
    public void onDisable() {

    }
}
