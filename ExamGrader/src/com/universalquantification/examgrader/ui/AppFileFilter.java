package com.universalquantification.examgrader.ui;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter with specifications based off constructor arguments.
 *
 * @author CY
 */
public class AppFileFilter extends FileFilter
{
    private final String[] extensions;
    private final String desc;

    /**
     * Create an new file filter give the description and the allowed extensions
     * @param desc the description of the file to choose
     * @param extensions the allowed extension
     */
    public AppFileFilter(final String desc, final String[] extensions)
    {
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
    public boolean accept(File f)
    {
        // make sure this is a directory
        if (f.isDirectory())
        {
            return true;
        }

        int pos = f.getName().lastIndexOf('.');

        // make sure the file has a dot.
        if (pos == -1)
        {
            return false;
        }
        else
        {
            String extension = f.getName().substring(pos + 1);

            // check that this file has an allowed extension
            for (String ext : extensions)
            {
                // check if this extension is allowed
                if (extension.equalsIgnoreCase(ext))
                {
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
    public String getDescription()
    {
        return desc;
    }
}
