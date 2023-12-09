package org.brienze.whiteboard.persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.brienze.whiteboard.model.Shape;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class ShapePersistence {

    private final DynamoDBMapper dynamoDBMapper;

    public ShapePersistence(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Shape save(Shape shape) {
        dynamoDBMapper.save(shape);

        return shape;
    }

    public Set<Shape> findAllByName(String name) {
        DynamoDBQueryExpression<Shape>
                queryExpression =
                new DynamoDBQueryExpression<Shape>().withIndexName("whiteboard-name-idx")
                                                    .withKeyConditionExpression("whiteboard_name = :whiteboard_name")
                                                    .withExpressionAttributeValues(Map.of(":whiteboard_name", new AttributeValue().withS(String.valueOf(name))))
                                                    .withConsistentRead(false);

        return new HashSet<>(dynamoDBMapper.query(Shape.class, queryExpression));
    }

    public void deleteAllByName(String name) {
        Set<Shape> shapes = findAllByName(name);

        for (Shape shape : shapes) {
            dynamoDBMapper.delete(shape);
        }
    }

    public void deleteLast(String name, UUID lastKey) {
        Set<Shape> shapes = findAllByName(name);

        for (Shape shape: shapes) {
            if (shape.getId().equals(lastKey)) {
                System.out.println("Deleting " + shape.getType());
                dynamoDBMapper.delete(shape);
                break;
            }
        }
    }
}
