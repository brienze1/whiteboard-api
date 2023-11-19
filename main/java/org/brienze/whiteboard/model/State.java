package org.brienze.whiteboard.model;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.brienze.whiteboard.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamoDBTable(tableName = "whiteboard.state")
public class State {

    @JsonProperty("name")
    @DynamoDBHashKey(attributeName = "name")
    private String name;
    @JsonProperty("cleaned_at")
    @DynamoDBAttribute(attributeName = "cleaned_at")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime cleanedAt;
    @JsonProperty("shapes")
    @DynamoDBIgnore
    private Set<Shape> shapes = new HashSet<>();

    public String getName() {
        return name;
    }

    public LocalDateTime getCleanedAt() {
        return cleanedAt;
    }

    public Set<Shape> getShapes() {
        return shapes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCleanedAt(LocalDateTime cleanedAt) {
        this.cleanedAt = cleanedAt;
    }

    public void setShapes(Set<Shape> shapes) {
        this.shapes = shapes;
    }
}
