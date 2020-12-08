package com.bj25.study.java.dashboard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DashboardTest {

    @DisplayName("GitHub API를 이용한 대시보드 만들기 테스트")
    @Test
    public void createDashboardTest() {
        // given
        Dashboard dashboard = new Dashboard();

        // when
        boolean result = dashboard.createDashboard("whiteship", "live-study");

        // then
        assertTrue(result);
    }

}
