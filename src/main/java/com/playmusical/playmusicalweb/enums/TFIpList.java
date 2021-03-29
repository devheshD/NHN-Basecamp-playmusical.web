package com.playmusical.playmusicalweb.enums;

import lombok.Getter;

@Getter
public enum TFIpList {

    fox("http://airfoxket.nhn.com/api/member/login"), girin(
        "http://girinticket.nhn.com/auth"), hippo(
        "http://hippo-playmusical.nhn.com/api/login"), panda(
        "http://moviedapanda.nhn.com/member/login/external"), duck(
        "http://movieduck.nhn.com/login"), swan("http://movieswan.nhn.com/account/api/auth"), tart(
        "http://tart.nhn.com/remote/login"), rabbit("http://tomoro.nhn.com/login"), lion(
        "http://ticget.nhn.com/api/externlogin"), dalcom(
        "http://jogiyo.nhn.com/user/login_all"), saecom("api/accesslogin"), deer(
        "http://tart.nhn.com/remote/login");

    private String url;

    TFIpList(String url) {
        this.url = url;
    }

}
