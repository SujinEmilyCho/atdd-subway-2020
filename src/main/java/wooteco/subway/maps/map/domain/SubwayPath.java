package wooteco.subway.maps.map.domain;

import com.google.common.collect.Lists;
import wooteco.subway.maps.line.domain.Line;

import java.util.List;
import java.util.stream.Collectors;

public class SubwayPath {
    public static final int FIRST_ZONE_FARE = 1250;
    public static final int MAX_EXTRA_FARE_FROM_SECOND_ZONE = 800;
    public static final int FIRST_ZONE_BOUNDARY = 10;
    public static final int SECOND_ZONE_BOUNDARY = 50;
    public static final double SECOND_ZONE_ASSESSMENT_UNIT = 5.0;
    public static final double THIRD_ZONE_ASSESSMENT_UNIT = 8.0;
    public static final int EXTRA_FARE_RATE = 100;

    private List<LineStationEdge> lineStationEdges;
    private List<Line> lines;

    public SubwayPath(List<LineStationEdge> lineStationEdges, List<Line> lines) {
        this.lineStationEdges = lineStationEdges;
        this.lines = lines;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare() {
        int totalDistance = calculateDistance();
        return FIRST_ZONE_FARE + calculateExtraFare(totalDistance);
    }

    private int calculateExtraFare(int totalDistance) {
        int extraFare = 0;

        if (FIRST_ZONE_BOUNDARY < totalDistance && totalDistance <= SECOND_ZONE_BOUNDARY) {
            extraFare = calculateSecondZoneFare(totalDistance);
        } else if (SECOND_ZONE_BOUNDARY < totalDistance) {
            extraFare = calculateThirdZoneFare(totalDistance);
        }

        extraFare += calculateLineFare();

        return extraFare;
    }

    private int calculateSecondZoneFare(int totalDistance) {
        double extraDistanceSection = Math.ceil((totalDistance - FIRST_ZONE_BOUNDARY) / SECOND_ZONE_ASSESSMENT_UNIT);
        return (int) (extraDistanceSection * EXTRA_FARE_RATE);
    }

    private int calculateThirdZoneFare(int totalDistance) {
        double extraDistanceSection = Math.ceil((totalDistance - SECOND_ZONE_BOUNDARY) / THIRD_ZONE_ASSESSMENT_UNIT);
        return MAX_EXTRA_FARE_FROM_SECOND_ZONE + (int) (extraDistanceSection * EXTRA_FARE_RATE);
    }

    private int calculateLineFare() {
        return lineStationEdges.stream()
                .map(LineStationEdge::getLineId)
                .flatMap(it -> lines.stream().filter(line -> line.getId().equals(it)))
                .mapToInt(Line::getExtraFare)
                .max().orElse(0);
    }
}
