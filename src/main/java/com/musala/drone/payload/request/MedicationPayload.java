package com.musala.drone.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MedicationPayload {
    @NotNull(message = "Drone Model is required")
    @Pattern(regexp = "^[A-Z]+(?:_[A-Z]+)*$", message = "Please only enter upper case letters, underscore and numbers")
    String name;

    @NotNull(message = "Weight is required")
    String weight;

    @NotNull(message = "Code is required")
    String code;

    @NotNull(message = "Image is required")
    MultipartFile[] image;
}
