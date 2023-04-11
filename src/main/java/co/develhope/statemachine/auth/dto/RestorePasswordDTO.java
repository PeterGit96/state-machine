package co.develhope.statemachine.auth.dto;

public class RestorePasswordDTO {

    private String newPassword;
    private String passwordResetCode;

    public RestorePasswordDTO() { }

    public RestorePasswordDTO(String newPassword, String passwordResetCode) {
        this.newPassword = newPassword;
        this.passwordResetCode = passwordResetCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPasswordResetCode() {
        return passwordResetCode;
    }

}
