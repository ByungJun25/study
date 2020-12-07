package com.bj25.study.java.dashboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Project 정보를 저장하는 클래스입니다.
 * 
 * @author BJ25
 */
@Getter
public class Project {

    private List<Issue> issues;

    @Builder
    public Project(List<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        if (this.issues == null) {
            this.issues = new ArrayList<>();
        }

        if (!this.issues.contains(issue)) {
            this.issues.add(issue);
        }
    }

    public Issue getIssue(int issueNumber) {
        return this.issues.stream().filter(i -> i.getNumber() == issueNumber).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no issue"));
    }

    public Issue getIssue(Issue issue) {
        return this.issues.stream().filter(i -> i.equals(issue)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no issue"));
    }

    public int getIssueCount() {
        if (this.issues == null) {
            return 0;
        }

        return this.issues.size();
    }

}
