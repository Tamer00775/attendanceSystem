package kz.sdu.project.utils.enums;

public enum RegistrationErrorCodeEnums {
    EMAIL_ERROR_MESSAGE("Email format is incorrect. Example: user123@mail.ru, user123@yandex.ru, user@gmail.com"),
    PASSWORD_POLICY_ERROR("Password policy : (Uppercases, lowercases, digits)");

    private final String ru;
    RegistrationErrorCodeEnums(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return this.ru;
    }
}
