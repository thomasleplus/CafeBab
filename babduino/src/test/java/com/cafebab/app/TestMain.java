/**
 * 
 */
package com.cafebab.app;

import org.junit.jupiter.api.Test;

/**
 * @author thomas
 *
 */
class TestMain {

    @Test
    void test() {
        try {
            Main.main(null);
        } catch (final UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

}
