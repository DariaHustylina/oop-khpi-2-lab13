package ua.khpi.oop.lab13.demo;

import ua.khpi.oop.lab13.model.MedicalRecord;
import ua.khpi.oop.lab13.service.MedicalRecordProcessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> rawLines = readResourceLines("medical-records.txt");
        MedicalRecordProcessor processor = new MedicalRecordProcessor();

        List<MedicalRecord> records = processor.parseLines(rawLines);
        String report = processor.buildReport(records);

        System.out.println(report);
    }

    private static List<String> readResourceLines(String resourceName) throws IOException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new IOException("Resource not found: " + resourceName);
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }
}
