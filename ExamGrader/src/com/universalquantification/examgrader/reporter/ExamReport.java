/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.universalquantification.examgrader.models.Exam;
import com.universalquantification.examgrader.utils.PreferencesManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * an ExamReport is a per-student report of exam results.
 * Takes an instance of an Exam object and writes an HTML
 * document showing the results.
 *
 * @author Scott Vanderlind
 * @date 2015-05-10
 * @version 2.0
 */
public class ExamReport
{
    /**
     * The exam for which a report will be written.
     */
    private Exam exam;
    
    /**
     * The destination file the report will be written to.
     */
    private Writer outfile;
    
    /**
     * The variables that will be put into the template.
     */
    private HashMap<String, Object> scope;
    
    /**
     * The template reader.
     */
    private Reader template;

    /**
     * Instantiates a new ExamReport given a graded exam and a file
     * to write to
     * @param exam - the graded exam
     * @param writeFile - the output file
     */
    public ExamReport(Exam exam, Writer writeFile, Reader template)
    {
        // SET this.exam to exam
        this.exam = exam;
        // SET this.outfile to writeFile
        this.outfile = writeFile;
        // SET this.template to template
        this.template = template;
        // CALL scope.put with "exam" and this.exam
        this.scope = new HashMap<String, Object>();
        this.scope.put("exam", this.exam);
        
        // We also need to inject the display preferences into the context.
        boolean showCorrect = (boolean)PreferencesManager.getInstance().get("show-correct-answers");
        this.scope.put("showCorrect", showCorrect);
        boolean showInorrect = (boolean)PreferencesManager.getInstance().get("show-incorrect-answers");
        this.scope.put("showIncorrect", showInorrect);
        boolean showFullImage = (boolean)PreferencesManager.getInstance().get("show-full-image");
        this.scope.put("showFullImage", showFullImage);
    }
    
    /**
     * Writes the HTML report to file
     */
    public void writeReport()
    {
        try
        {
            // INIT a new String with the return of CALL renderHTML
            String html = this.renderHTML();
            // CALL write with the html string
            this.outfile.write(html);
            // CALL flush 
            this.outfile.flush();
            // CALL close
            this.outfile.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ExamReport.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    
    /**
     * Renders the HTML and returns it as a string.
     * @return 
     */
    public String renderHTML()
    {
        // INIT a new StringWriter
        StringWriter html = new StringWriter();
       
        // INIT a new MustacheFactory mf
        MustacheFactory mf = new DefaultMustacheFactory();
        // SET generator to mf.compile with examReportTemplateName
        Mustache m = mf.compile(this.template, "Exam Report");
        // CALL generator.execute with outfile and scope
        m.execute(html, this.scope);
        // CALL writer.flush
        html.flush();
        
        return html.toString();
    }
    
}
