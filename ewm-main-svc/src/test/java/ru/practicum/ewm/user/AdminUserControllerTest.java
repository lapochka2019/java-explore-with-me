package ru.practicum.ewm.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.controller.AdminUserController;
import ru.practicum.ewm.user.dto.UserCreateDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.user.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminUserController.class)
@DisplayName("Тестирование AdminUserController")
public class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository; // может понадобиться, если используются кастомные методы

    @Test
    void shouldReturnCreated_whenUserIsCreated() throws Exception {
        UserCreateDto dto = new UserCreateDto("User Name", "user@mail.ru");

        User createdUser = new User(1L, "User Name", "user@mail.ru");

        when(userService.createUser(any(UserCreateDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("user@mail.ru"));
    }

    @Test
    void shouldReturnConflict_whenEmailAlreadyExists() throws Exception {
        UserCreateDto dto = new UserCreateDto("User Name", "user@mail.ru");

        when(userService.createUser(any(UserCreateDto.class)))
                .thenThrow(new ConflictException("Пользователь с user@mail.ru уже существует"));

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnBadRequest_whenInvalidEmailFormat() throws Exception {
        UserCreateDto dto = new UserCreateDto("User Name", "user-mail.ru");

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNoContent_whenUserDeleted() throws Exception {
       doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/admin/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenUserDoesNotExistToDelete() throws Exception {
        doThrow(new NotFoundException("Пользователя не существует")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/admin/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOk_whenGetAllUsers() throws Exception {
        List<User> users = List.of(
                new User(1L, "John",  "user1@example.com"),
                new User(2L, "Jane",  "user2@example.com")
        );

        when(userService.getAllUsers(any(), anyInt(), anyInt())).thenReturn(users);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldReturnOkWithFilter_whenGetUsersByIds() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<User> users = List.of(
                new User(1L, "John",  "user1@example.com")
        );

        when(userService.getAllUsers(anyList(), anyInt(), anyInt())).thenReturn(users);

        mockMvc.perform(get("/admin/users").param("idList", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void shouldReturnBadRequest_whenFromIsNegative() throws Exception {
        mockMvc.perform(get("/admin/users").param("from", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenSizeIsZeroOrNegative() throws Exception {
       mockMvc.perform(get("/admin/users").param("size", "0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/admin/users").param("size", "-5"))
                .andExpect(status().isBadRequest());
    }

}
