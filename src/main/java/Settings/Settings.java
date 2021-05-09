package Settings;

import java.util.EnumMap;
import java.lang.Integer;

/**
 * Class containing all the static settings necessary to run the game.
 */
public class Settings {
    static private EnumMap<EParam, Integer> mapping;
     
    /**
     * Initializes the default settings.
     */
    static {
        setDefaultSettings();
    }
    
    /**
     * Returns the integer value associated with a Setting.
     * @param eparam the Setting ENUM key
     * @return the setting's value
     */
    public static int get(EParam eparam) {
        return mapping.get(eparam);
    }
    
    /**
     * Sets the current settings to their default values.
     */
    static private void setDefaultSettings() {
        EnumMap<EParam, Integer> newMapping = new EnumMap<>(EParam.class);
    
        newMapping.put(EParam.pacman_speed,            4);
        newMapping.put(EParam.pacman_starting_lives,   3);
    
        newMapping.put(EParam.ghost_speed,             3);
        newMapping.put(EParam.vulnerable_mstime,       15000);
    
        newMapping.put(EParam.large_food_score,        40);
        newMapping.put(EParam.small_food_score,        20);
    
        newMapping.put(EParam.special_food_spawn_odd,  10);
        
        mapping = newMapping;
    }
    
    /**
     * Getter method for the EnumMap.
     * @return the settings EnumMap
     */
    public static EnumMap<EParam, Integer> getMapping(){
        return mapping;
    }
    
}