package com.unn.constants;

import lombok.Getter;

@Getter
public enum UserTypes {
    ADMIN(1000L),
    MODER(1001L),
    DOCTOR(1002L),
    PATIENT(1003L);

    private Long id;

    UserTypes(Long id) {
        this.id = id;
    }
}
