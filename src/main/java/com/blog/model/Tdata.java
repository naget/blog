package com.blog.model;

import lombok.Data;

/**
 * Created by t on 2017/3/10.
 */
@Data
public class Tdata<T,F,A> {
    int code;
    T messageOne;
    F messageTwo;
    A messageThree;
    public Tdata(int code,T dataOne,F dataTwo,A dataThree){
        this.code=code;
        this.messageOne=dataOne;
        this.messageTwo=dataTwo;
        this.messageThree=dataThree;
    }
    public Tdata(int code,T dataOne,F dataTwo)
    {
        this.code=code;
        this.messageOne=dataOne;
        this.messageTwo=dataTwo;
    }
    public Tdata(int code,T dataOne)
    {
        this.code=code;
        this.messageOne=dataOne;
    }
    public Tdata(){}
}
