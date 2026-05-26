package ua.khpi.oop.lab13.model;

public final class MedicalReport {
    private final int totalRecords;
    private final int completedVisits;
    private final int cancelledVisits;

    public MedicalReport(int totalRecords, int completedVisits, int cancelledVisits) {
        this.totalRecords = totalRecords;
        this.completedVisits = completedVisits;
        this.cancelledVisits = cancelledVisits;
    }

    public int totalRecords() { return totalRecords; }
    public int completedVisits() { return completedVisits; }
    public int cancelledVisits() { return cancelledVisits; }
}