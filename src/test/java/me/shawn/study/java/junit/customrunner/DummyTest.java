package me.shawn.study.java.junit.customrunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * @author shawn.ctl
 */
@RunWith(InterceptorRunner.class)
@InterceptorRunner.InterceptorClasses(SampleLoggingInterceptor.class)
public class DummyTest {

    @Test
    public void 테스트() {
        assertTrue(true);
    }
}
