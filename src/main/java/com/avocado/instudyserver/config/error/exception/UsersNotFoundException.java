package com.avocado.instudyserver.config.error.exception;

import com.avocado.instudyserver.config.error.ErrorCode;

public class UsersNotFoundException extends NotFoundException{

    public UsersNotFoundException(){
        super(ErrorCode.USERS_NOT_FOUND);
    }
}
