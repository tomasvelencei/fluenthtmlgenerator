package org.fluenthtmlgenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FluentHTMLGenerator {

    private StringBuilder html;
    private Configuration configuration;

    public FluentHTMLGenerator() {
        this.html = new StringBuilder();
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("UTF-8");
    }

    public FluentHTMLGenerator startDocument() {
        html.append("<!DOCTYPE html>\n<html>\n");
        return this;
    }

    public FluentHTMLGenerator startHead() {
        html.append("<head>\n<meta charset=\"UTF-8\">\n");
        return this;
    }

    public FluentHTMLGenerator setTitle(String title) {
        html.append("<title>").append(title).append("</title>\n");
        return this;
    }

    public FluentHTMLGenerator endHead() {
        html.append("</head>\n");
        return this;
    }

    public FluentHTMLGenerator startBody() {
        html.append("<body>\n");
        return this;
    }

    public FluentHTMLGenerator addHeading(String text, int level) {
        html.append("<h").append(level).append(">").append(text).append("</h").append(level).append(">\n");
        return this;
    }

    public FluentHTMLGenerator addParagraph(String text) {
        html.append("<p>").append(text).append("</p>\n");
        return this;
    }

    public FluentHTMLGenerator addLink(String text, String url) {
        html.append("<a href=\"").append(url).append("\">").append(text).append("</a>\n");
        return this;
    }

    public FluentHTMLGenerator startTable() {
        html.append("<table border=\"1px solid black\">\n");
        return this;
    }

    public FluentHTMLGenerator addTableRow(String cell1, String cell2) {
        html.append("<tr>\n<td>").append(cell1).append("</td>\n<td>").append(cell2).append("</td>\n</tr>\n");
        return this;
    }

    public FluentHTMLGenerator endTable() {
        html.append("</table>\n");
        return this;
    }

    public FluentHTMLGenerator endBody() {
        html.append("</body>\n");
        return this;
    }

    public FluentHTMLGenerator endDocument() {
        html.append("</html>");
        return this;
    }

    public FluentHTMLGenerator saveToFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(html.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FluentHTMLGenerator saveHtmlWithData(String templateFileName, String outputFileName, Map<String, Object> data) {
        try {
            Template template = configuration.getTemplate(templateFileName);
            File outputHtml = new File(outputFileName);
            try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(outputHtml), "UTF-8")) {
                template.process(data, fileWriter);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void deleteTemplateFile(String templateFileName) {
        File templateFile = new File(templateFileName);
        if (templateFile.exists()) {
            templateFile.delete();
        }
    }
}