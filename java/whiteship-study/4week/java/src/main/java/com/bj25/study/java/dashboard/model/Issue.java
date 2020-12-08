package com.bj25.study.java.dashboard.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Issue 데이터를 담는 클래스입니다.
 * 
 * @author BJ25
 */
@Getter
public class Issue implements Comparable<Issue> {

    private int number;
    private String title;
    private List<Participant> participants;

    @Builder
    public Issue(int number, String title, List<Participant> participants) {
        this.number = number;
        this.title = title;
        this.participants = participants;
    }

    /**
     * Issue에 참여한 인원을 추가합니다.
     */
    public void addParticipant(Participant participant) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }

        if (!this.participants.contains(participant)) {
            this.participants.add(participant);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Issue other = (Issue) obj;
        if (number != other.number)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int compareTo(Issue o) {
        if (this.number > o.getNumber()) {
            return 1;
        } else if (this.number < o.getNumber()) {
            return -1;
        } else {
            return 0;
        }
    }

}
