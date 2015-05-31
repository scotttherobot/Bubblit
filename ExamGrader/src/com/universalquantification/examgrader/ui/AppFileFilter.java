package com.universalquantification.examgrader.ui;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter with specifications based off constructor arguments.
 * 
 * @author CY
 */
public class AppFileFilter extends FileFilter {
    private final String[] extensions;
    private final String desc;
    
    public AppFileFilter (final String desc, final String[] extensions) {
        super();
        
        this.desc = desc;
        this.extensions = extensions;
    }
    
    /**
     * Takes in a file f and determines whether or not to accept it based on its
     * format
     * 
     * @param f The file to check.
     * @return A boolean representing whether or not it was accepted.
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        int pos = f.getName().lastIndexOf('.');
        
        if (pos == -1) {
            return false;
        } else {
            String extension = f.getName().substring(pos + 1);
            
            for (String ext : extensions) {
                if (extension.equalsIgnoreCase(ext)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * Returns a description of the type of format accepted.
     * 
     * @return A string description. 
     */
    @Override
    public String getDescription() {
        return desc;
    }
}
