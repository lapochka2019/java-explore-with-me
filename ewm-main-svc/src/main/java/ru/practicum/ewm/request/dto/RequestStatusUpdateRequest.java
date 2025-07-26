package ru.practicum.ewm.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.utils.RequestStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatusUpdateRequest {
    @NotEmpty(message = "Ваш список запросов пуст")
    private List<Long> requestIds;

    @NotBlank(message = "Статус запросов не указан")
    private RequestStatus status;
}
