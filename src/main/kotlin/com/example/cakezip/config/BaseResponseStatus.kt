package com.example.cakezip.config

import lombok.Getter

@Getter
enum class BaseResponseStatus  //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
    (// 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요
    val isSuccess: Boolean, val code: Int, val message: String
) {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."), EMPTY_JWT(false, 2001, "JWT를 입력해주세요."), INVALID_JWT(
        false,
        2002,
        "유효하지 않은 JWT입니다."
    ),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),  // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),  // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."), POST_USERS_EMPTY_NAME(
        false,
        2016,
        "이름을 입력해주세요."
    ),
    POST_USERS_EMPTY_GROUP(false, 2017, "소속을 입력해주세요."), POST_USERS_INVALID_EMAIL(
        false,
        2018,
        "이메일 형식을 확인해주세요."
    ),
    POST_USERS_INVALID_NICKNAME(false, 2019, "닉네임 형식을 확인해주세요."), POST_USERS_SIZE_NICKNAME(
        false,
        2020,
        "닉네임은 2글자 이상 8글자 이하여야 합니다."
    ),
    POST_USERS_EXISTS_EMAIL(false, 2021, "이미 가입된 이메일입니다."), POST_USERS_EXISTS_NICKNAME(
        false,
        2022,
        "이미 사용중인 닉네임입니다."
    ),
    GET_USERS_EMPTY_DATA(false, 2023, "유저에 관한 정보가 없습니다"), GET_TARGETUSERS_EMPTY_DATA(
        false,
        2030,
        "알림받을 유저가 존재하지 않습니다"
    ),
    POST_USERS_INACTIVE_STATUS(false, 2050, "비활성화된 유저입니다"), POST_USERS_INVALID_CATEGORY(
        false,
        2024,
        "잘못된 카테고리명입니다."
    ),
    PATCH_USERS_INVALID_NICKNAME_PERIOD(false, 2025, "닉네임 변경은 14일 이후에 가능합니다."),

    /**
     * 2500 : Questions 오류
     */
    //questions
    POST_EMPTY_ESSENTIAL_BODY(false, 2500, "필수정보들을 모두 입력해주세요"), POST_QUESTIONS_INVALID_CATEGORY_RANGE(
        false,
        2501,
        "카테고리를 범위안에 입력해주세요"
    ),
    GET_QUESTIONS_EMPTY_DATA(false, 2502, "존재하지 않는 질문입니다"), POST_IMAGES_FAILED(
        false,
        2510,
        "S3에 이미지 전송을 실패했습니다."
    ),
    POST_QUESTIONS_INVALID_SMALLCATEGORY(false, 2011, "기타인 경우는 하위카테고리를 선택할 수 없습니다"),

    /**
     * 2600 : Scrap 오류
     */
    //scrap
    POST_INVALID_SCRAP_AUTH(false, 2600, "자신의 글은 스크랩할 수 없습니다."),

    /**
     * 2700 : Like 오류
     */
    //like
    POST_INVALID_LIKE_AUTH(false, 2700, "자신의 글은 좋아요 할 수 없습니다."),

    /**
     * 2800 : Reply 오류
     */
    POST_INVALID_REREPLY_AUTH(false, 2800, "자신의 답변은 답변할 수 없습니다."), GET_REPLIES_EMPTY_DATA(
        false,
        2810,
        "해당 답변이 존재하지 않습니다,"
    ),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),  // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."), FAILED_TO_LOGIN(
        false,
        3014,
        "가입된 이메일이 아닙니다."
    ),  // [PATCH] /replies/adoption/:userIdx/:replyIdx
    PATCH_ADOPT_NOT_SAME(false, 3600, "해당 답변을 채택할 권한이 없습니다."), FAILED_ADOPT_REPLY(
        false,
        3601,
        "답변 채택에 실패하였습니다."
    ),
    ALREADY_ADOPTED(false, 3602, "이미 채택이 완료된 질문입니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."), SERVER_ERROR(
        false,
        4001,
        "서버와의 연결에 실패하였습니다."
    ),  //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false, 4014, "유저네임 수정 실패"), PASSWORD_ENCRYPTION_ERROR(
        false,
        4011,
        "비밀번호 암호화에 실패하였습니다."
    ),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");

}
