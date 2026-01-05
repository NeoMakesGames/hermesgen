package com.hermesmod;

import jdk.incubator.code.dialect.core.CoreOp;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// --- 1. NEW IMPORTS ---
import jdk.incubator.code.Op;
import jdk.incubator.code.Reflect;
import java.util.Optional;

public class HermesGen implements ModInitializer {
    public static final String MOD_ID = "hermesgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("⚡ HermesGen Initializing on Babylon JDK...");
        runReflectionDiagnostics();
    }

    private void runReflectionDiagnostics() {
        LOGGER.info("🔍 Scanning for code models...");

        try {
            // --- 2. NEW ENTRY POINT ---
            // 'CodeReflection.reflectCode' -> 'Op.ofMethod'
            Optional<CoreOp.FuncOp> opTree = Op.ofMethod(
                    HermesGen.class.getMethod("gpuNoiseStub", double.class, double.class)
            );

            if (opTree.isPresent()) {
                LOGGER.info("✅ SUCCESS: Captured Code Model for 'gpuNoiseStub'");
                LOGGER.info("--- START AST DUMP ---");
                LOGGER.info("\n" + opTree.get().toText());
                LOGGER.info("--- END AST DUMP ---");
            } else {
                LOGGER.warn("⚠️ WARNING: Reflection succeeded, but no Code Model was returned.");
            }

        } catch (NoSuchMethodException e) {
            LOGGER.error("Method lookup failed", e);
        }
    }

    /**
     * Test Subject.
     * --- 3. NEW ANNOTATION ---
     * '@CodeReflection' -> '@Reflect'
     */
    @Reflect
    public static double gpuNoiseStub(double x, double z) {
        double frequency = 0.05;
        return Math.sin(x * frequency) * Math.cos(z * frequency);
    }
}