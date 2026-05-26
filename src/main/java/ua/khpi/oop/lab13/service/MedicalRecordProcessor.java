package ua.khpi.oop.lab13.service;

import ua.khpi.oop.lab13.model.MedicalRecord;
import ua.khpi.oop.lab13.model.MedicalReport;
import ua.khpi.oop.lab13.model.RecordStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MedicalRecordProcessor {

    // Регулярний вираз для перевірки формату та вилучення груп [cite: 147, 160]
    private static final Pattern RECORD_PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}) (PAT-\\d{4}) DIAGNOSIS=([a-zA-Z]+) STATUS=(COMPLETED|SCHEDULED|CANCELLED)$"
    );

    public static String normalizeWhitespace(String line) {
        if (line == null) {
            return "";
        }
        // Очищення зайвих пробілів на краях та нормалізація внутрішніх пробілів [cite: 818]
        return line.trim().replaceAll("\\s+", " ");
    }

    public MedicalRecord parseLine(String rawLine) {
        String normalizedLine = normalizeWhitespace(rawLine);
        Matcher matcher = RECORD_PATTERN.matcher(normalizedLine);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid medical record format: " + rawLine);
        }

        LocalDate date = LocalDate.parse(matcher.group(1));
        LocalTime time = LocalTime.parse(matcher.group(2));
        String patientId = matcher.group(3);
        String appointment = matcher.group(4);
        RecordStatus status = RecordStatus.valueOf(matcher.group(5));

        return new MedicalRecord(date, time, patientId, appointment, status);
    }

    public List<MedicalRecord> parseLines(List<String> rawLines) {
        List<MedicalRecord> records = new ArrayList<>();
        for (String rawLine : rawLines) {
            String normalizedLine = normalizeWhitespace(rawLine);
            if (!normalizedLine.isBlank()) {
                records.add(parseLine(normalizedLine));
            }
        }
        return records;
    }

    public MedicalReport summarize(List<MedicalRecord> records) {
        if (records.isEmpty()) {
            return new MedicalReport(0, 0, 0);
        }

        int completed = 0;
        int cancelled = 0;

        for (MedicalRecord record : records) {
            if (record.status() == RecordStatus.COMPLETED) {
                completed++;
            } else if (record.status() == RecordStatus.CANCELLED) {
                cancelled++;
            }
        }
        return new MedicalReport(records.size(), completed, cancelled);
    }

    // Використання StringBuilder для поетапного формування підсумкового звіту [cite: 129]
    public String buildReport(List<MedicalRecord> records) {
        MedicalReport summary = summarize(records);
        StringBuilder report = new StringBuilder();

        report.append("Medical Records Report\n");
        report.append("======================\n");
        report.append("Total processed records: ").append(summary.totalRecords()).append('\n');
        report.append("Completed appointments: ").append(summary.completedVisits()).append('\n');
        report.append("Cancelled appointments: ").append(summary.cancelledVisits()).append("\n\n");
        report.append("Detailed Records:\n");

        for (MedicalRecord record : records) {
            report.append("- ")
                    .append(record.date()).append(" ")
                    .append(record.time()).append(" | ")
                    .append(record.patientId()).append(" | ")
                    .append(record.appointment()).append(" | ")
                    .append(record.status())
                    .append('\n');
        }

        return report.toString();
    }
}