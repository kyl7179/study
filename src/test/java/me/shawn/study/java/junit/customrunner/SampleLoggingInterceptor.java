package me.shawn.study.java.junit.customrunner;

class SampleLoggingInterceptor implements Interceptor {
    @Override
    public void interceptBefore() {
        System.out.println("before-test");
    }

    @Override
    public void interceptAfter() {
        System.out.println("after-test");
    }
}
