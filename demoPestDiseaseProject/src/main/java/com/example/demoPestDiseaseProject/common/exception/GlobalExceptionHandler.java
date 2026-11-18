package com.example.demoPestDiseaseProject.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 컨트롤러에서 터진 예외를 한곳에서 받아 {@link ErrorResponse} JSON 으로 바꿔 준다.
 *
 * <p>ResponseEntityExceptionHandler 를 상속해서 스프링 MVC 기본 예외
 * (없는 URL 404, 허용 안 된 메서드 405, JSON 파싱 실패 400, 파라미터 타입 오류 400 등)도
 * 올바른 상태코드 그대로 같은 JSON 형태로 응답한다.
 *
 * <p>스프링은 가장 구체적인 예외 타입의 핸들러를 고르므로, 맨 아래 Exception 핸들러는
 * 위에서 못 잡은 "예상 못 한 오류"만 받는다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /** 서비스에서 직접 던진 BusinessException */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity.status(code.getStatus())
                .body(new ErrorResponse(code.name(), e.getMessage()));
    }

    /** @PreAuthorize 등 메서드 보안에서 권한이 없을 때 (없으면 아래 Exception 핸들러가 500 으로 바꿔 버림) */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatus())
                .body(new ErrorResponse(ErrorCode.FORBIDDEN.name(), ErrorCode.FORBIDDEN.getMessage()));
    }

    /** 그 밖의 예상 못 한 오류: 로그를 남기고 내부 내용은 숨긴 채 500 응답 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
        log.error("처리되지 않은 예외", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.INTERNAL_ERROR.name(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }

    /** @Valid 검증 실패: 첫 번째 필드 오류를 "필드명: 메시지" 로 돌려준다 */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null
                ? fieldError.getField() + ": " + fieldError.getDefaultMessage()
                : ErrorCode.INVALID_INPUT.getMessage();
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus())
                .body(new ErrorResponse(ErrorCode.INVALID_INPUT.name(), message));
    }

    /** 나머지 스프링 MVC 기본 예외: 스프링이 정한 상태코드는 유지하고 본문만 우리 형식으로 바꾼다 */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String code = status instanceof HttpStatus hs ? hs.name() : String.valueOf(status.value());
        String message = body instanceof ProblemDetail pd && pd.getDetail() != null ? pd.getDetail() : code;
        return ResponseEntity.status(status).headers(headers).body(new ErrorResponse(code, message));
    }
}
