package wooteco.subway.maps.line.domain;

import wooteco.subway.common.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String color;
    private Integer extraFare;
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;
    @Embedded
    private LineStations lineStations = new LineStations();

    public Line() {
    }

    public Line(String name, String color, LocalTime startTime, LocalTime endTime, int intervalTime) {
        this(name, color, 0, startTime, endTime, intervalTime);
    }

    public Line(String name, String color, Integer extraFare, LocalTime startTime, LocalTime endTime, int intervalTime) {
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    @PrePersist
    public void prePersist() {
        this.extraFare = this.extraFare == null ? 0 : this.extraFare;
    }

    public void update(Line line) {
        this.name = line.getName();
        this.startTime = line.getStartTime();
        this.endTime = line.getEndTime();
        this.intervalTime = line.getIntervalTime();
        this.color = line.getColor();
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public void removeLineStationById(Long stationId) {
        lineStations.removeByStationId(stationId);
    }

    public List<LineStation> getStationInOrder() {
        return lineStations.getStationsInOrder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public LineStations getLineStations() {
        return lineStations;
    }
}
