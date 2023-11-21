package org.brienze.whiteboard.controller;

import org.brienze.whiteboard.model.Shape;
import org.brienze.whiteboard.model.State;
import org.brienze.whiteboard.persistence.ShapePersistence;
import org.brienze.whiteboard.persistence.StatePersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/whiteboards/{name}/shapes")
public class ShapeController {

    private final StatePersistence statePersistence;
    private final ShapePersistence shapePersistence;

    public ShapeController(StatePersistence statePersistence, ShapePersistence shapePersistence) {
        this.statePersistence = statePersistence;
        this.shapePersistence = shapePersistence;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(name = "application-id", required = false) Integer applicationId, @PathVariable("name") String name, @RequestBody Shape shape) {
        System.out.println("Create shape called, application id: " + applicationId);

        Optional<State> existingState = statePersistence.getByName(name);

        if (existingState.isPresent()) {
            shape.setWhiteboardName(name);

            return ResponseEntity.status(HttpStatus.CREATED).body(shapePersistence.save(shape));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "State not found"));
    }

}
