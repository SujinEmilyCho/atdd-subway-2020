package wooteco.subway.maps.map.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SubwayPathTest {

    @DisplayName("이동 거리가 10km 이내인 경우 운임 요금은 기본 요금인 1250원 이다.")
    @Test
    void calculateFare_within_10km() {
        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN");

        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, 2, 2);
        LineStation 강남_양재 = new LineStation(3L, 2L, 2, 1);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));
        lineStationEdges.add(new LineStationEdge(강남_양재, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges, Arrays.asList(line1));

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250);
    }

    @DisplayName("이동 거리가 10km - 50km 이내인 경우 5km 당 100원의 추가 요금이 발생한다.")
    @ParameterizedTest
    @CsvSource({"11, 100", "15, 100", "16, 200", "50, 800"})
    void calculateFare_between_10km_50km(int distance, int extraFare) {
        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN");

        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, distance, 2);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges, Arrays.asList(line1));

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250 + extraFare);
    }

    @DisplayName("이동 거리가 50km 이상인 경우 8km 당 100원의 추가 요금이 발생한다.")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 100", "8, 100", "9, 200"})
    void calculateFare_over_50km(int distance, int extraFare) {
        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN", 0);

        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, 50, 2);
        LineStation 강남_양재 = new LineStation(3L, 2L, distance, 1);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));
        lineStationEdges.add(new LineStationEdge(강남_양재, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges, Arrays.asList(line1));

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250 + 800 + extraFare);
    }

    @DisplayName("추가 요금이 있는 지하철 노선을 이용하면, 추가 요금 중 가장 비싼 금액이 운임 요금에 추가된다.")
    @CsvSource(value = {"0, 0", "0, 900", "500, 900"})
    @ParameterizedTest
    void calculateFare_when_taking_line_with_extra_fare(int extraFare, int extraFare2) {
        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN", extraFare);
        Line line2 = TestObjectUtils.createLine(2L, "신분당선", "RED", extraFare2);

        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, 1, 2);
        LineStation 강남_양재 = new LineStation(3L, 2L, 2, 1);
        LineStation 양재_출발역 = new LineStation(3L, null, 2, 1);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));
        lineStationEdges.add(new LineStationEdge(강남_양재, 2L));
        lineStationEdges.add(new LineStationEdge(양재_출발역, 2L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges, Arrays.asList(line1, line2));

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250 + Math.max(extraFare, extraFare2));
    }
}