package com.simpolab.server_main.user_authentication.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Slf4j
@Validated
public class AppUser {

  @Min(0)
  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  @Pattern(regexp = "(?i)manager|elector")
  private String role;

  public AppUser() {
    this.role = "elector";
  }
}
