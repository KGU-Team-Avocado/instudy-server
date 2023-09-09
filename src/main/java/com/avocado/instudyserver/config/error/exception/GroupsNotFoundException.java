package com.avocado.instudyserver.config.error.exception;
import com.avocado.instudyserver.config.error.ErrorCode;
public class GroupsNotFoundException extends NotFoundException{
    public GroupsNotFoundException(){
        super(ErrorCode.GROUPS_NOT_FOUND);
    }
}
