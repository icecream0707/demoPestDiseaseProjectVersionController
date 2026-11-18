package com.example.demoPestDiseaseProject.common.exception;

/**
 * 모든 에러 응답의 JSON 형태.
 *
 * <pre>
 * { "code": "NOT_FOUND", "message": "대상을 찾을 수 없습니다." }
 * </pre>
 *
 * @param code    에러 종류 (ErrorCode 이름, 또는 스프링 기본 에러면 HTTP 상태 이름)
 * @param message 사용자에게 보여줄 메시지
 */
public record ErrorResponse(String code, String message) {
}
