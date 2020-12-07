package com.bj25.study.java.dashboard;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.bj25.study.java.dashboard.model.Issue;
import com.bj25.study.java.dashboard.model.Participant;
import com.bj25.study.java.dashboard.model.Project;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

/**
 * Dashboard를 만드는 클래스입니다.
 * 
 * @author BJ25
 */
@NoArgsConstructor
public class Dashboard {
    private static final Logger log = Logger.getLogger(Dashboard.class.getName());

    private final String DEFAULT_AUTHOR = "whiteship";
    private final String DEFAULT_REPO = "live-study";

    /**
     * 주어지는 저자와 레포지토리명으로 대시보드를 만듭니다.
     * 
     * @param author
     * @param repository
     * @return
     */
    public boolean createDashboard(String author, String repository) {
        boolean isSuccess = false;

        if (StringUtils.isBlank(author)) {
            author = this.DEFAULT_AUTHOR;
        }

        if (StringUtils.isBlank(repository)) {
            repository = this.DEFAULT_REPO;
        }

        try {
            Project project = GitHubHelper.getInstance().getProjectInfo(author, repository);
            isSuccess = this.drawDashboard(project);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to create dashboard.", e);
        }

        return isSuccess;
    }

    /**
     * 프로젝트 정보를 이용해 대시보드를 만듭니다.
     * 
     * @param project
     * @return
     */
    private boolean drawDashboard(Project project) {
        boolean isSuccess = false;
        final List<Issue> issues = project.getIssues();
        final int total = project.getIssueCount();

        Map<String, List<Issue>> userMap = new HashMap<>();

        List<String> userIds = issues.stream()
                .filter(i -> i.getParticipants() != null && !i.getParticipants().isEmpty())
                .flatMap(i -> i.getParticipants().stream()).map(p -> p.getUserId()).distinct()
                .collect(Collectors.toList());
        Collections.sort(userIds);

        for (Issue issue : issues) {
            if (issue.getParticipants() != null && !issue.getParticipants().isEmpty()) {
                for (Participant p : issue.getParticipants()) {
                    List<Issue> participatedIssues = userMap.getOrDefault(p.getUserId(), new ArrayList<Issue>());
                    participatedIssues.add(issue);
                    userMap.put(p.getUserId(), participatedIssues);
                }
            }
        }

        try {
            return this.createDashboard(userIds, userMap, total);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Fail to create dashboard file.", e);
        }

        return isSuccess;
    }

    /**
     * 최종 정보를 이용하여 대시보드 파일을 만드는 메서드입니다.
     * 
     * @param userIds
     * @param userIssueMap
     * @param totalIssue
     * @return
     * @throws IOException
     */
    private boolean createDashboard(List<String> userIds, Map<String, List<Issue>> userIssueMap, int totalIssue)
            throws IOException {
        final String output = System.getProperty("user.dir") + File.separator + ".." + File.separator + "dashboard"
                + File.separator + "README.md";

        List<String> lines = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|참여자|");
        for (int i = 0; i < totalIssue; i++) {
            stringBuilder.append((i + 1) + "주차|");
        }
        stringBuilder.append("참여율|");
        lines.add(stringBuilder.toString());

        stringBuilder = new StringBuilder();
        stringBuilder.append("|---|");
        for (int i = 0; i < totalIssue + 1; i++) {
            stringBuilder.append("---|");
        }
        lines.add(stringBuilder.toString());

        for (String userId : userIds) {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("|");
            sBuilder.append(userId);
            sBuilder.append("|");
            List<Issue> issue = userIssueMap.get(userId);
            for (int i = 0; i < totalIssue; i++) {
                int issueNumber = i + 1;
                if (issue.stream().filter(is -> is.getNumber() == issueNumber).count() > 0) {
                    sBuilder.append(":white_check_mark:|");
                } else {
                    sBuilder.append("|");
                }
            }
            sBuilder.append(this.calculateParticipationRating(issue.size(), totalIssue));
            sBuilder.append("%|");
            lines.add(sBuilder.toString());
        }

        Path file = Paths.get(output);
        Files.write(file, lines, StandardCharsets.UTF_8);
        return true;
    }

    private BigDecimal calculateParticipationRating(int target, int total) {
        return BigDecimal.valueOf(target * 100).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
    }

}
