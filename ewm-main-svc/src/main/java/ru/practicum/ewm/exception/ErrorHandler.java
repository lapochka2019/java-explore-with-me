package ru.practicum.ewm.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return new ErrorResponse(
                "NOT_FOUND",
                "Запрашиваемый объект не найден",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException ex) {
        return new ErrorResponse(
                "CONFLICT",
                "Такой объект уже существует",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        return new ErrorResponse(
                "BAD_REQUEST",
                "Ошибка валидации: переданы некорректные данные",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenErrors(final ForbiddenException ex) {
        return new ErrorResponse(
                "FORBIDDEN",
                "У пользователя нет необходимых прав доступа к ресурсу",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleIncorrectRequestErrors(final IncorrectRequestException ex) {
        return new ErrorResponse(
                "BAD_REQUEST",
                "Некорректный запрос",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handlerValidationException(ValidationException ex) {
        return new ErrorResponse(
                "BAD_REQUEST",
                "Некорректные данные",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponse(
                "BAD_REQUEST",
                "Некорректные данные",
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
