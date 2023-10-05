package com.example.todoapp.controllers;

import com.example.todoapp.dtos.CreateTodoDTO;
import com.example.todoapp.dtos.UpdateTodoDTO;
import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid CreateTodoDTO createTodoDTO) {
        var todo = new Todo();

        BeanUtils.copyProperties(createTodoDTO, todo);
        var newTodo = todoRepository.save(todo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Todo>> getAllTodos() {
        var todos = todoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable(value = "id") UUID id) {
        Optional<Todo> currentTodo = todoRepository.findById(id);

        if (currentTodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(currentTodo.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable(value = "id") UUID id, @RequestBody @Valid UpdateTodoDTO updateTodoDTO) {
        Optional<Todo> currentTodo = todoRepository.findById(id);

        if (currentTodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        currentTodo.get().setDescription(updateTodoDTO.description());
        currentTodo.get().setStatus(updateTodoDTO.status());
        var updatedTodo = todoRepository.save(currentTodo.get());

        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable(value = "id") UUID id) {
        todoRepository.deleteById(id);
    }
}
