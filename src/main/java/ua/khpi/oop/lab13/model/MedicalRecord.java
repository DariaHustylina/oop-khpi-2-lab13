package ua.khpi.oop.lab13.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public final class MedicalRecord {
    private final LocalDate date;
    private final LocalTime time;
    private final String patientId;
    private final String appointment;
    private final RecordStatus status;

    public MedicalRecord(LocalDate date, LocalTime time, String patientId, String appointment, RecordStatus status) {
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.time = Objects.requireNonNull(time, "Time cannot be null");
        this.patientId = Objects.requireNonNull(patientId, "Patient ID cannot be null");
        this.appointment = Objects.requireNonNull(appointment, "Appointment cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }

    public LocalDate date() { return date; }
    public LocalTime time() { return time; }
    public String patientId() { return patientId; }
    public String appointment() { return appointment; }
    public RecordStatus status() { return status; }
}