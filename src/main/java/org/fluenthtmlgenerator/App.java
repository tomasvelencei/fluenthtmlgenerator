package org.fluenthtmlgenerator;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        String templateFileName = "template.ftl";
        Map<String, Object> data = Map.of(
                "nameParameter", "Tamás Velencei",
                "repositoryUrl", "https://github.com/tomasvelencei/fluenthtmlgenerator",
                "emailParameter", "tomas.velencei@gmail.com"
        );

        FluentHTMLGenerator builder = new FluentHTMLGenerator();
        builder
                .startDocument()
                .startHead()
                .setTitle("${nameParameter} - Teszt Feladat")
                .endHead()
                .startBody()
                .addHeading("Teszt Feladat", 1)
                .addParagraph("<a href=\"${repositoryUrl}\">Megoldás</a>")
                .addParagraph("A feladat elkészítőjének adatai")
                .startTable()
                .addTableRow("Név", "${nameParameter}")
                .addTableRow("Elérhetőség", "${emailParameter}")
                .endTable()
                .addLink("L&P Solutions", "http://lpsolutions.hu")
                .endBody()
                .endDocument()
                .saveToFile(templateFileName)
                .saveHtmlWithData(templateFileName, "output.html", data)
                .deleteTemplateFile(templateFileName);
    }
}
