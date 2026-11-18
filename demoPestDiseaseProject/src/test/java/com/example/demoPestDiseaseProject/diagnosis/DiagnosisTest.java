package com.example.demoPestDiseaseProject.diagnosis;

import com.example.demoPestDiseaseProject.common.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** DB 없이 엔티티 규칙만 확인하는 단위 테스트 */
class DiagnosisTest {

    @Test
    void 피드백은_한_번만_남길_수_있다() {
        Diagnosis diagnosis = new Diagnosis(null, null, "a.jpg", "a_thumb.jpg", false);

        diagnosis.giveFeedback(Feedback.CORRECT);

        assertThat(diagnosis.getFeedback()).isEqualTo(Feedback.CORRECT);
        assertThatThrownBy(() -> diagnosis.giveFeedback(Feedback.INCORRECT))
                .isInstanceOf(BusinessException.class);
        assertThat(diagnosis.getFeedback()).isEqualTo(Feedback.CORRECT); // 두 번째 시도로 값이 바뀌지 않음
    }
}
