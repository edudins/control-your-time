package lv.dudins.ControlYourTime.literals;

public enum MessageTemplate {
    UPDATE("[UPDATE] : "),
    ERROR("[ERROR] : "),
    SUCCESS("[SUCCESS] : ");

    private String template;

    MessageTemplate(String type) {
        this.template = type;
    }

    public String build() {
        return template;
    }
}
