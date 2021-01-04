package ru.arkaleks.telptracker.model;

public enum Status {
    СОЗДАНА("СОЗДАНА"),
    НАЧАТА("НАЧАТА"),
    ЗАКОНЧЕНА("ЗАКОНЧЕНА"),
    ПРИОСТАНОВЛЕНА("ПРИОСТАНОВЛЕНА"),
    В_РАБОТЕ("В РАБОТЕ"),
    ПРОСРОЧЕНА("ПРОСРОЧЕНА"),
    ОТМЕНЕНА("ОТМЕНЕНА");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
