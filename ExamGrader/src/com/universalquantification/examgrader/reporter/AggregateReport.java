/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universalquantification.examgrader.reporter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.universalquantification.examgrader.models.GradedExamCollection;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * an AggregateReport is a results report for a GradedExamCollection. It is a
 * modified CSV format (non-IEEE conforming) defined by Mr. Dalbey in the piazza
 * thread: https://piazza.com/class/i89evu12g2cfo?cid=9
 *
 * @author Scott Vanderlind
 * @date 2015-05-10
 * @version 2.0
 */
public class AggregateReport
{

    /**
     * The collection of exams to create a report for.
     */
    private GradedExamCollection exams;

    /**
     * The output writer.
     */
    private Writer outfile;

    /**
     * The variables that will be put into the template.
     */
    private HashMap<String, Object> scope;

    /**
     * The reader of the template file to compile.
     */
    private static Reader template;

    /**
     * Instantiates an AggregateReport for a collection of exams
     *
     * @param exams - the GradedExamCollection of graded exams
     * @param writeFile - the file to write the aggregate report to
     * @param template the template to use
     */
    public AggregateReport(GradedExamCollection exams, Writer writeFile,
        Reader template)
    {
        // SET this.exams to exams
        this.exams = exams;
        // SET this.outfile to writeFile
        this.outfile = writeFile;
        // SET this.aggregateReportTemplate to template
        this.template = template;
        // CALL scope.put with this.exams
        this.scope = new HashMap<String, Object>();
        this.scope.put("exams", this.exams);
        this.scope.put("filename",
            this.exams.getAnswerKey().getExamFileName());

        String date = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a").format(
            new Date());
        this.scope.put("creationDate", date);
    }

    /**
     * Writes the report to the file.
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
     *
     * @return the html
     */
    public String renderHTML()
    {
        // INIT a new StringWriter
        StringWriter html = new StringWriter();

        // INIT a new MustacheFactory mf
        MustacheFactory mf = new DefaultMustacheFactory();
        // SET generator to mf.compile with examReportTemplateName
        Mustache mustache = mf.compile(this.template, "Aggregate Report");
        // CALL generator.execute with outfile and scope
        mustache.execute(html, this.scope);
        // CALL writer.flush
        html.flush();

        return html.toString();
    }
}
