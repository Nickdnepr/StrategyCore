package com.nickdnepr.strategy.utils;

public interface InputRange {

    InputRange POSITIVE_NUMBERS = i -> i > 0;

    boolean validateInt(int i);

}
