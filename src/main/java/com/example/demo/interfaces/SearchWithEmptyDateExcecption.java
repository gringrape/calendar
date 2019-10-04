package com.example.demo.interfaces;

public class SearchWithEmptyDateExcecption extends RuntimeException {

    public SearchWithEmptyDateExcecption(String target) {

        super("검색에 필요한 " + target + "이 존재하지 않습니다.");

    }

}
