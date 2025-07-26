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
    @Size(min = 2, max = 250, message = "Длина name должна быть от 2 до 250 символов!")
    private String name;

    @NotBlank(message = "Поле email не может быть пустым")
    @Size(min = 6, max = 254, message = "Длина email должна быть от 6 до 254 символов!")
    @Email(message = "Email должен быть в подходящем формате!")
    private String email;
}
