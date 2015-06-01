package com.universalquantification.examgrader.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton preferences management object.
 * Adopted from the paradigm explained at http://stackoverflow.com/a/73763
 * @author Scott Vanderlind
 */
public final class PreferencesManager
{
    private static final PreferencesManager INSTANCE = new PreferencesManager();
    
    private Map<String, Object> prefs;
    
    /**
     * Instantiates a new singleton object.
     */
    private PreferencesManager()
    {      ;
        prefs = new HashMap<String, Object>();
        
          // Init our preferences to false. We should do this later, but it will
        // cause a crash if it's NOT done. So here.
        set("show-full-image", false);
        set("show-incorrect-answers", false);
        set("show-correct-answers", false);

    }
    
    /**
     * Gets the shared singleton instance
     * @return PreferencesManager shared object instance
     */
    public static PreferencesManager getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * Set the value of a preference key to a value
     * @param key preference key to set
     * @param value object to set the value of key to 
     */
    public void set(String key, Object value)
    {
        prefs.put(key, value);
    }
    
    /**
     * Return the value for a key
     * @param key preference key
     * @return value
     */
    public Object get(String key)
    {
        if (prefs.containsKey(key))
        {
            return prefs.get(key);
        }
        return null;
    }
    
}
