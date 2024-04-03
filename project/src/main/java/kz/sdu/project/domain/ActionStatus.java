package kz.sdu.project.domain;

public enum ActionStatus {
    ABSENT_STATUS("ABSENT"),
    PRESENT_STATUS("PRESENT"),
    WITH_REASON_STATUS("WITH_REASON"),
    MANUALLY("MANUALLY"),
    CARD("CARD");
    private final String status;
    ActionStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return this.status;
    }
}