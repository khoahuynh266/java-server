package com.example.javaserver.payload.request;



import java.util.Set;

        import javax.validation.constraints.*;

public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @NotBlank
    @Size(max = 50)
    @Email
    private String fullname;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    private Set<String> role;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
