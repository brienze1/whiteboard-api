package org.brienze.whiteboard.controller;

import org.brienze.whiteboard.model.Shape;
import org.brienze.whiteboard.model.State;
import org.brienze.whiteboard.persistence.ShapePersistence;
import org.brienze.whiteboard.persistence.StatePersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/whiteboards")
public class StateController {

    private final StatePersistence statePersistence;
    private final ShapePersistence shapePersistence;

    public StateController(StatePersistence statePersistence, ShapePersistence shapePersistence) {
        this.statePersistence = statePersistence;
        this.shapePersistence = shapePersistence;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody State state) {
        return statePersistence.getByName(state.getName())
                               .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                               .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(statePersistence.save(state)));
    }

    @PostMapping("/{name}/clear")
    public ResponseEntity<?> clear(@PathVariable("name") String name) {
        shapePersistence.deleteAllByName(name);

        Optional<State> state = statePersistence.getByName(name);
        if (state.isPresent()) {
            state.get().setCleanedAt(LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.OK).body(statePersistence.save(state.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "State not found"));

    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        Optional<State> state = statePersistence.getByName(name);

        if (state.isPresent()) {
            Set<Shape> shapes = shapePersistence.findAllByName(name);
            state.get().setShapes(shapes);

            return ResponseEntity.status(HttpStatus.OK).body(state.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "State not found"));
    }

}
