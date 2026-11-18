package com.example.demoPestDiseaseProject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 서비스 전체에서 쓰는 에러 코드 모음.
 *
 * <p>새 에러가 필요하면 여기에 한 줄 추가하고 {@code throw new BusinessException(ErrorCode.XXX)} 로 던진다.
 * 응답 JSON 의 "code" 필드에는 enum 이름(예: NOT_FOUND)이 그대로 들어간다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // 진단
    FEEDBACK_ALREADY_GIVEN(HttpStatus.CONFLICT, "피드백은 한 번만 남길 수 있습니다.");

    // 도메인별 에러(예: 진단 하루 횟수 초과)는 기능을 만들 때 위에 추가

    private final HttpStatus status;
    private final String message;
}
