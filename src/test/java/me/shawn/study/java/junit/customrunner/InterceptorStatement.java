package me.shawn.study.java.junit.customrunner;

import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class InterceptorStatement extends Statement {

    private final Statement invoker;
    private List<Interceptor> interceptors = new ArrayList<>();

    public InterceptorStatement(Statement invoker) {
        this.invoker = invoker;
    }

    @Override
    public void evaluate() throws Throwable {
        interceptors.stream().forEach(Interceptor::interceptBefore);
        invoker.evaluate();
        interceptors.stream().forEach(Interceptor::interceptAfter);
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }
}
