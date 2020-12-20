package com.unn.constants;

import lombok.Getter;

@Getter
public enum UserTypes {
    ADMIN(1000l),
    MODER(1001l),
    DOCTOR(1002l),
    PATIENT(1003l);

    private long id;

    UserTypes(long id) {
        this.id = id;
    }
}
