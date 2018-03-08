package com.generic.genericcodegenerate.core.aop;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WrappedOutputStream extends ServletOutputStream{

    private ByteArrayOutputStream buffer;

    public WrappedOutputStream(ByteArrayOutputStream buffer) {
        this.buffer = buffer;
    }

    public void write(int b) throws IOException {
        this.buffer.write(b);
    }

    public byte[] toByteArray() {
        return this.buffer.toByteArray();
    }

    public boolean isReady() {
        return false;
    }

    public void setWriteListener(WriteListener writeListener) {

    }
}
