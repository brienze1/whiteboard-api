package org.brienze.whiteboard.controller;

import com.amazonaws.Response;
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
import java.util.UUID;

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
    public ResponseEntity<?> create(@RequestHeader(name = "application-id", required = false) Integer applicationId, @RequestBody State state) {
        System.out.println("Create state called, application id: " + applicationId);

        return statePersistence.getByName(state.getName())
                               .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                               .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(statePersistence.save(state)));
    }

    @PostMapping("/{name}/clear")
    public ResponseEntity<?> clear(@RequestHeader(name = "application-id", required = false) Integer applicationId, @PathVariable("name") String name) {
        System.out.println("Clear state called, application id: " + applicationId);

        shapePersistence.deleteAllByName(name);

        Optional<State> state = statePersistence.getByName(name);
        if (state.isPresent()) {
            state.get().setCleanedAt(LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.OK).body(statePersistence.save(state.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "State not found"));

    }

    @DeleteMapping("/{name}/undo/{lastKey}")
    public ResponseEntity<?> undo(@RequestHeader(name = "application-id", required = false) Integer applicationId,
                                  @PathVariable("name") String name, @PathVariable("lastKey") UUID lastKey) {
        System.out.println("Undo state called, application id: " + applicationId);

        shapePersistence.deleteLast(name, lastKey);

        Optional<State> state = statePersistence.getByName(name);
        if(state.isPresent()) {
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
