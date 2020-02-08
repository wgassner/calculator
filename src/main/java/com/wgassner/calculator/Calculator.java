package com.wgassner.calculator;

import org.springframework.stereotype.Service;

/**
 *  Calculation Service
 */
@Service
public class Calculator {
    int sum(int a, int b) {
        return a + b;
    }
}
