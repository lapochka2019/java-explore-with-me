package ru.practicum.ewm.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Поле name не может быть пустым")
    @Size(min = 2, message = "Длина name не должна превышать 250 символов!")
    @Size(max = 250, message = "Длина name должна быть более 2 символов!")
    private String name;

    @NotBlank(message = "Поле email не может быть пустым")
    @Size(min = 6, message = "Длина email не должна превышать 250 символов!")
    @Size(max = 254, message = "Длина email должна быть более 6 !")
    @Email(message = "Email должен быть в подходящем формате!")
    private String email;
}
