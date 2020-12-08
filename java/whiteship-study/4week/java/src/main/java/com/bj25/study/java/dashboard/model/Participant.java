package com.bj25.study.java.dashboard.model;

import lombok.Builder;
import lombok.Getter;

/**
 * 이슈 참여자 정보를 저장하는 클래스입니다.
 * 
 * @author BJ25
 */
@Getter
public class Participant implements Comparable<Participant> {

    private String userId;

    @Builder
    public Participant(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        Participant other = (Participant) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public int compareTo(Participant o) {
        return this.userId.compareTo(o.userId);
    }

}
