package pixelparty.utils;

import java.text.ParseException;

public class IntegerUtil {

    //returns -1 if it is not valid
    public static int getIntFromString(String s){
        try{
            return Integer.parseInt(s);
        }catch(Exception e){
            return -1;
        }
    }

    public static int randomInt(int max, int min){
        return (int) (Math.random() * (max - min) + min);
    }
}
