package com.project.ipyang.common;

public class IpyangEnum {
    public enum MemberRoleType {
        ADMIN,
        USER
    }

    public enum Status {
        Y, N
    }

    public enum BoardCategory {
        INFO,
        PROMO,
        REPORT

    }

    public enum ProductType{
        FOOD,TOY,SNACK,SAND,BEAUTY,ETC
    }


    public enum NoticeCategory {
        NOTICE,
        EVENT,
        FAQ
    }

    public enum PointType {
        CHARGE,
        BUY,
        SELL
    }


    public enum WarningReason{
        BAD_WORDS,FRAUD,LYING

    }
    public enum LikeType{
        BOARD,COMMENT

    }

    public enum MessageType {
        ENTER, TALK, QUIT
    }


}
