package org.fluenthtmlgenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


class FluentHTMLGeneratorTest {

    private FluentHTMLGenerator builder;
    private static final String TEMPLATE_FILE = "template.ftl";
    private static final String OUTPUT_FILE = "output.html";

    @BeforeEach
    public void setUp() {
        builder = new FluentHTMLGenerator();
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
                .saveToFile(TEMPLATE_FILE);
    }

    @AfterEach
    public void tearDown() {
        deleteFile(TEMPLATE_FILE);
        deleteFile(OUTPUT_FILE);
    }

    @Test
    void testTemplate() {
        assertTrueFileExists(TEMPLATE_FILE);
    }

    @Test
    void testGeneratedHtmlWithData() throws IOException {
        Map<String, Object> data = Map.of(
                "nameParameter", "John Doe",
                "repositoryUrl", "http://example.com",
                "emailParameter", "john.doe@example.com"
        );

        builder.saveHtmlWithData(TEMPLATE_FILE, OUTPUT_FILE, data);
        assertTrueFileExists(OUTPUT_FILE);

        String htmlContent = Files.readString(Path.of(OUTPUT_FILE));

        assertTrue(htmlContent.contains("A feladat elkészítőjének adatai"));
        assertTrue(htmlContent.contains("John Doe"));
        assertTrue(htmlContent.contains("http://example.com"));
        assertTrue(htmlContent.contains("john.doe@example.com"));
    }

    private void assertTrueFileExists(String fileName) {
        assertTrue(Files.exists(Path.of(fileName)), "File '" + fileName + "' does not exist.");
    }

    private void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
