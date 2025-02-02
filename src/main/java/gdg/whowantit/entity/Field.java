package gdg.whowantit.entity;

import lombok.Getter;

@Getter
public enum Field {
    LIVING_SUPPORT("생활 편의 지원"),
    HOUSING_ENVIRONMENT("주거환경"),
    COUNSELING("상담"),
    EDUCATION("교육"),
    HEALTHCARE("보건의료"),
    CULTURAL_EVENTS("문화행사"),
    ENVIRONMENTAL_PROTECTION("환경보호"),
    DISASTER_RELIEF("재해 재난"),
    PUBLIC_INTEREST_RIGHTS("공익 인권"),
    MENTORING("멘토링");

    private final String description; // 한글 설명

    Field(String description) {
        this.description = description;
    }
}
