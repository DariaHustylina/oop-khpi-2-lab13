package ua.khpi.oop.lab13.service;

import org.junit.jupiter.api.Test;
import ua.khpi.oop.lab13.model.MedicalRecord;
import ua.khpi.oop.lab13.model.MedicalReport;
import ua.khpi.oop.lab13.model.RecordStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordProcessorTest {
    private final MedicalRecordProcessor processor = new MedicalRecordProcessor();

    @Test
    void normalizesWhitespace() {
        String normalized = MedicalRecordProcessor.normalizeWhitespace(
                "  2026-10-12   09:30   PAT-1002   DIAGNOSIS=Surgeon  STATUS=SCHEDULED  "
        );
        assertEquals("2026-10-12 09:30 PAT-1002 DIAGNOSIS=Surgeon STATUS=SCHEDULED", normalized);
    }

    @Test
    void parsesValidRecordLine() {
        MedicalRecord record = processor.parseLine(
                "2026-10-12 09:00 PAT-1001 DIAGNOSIS=Therapist STATUS=COMPLETED"
        );
        assertEquals("PAT-1001", record.patientId());
        assertEquals("Therapist", record.appointment());
        assertEquals(RecordStatus.COMPLETED, record.status());
    }

    @Test
    void rejectsInvalidRecordFormat() {
        assertThrows(
                IllegalArgumentException.class,
                () -> processor.parseLine("2026-10-12 09:00 PAT-1 DIAGNOSIS=Therapist STATUS=DONE")
        );
    }

    @Test
    void buildsTextReport() {
        List<MedicalRecord> records = processor.parseLines(List.of(
                "2026-10-12 09:00 PAT-1001 DIAGNOSIS=Therapist STATUS=COMPLETED"
        ));
        String report = processor.buildReport(records);

        assertTrue(report.contains("Total processed records: 1"));
        assertTrue(report.contains("PAT-1001"));
    }
}