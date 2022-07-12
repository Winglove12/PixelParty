package pixelparty.utils;

import net.minecraft.server.v1_16_R3.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil {

    public static List<String> locationToStringList(Location location){
        List<String> list = new ArrayList<>();
        list.add(location.getWorld().getName());
        list.add(Double.toString(location.getX()));
        list.add(Double.toString(location.getY()));
        list.add(Double.toString(location.getZ()));
        list.add(Float.toString(location.getYaw()));
        list.add(Float.toString(location.getPitch()));
        return list;
    }

    public static Location locationFromStringList(List<String> stringList){
        Location loc = new Location(Bukkit.getWorld(stringList.get(0)), Double.parseDouble(stringList.get(1)),
                Double.parseDouble(stringList.get(2)), Double.parseDouble(stringList.get(3)), Float.parseFloat(stringList.get(4)),
                Float.parseFloat(stringList.get(5)));
        return loc;
    }

    public static Location findLocationFromYaw(float yaw, Entity entity){
        Location loc = new Location(entity.getBukkitEntity().getWorld(), entity.getBukkitEntity().getLocation().getX(),
                ((LivingEntity) entity.getBukkitEntity()).getEyeHeight() + entity.getBukkitEntity().getLocation().getY(),
                entity.getBukkitEntity().getLocation().getZ());
        loc.setX((loc.getX() + (-1 * (Math.sin(Math.toRadians(yaw + 1))) * 5)));
        loc.setZ((loc.getZ() + (Math.cos(Math.toRadians(yaw + 1))) * 5));
        return loc;
    }
}
