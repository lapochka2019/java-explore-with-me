package ru.practicum.ewm.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserCreateDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@RequestBody @Valid UserCreateDto userCreateDto){
        log.info("Запрос на создание пользователя с данными: {}", userCreateDto);
        return userService.createUser(userCreateDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        log.info("Запрос на удаление пользователя с id: {}", userId);
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) List<Long> idList,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос на получение списка пользователей с параметрами: " +
                "\n список ID: {}" +
                "\n from: {}" +
                "\n size: {}",
                idList, from, size);
        return userService.getAllUsers(idList, from, size);
    }
}
