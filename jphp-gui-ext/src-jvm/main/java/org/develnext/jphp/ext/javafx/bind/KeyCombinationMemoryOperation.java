package org.develnext.jphp.ext.javafx.bind;

import javafx.scene.input.KeyCombination;
import php.runtime.Memory;
import php.runtime.common.Constants;
import php.runtime.env.Environment;
import php.runtime.env.TraceInfo;
import php.runtime.memory.StringMemory;
import php.runtime.memory.support.MemoryOperation;

public class KeyCombinationMemoryOperation extends MemoryOperation<KeyCombination> {
    @Override
    public Class<?>[] getOperationClasses() {
        return new Class<?>[] { KeyCombination.class };
    }

    @Override
    public KeyCombination convert(Environment environment, TraceInfo traceInfo, Memory memory) {
        if (memory.isNull()) {
            return null;
        }

        String value = memory.toString();

        // "Ctrl" is a literal physical-Control-key modifier on every platform in JavaFX
        // (unlike "Shortcut", which auto-maps to Cmd on mac). Accelerators throughout the
        // IDE are authored as "Ctrl + ..." strings, so remap them to the platform shortcut
        // key on mac instead of touching every call site.
        if (Constants.OS_MAC) {
            value = value.replaceAll("(?i)\\bctrl\\b", "Shortcut");
        }

        return KeyCombination.valueOf(value);
    }

    @Override
    public Memory unconvert(Environment environment, TraceInfo traceInfo, KeyCombination keyCombination) {
        return keyCombination == null ? Memory.NULL : StringMemory.valueOf(keyCombination.toString());
    }
}
