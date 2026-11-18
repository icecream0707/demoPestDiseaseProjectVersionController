/**
 * 여러 도메인에서 함께 쓰는 공통 유틸 함수 모음.
 *
 * <p>규칙:
 * <ul>
 *   <li>상태가 없는 static 메서드만 둔다 (예: {@code DateUtils.startOfToday()}).</li>
 *   <li>특정 도메인에서만 쓰는 함수는 여기 말고 그 도메인 패키지에 둔다.</li>
 *   <li>클래스는 {@code final} + private 생성자로 만들어 객체 생성을 막는다.</li>
 * </ul>
 */
package com.example.demoPestDiseaseProject.common.util;
