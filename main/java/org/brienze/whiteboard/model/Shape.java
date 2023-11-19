package org.brienze.whiteboard.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@DynamoDBTable(tableName = "whiteboard.shape")
public class Shape {

    @JsonProperty("id")
    @DynamoDBHashKey(attributeName = "id")
    private UUID id;
    @JsonProperty("whiteboard_name")
    @DynamoDBIndexHashKey(attributeName = "whiteboard_name", globalSecondaryIndexName = "whiteboard-name-idx")
    private String whiteboardName;
    @JsonProperty("type")
    @DynamoDBAttribute(attributeName = "type")
    private String type;
    @JsonProperty("x")
    @DynamoDBAttribute(attributeName = "x")
    private int x;
    @JsonProperty("y")
    @DynamoDBAttribute(attributeName = "y")
    private int y;
    @JsonProperty("temp_x")
    @DynamoDBAttribute(attributeName = "temp_x")
    private int tempX;
    @JsonProperty("temp_y")
    @DynamoDBAttribute(attributeName = "temp_y")
    private int tempY;
    @JsonProperty("x1")
    @DynamoDBAttribute(attributeName = "x1")
    private int x1;
    @JsonProperty("y1")
    @DynamoDBAttribute(attributeName = "y1")
    private int y1;
    @JsonProperty("x2")
    @DynamoDBAttribute(attributeName = "x2")
    private int x2;
    @JsonProperty("y2")
    @DynamoDBAttribute(attributeName = "y2")
    private int y2;
    @JsonProperty("width")
    @DynamoDBAttribute(attributeName = "width")
    private int width;
    @JsonProperty("height")
    @DynamoDBAttribute(attributeName = "height")
    private int height;
    @JsonProperty("text")
    @DynamoDBAttribute(attributeName = "text")
    private String text;

    public UUID getId() {
        return id;
    }

    public String getWhiteboardName() {
        return whiteboardName;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTempX() {
        return tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setWhiteboardName(String whiteboardName) {
        this.whiteboardName = whiteboardName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTempX(int tempX) {
        this.tempX = tempX;
    }

    public void setTempY(int tempY) {
        this.tempY = tempY;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(String text) {
        this.text = text;
    }
}
