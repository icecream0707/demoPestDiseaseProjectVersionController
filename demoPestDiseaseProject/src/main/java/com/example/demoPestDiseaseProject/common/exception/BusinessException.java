package com.example.demoPestDiseaseProject.common.exception;

import lombok.Getter;

/**
 * 서비스 로직에서 "예상 가능한 실패"를 알릴 때 던지는 예외.
 * GlobalExceptionHandler 가 받아서 ErrorCode 의 상태코드와 메시지로 응답한다.
 *
 * <pre>
 * throw new BusinessException(ErrorCode.NOT_FOUND);
 * throw new BusinessException(ErrorCode.NOT_FOUND, "진단 기록이 없습니다. id=" + id);
 * </pre>
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    /** 기본 메시지 대신 상황에 맞는 메시지를 응답에 쓰고 싶을 때 */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
