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

        // 이슈 리스트 얻기
        List<GHIssue> issues = this.github.getRepository(author + "/" + repository).getIssues(GHIssueState.ALL);

        // 재분류를 위한 Project 데이터 생성
        Project project = Project.builder().build();

        // 이슈를 순회하며 필요 정보 습득
        for (GHIssue issue : issues) {
            // 재분류를 위한 Issue 정보 생성
            Issue targetIssue = Issue.builder().number(issue.getNumber()).title(issue.getTitle()).build();

            // 코멘트 존재 여부 확인 - 리소스 낭비 방지
            if (issue.getCommentsCount() != 0) {
                // 코멘트 리스트 습득
                List<GHIssueComment> comments = issue.getComments();
                // 코멘트를 순회하며 필요 정보 습득
                for (GHIssueComment comment : comments) {
                    // 로그인 아이디 습득
                    final String login = comment.getUser().getLogin();
                    // 재분류를 위한 참여자 정보 생성
                    Participant participant = Participant.builder().userId(login).build();
                    // 재분류를 위한 issue 데이터에 참여자 정보 저장
                    targetIssue.addParticipant(participant);
                }
            }
            // 재분류를 위한 Project 데이터에 이슈 정보 저장
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
