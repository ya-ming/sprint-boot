package io.javabrains.SpringBootSecurity.UserService;

public enum ChangePasswordResult {
    SUCCESS("Password changed successfully."),
    BAD_CREDENTIAL("Incorrect old password."),
    PASSWORD_MISMATCH("New password and confirm password do not match."),
    OTHER_ERROR("An unexpected error occurred.");

    private final String message;

    // Constructor
    ChangePasswordResult(String message) {
        this.message = message;
    }

    // Getter method
    public String getMessage() {
        return message;
    }
}
