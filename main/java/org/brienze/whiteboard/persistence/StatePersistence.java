package org.brienze.whiteboard.persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.brienze.whiteboard.model.State;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatePersistence {

    private final DynamoDBMapper dynamoDBMapper;

    public StatePersistence(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public State save(State state) {
        dynamoDBMapper.save(state);

        return state;
    }

    public Optional<State> getByName(String name) {
        try {
            return Optional.of(dynamoDBMapper.load(State.class, name));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
