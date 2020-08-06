package wooteco.subway.maps.map.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import wooteco.subway.maps.line.domain.LineStation;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class SubwayPathTest {

    @DisplayName("이동 거리가 10km 이내인 경우 운임 요금은 기본 요금인 1250원 이다.")
    @Test
    void calculateFare_within_10km() {
        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, 2, 2);
        LineStation 강남_양재 = new LineStation(3L, 2L, 2, 1);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));
        lineStationEdges.add(new LineStationEdge(강남_양재, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges);

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250);
    }

    @DisplayName("이동 거리가 10km - 50km 이내인 경우 5km 당 100원의 추가 요금이 발생한다.")
    @ParameterizedTest
    @CsvSource({"11, 100", "15, 100", "16, 200", "50, 800"})
    void calculateFare_between_10km_50km(int distance, int extraFare) {
        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, distance, 2);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges);

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250 + extraFare);
    }

    @DisplayName("이동 거리가 50km 이상인 경우 8km 당 100원의 추가 요금이 발생한다.")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 100", "8, 100", "9, 200"})
    void calculateFare_beyond_50km(int distance, int extraFare) {
        LineStation 교대_출발역 = new LineStation(1L, null, 0, 0);
        LineStation 교대_강남 = new LineStation(2L, 1L, 50, 2);
        LineStation 강남_양재 = new LineStation(3L, 2L, distance, 1);

        ArrayList<LineStationEdge> lineStationEdges = new ArrayList<>();

        lineStationEdges.add(new LineStationEdge(교대_출발역, 1L));
        lineStationEdges.add(new LineStationEdge(교대_강남, 1L));
        lineStationEdges.add(new LineStationEdge(강남_양재, 1L));

        SubwayPath subwayPath = new SubwayPath(lineStationEdges);

        int totalFare = subwayPath.calculateFare();
        assertThat(totalFare).isEqualTo(1250 + 800 + extraFare);
    }
}