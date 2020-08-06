package wooteco.subway.maps.line.dto;

import wooteco.subway.maps.line.domain.Line;

import java.time.LocalTime;

public class LineRequest {
    private String name;
    private String color;
    private Integer extraFare;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer intervalTime;

    public LineRequest() {
    }

    public LineRequest(String name, String color, Integer extraFare, LocalTime startTime, LocalTime endTime, Integer intervalTime) {
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getExtraFare() {
        return extraFare;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public Line toLine() {
        return new Line(name, color, extraFare, startTime, endTime, intervalTime);
    }
}
