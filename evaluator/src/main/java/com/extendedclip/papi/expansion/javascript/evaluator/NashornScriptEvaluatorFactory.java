package com.extendedclip.papi.expansion.javascript.evaluator;

import com.extendedclip.papi.expansion.javascript.evaluator.util.InjectionUtil;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class NashornScriptEvaluatorFactory implements ScriptEvaluatorFactory {
    public static final Collection<String> LIBRARIES = Arrays.asList(
            "nashorn-core-15.4.isolated-jar",
            "asm-commons-9.2.isolated-jar",
            "asm-util-9.2.isolated-jar",
            "asm-9.2.isolated-jar"
    );

    private final ThreadLocal<ScriptEngine> engines;

    private NashornScriptEvaluatorFactory(final NashornScriptEngineFactory engineFactory) {
        this.engines = ThreadLocal.withInitial(() -> engineFactory.getScriptEngine("--no-java"));
    }

    @Override
    public ScriptEvaluator create(final Map<String, Object> bindings) {
        return new NashornScriptEvaluator(engines.get(), bindings);
    }

    public static ScriptEvaluatorFactory create() throws URISyntaxException, ReflectiveOperationException, NoSuchAlgorithmException, IOException {
        InjectionUtil.inject(LIBRARIES);
        return new NashornScriptEvaluatorFactory(new NashornScriptEngineFactory());
    }

}
