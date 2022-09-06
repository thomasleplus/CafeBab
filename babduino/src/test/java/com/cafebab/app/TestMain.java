/**
 * 
 */
package com.cafebab.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author thomas
 *
 */
class TestMain {

    @Test
    void test() {
        Assertions.assertThrows(UnsatisfiedLinkError.class, () -> {
            Main.main(null);
        });
    }

}
