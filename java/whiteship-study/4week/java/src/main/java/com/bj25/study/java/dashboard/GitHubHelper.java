package com.bj25.study.java.dashboard;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bj25.study.java.dashboard.model.Issue;
import com.bj25.study.java.dashboard.model.Participant;
import com.bj25.study.java.dashboard.model.Project;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

/**
 * GitHub API 라이브러리를 wrapping한 싱글톤 클래스입니다.
 * 
 * @author BJ25
 */
public class GitHubHelper {

    private static final Logger log = Logger.getLogger(GitHubHelper.class.getName());

    private GitHub github;
    private boolean isConnect = false;

    private GitHubHelper() {
        try {
            // GitHub API 도큐먼트에 따라 사용자 폴더의 '.github' 파일에 존재하는 프로퍼티를 읽어서 GitHub 인스턴스를 만듭니다.
            // 이는 인증을 위한 데이터를 분리하기에, 보안적으로 매우 좋은 접근방식입니다.
            this.github = GitHubBuilder.fromPropertyFile().build();
            this.isConnect = true;
        } catch (IOException e) {
            log.log(Level.WARNING, "Fail to connect the GitHub.", e);
        }
    }

    /**
     * 연결이 정상적이면 true를 반환합니다.
     * 
     * @return
     */
    public boolean isConnect() {
        return this.isConnect;
    }

    /**
     * 저자와 레포지토리 명으로 프로젝트 정보를 가져오는 메서드입니다.
     * 
     * @param author
     * @param repository
     * @return
     * @throws IOException
     */
    public Project getProjectInfo(String author, String repository) throws IOException {
        if (StringUtils.isBlank(author)) {
            throw new IllegalArgumentException("The author is required!");
        }

        if (StringUtils.isBlank(repository)) {
            throw new IllegalArgumentException("The repository is required!");
        }

        List<GHIssue> issues = this.github.getRepository(author + "/" + repository).getIssues(GHIssueState.ALL);

        Project project = Project.builder().build();

        for (GHIssue issue : issues) {
            Issue targetIssue = Issue.builder().number(issue.getNumber()).title(issue.getTitle()).build();

            if (issue.getCommentsCount() != 0) {
                List<GHIssueComment> comments = issue.getComments();
                for (GHIssueComment comment : comments) {
                    final String login = comment.getUser().getLogin();
                    Participant participant = Participant.builder().userId(login).build();
                    targetIssue.addParticipant(participant);
                }
            }

            project.addIssue(targetIssue);
        }

        return project;
    }

    public static GitHubHelper getInstance() {
        return SingletonLazyHolder.INSTANCE;
    }

    private static class SingletonLazyHolder {
        private static final GitHubHelper INSTANCE = new GitHubHelper();
    }
}
