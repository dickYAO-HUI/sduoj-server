/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the General Public License, Version 3.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package cn.edu.sdu.qd.oj.submit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SubmissionJudgeResult {

    // 过程状态码
    COMPILING(-3, "Compiling"),
    JUDGING(-2, "Judging"),
    END(-1, "End"),
    PD(0, "Pending"),

    // 最终评测结果码
    AC(1, "Accepted"),
    TLE(2, "Time Limit Exceeded"),
    MLE(3, "Memory Limit Exceeded"),
    RE(4, "Runtime Error"),
    SE(5, "System Error"),
    WA(6, "Wrong Answer"),
    PR(7, "Presentation Error"),
    CE(8, "Compilation Error"),
    CAN(9, "Canceled");

    private final int code;
    private final String message;

    // 正式评测结果起始码
    public static final int RESULT_CODE_DIVIDING = 1;

    // 可能需要重新评测的结果状态码
    public static final List<Integer> WILL_REJUDGE_RESULT_CODES = Collections.unmodifiableList(
            Arrays.asList(
                    PD.code,
                    AC.code,
                    TLE.code,
                    MLE.code,
                    RE.code,
                    SE.code,
                    WA.code,
                    PR.code,
                    CE.code,
                    CAN.code
            )
    );

    /**
     * 通过 code 查找对应的 SubmissionJudgeResult 枚举
     *
     * @param code 整数状态码
     * @return 对应枚举值，找不到时返回 null
     */
    public static SubmissionJudgeResult of(int code) {
        for (SubmissionJudgeResult result : values()) {
            if (result.code == code) {
                return result;
            }
        }
        return null;
    }

    /**
     * 判断当前枚举的 code 是否等于传入 code
     *
     * @param otherCode 待比较的 code
     * @return 相等则返回 true，否则 false
     */
    public boolean equalsCode(int otherCode) {
        return this.code == otherCode;
    }
}
