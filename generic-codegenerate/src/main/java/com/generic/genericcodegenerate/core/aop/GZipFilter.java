package com.generic.genericcodegenerate.core.aop;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipFilter implements Filter {
    private static final Logger logger = Logger.getLogger(GZipFilter.class);
    private static final String STATIC_TEMPLATE_SOURCE = "online/template";
    private static final String STATIC_TEMPLATE_SOURCE_2 = "clzcontext/template";
    private static final String STATIC_TEMPLATE_SOURCE_3 = "/content/";
    private static final String STATIC_TEMPLATE_SOURCE_4 = "/plug-in-ui/";
    private static final String STATIC_TEMPLATE_SOURCE_5 = "/template/";
    private static final String NO_FILTER = ".do";
    private static final String NO_FILTER_JSP = ".jsp";
    private static final String DIAN = ".";

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path;

        if (((req.getRequestURI().indexOf("online/template") != -1) ||
                (req.getRequestURI().indexOf("clzcontext/template") != -1) ||
                (req.getRequestURI().indexOf("/content/") != -1) ||
                (req.getRequestURI().indexOf("/plug-in-ui/") != -1) ||
                (req.getRequestURI().indexOf("/template/") != -1)) && (req.getRequestURI().indexOf(".") != -1) && (req.getRequestURI().indexOf(".do") == -1) && (req.getRequestURI().indexOf(".jsp") == -1)) {
            if (url.startsWith(req.getContextPath())) {
                url = url.replaceFirst(req.getContextPath(), "");
            }
            response.reset();
            String s = ResMime.get(url.substring(url.lastIndexOf(".")).replace(".", ""));
            if (s != null) response.setContentType(s);
            OutputStream os = null;
            InputStream is = null;
            try {
                is = getClass().getResourceAsStream(url);
                if (is != null) {
                    cacheResource(request, response, chain);

                    if (isGZipEncoding(req)) {
                        byte[] i = input2byte(is);
                        byte[] gzipData = gzip(i);
                        resp.addHeader("Content-Encoding", "gzip");
                        resp.setContentLength(gzipData.length);
                        ServletOutputStream output = response.getOutputStream();
                        output.write(gzipData);
                        output.flush();
                    } else {
                        os = response.getOutputStream();
                        FileCopyUtils.copy(is, os);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } catch (IOException e) {
                e.printStackTrace();

                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException localIOException1) {
                    }
                }
                if (is == null) return;
                try {
                    is.close();
                } catch (IOException localIOException2) {
                }
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException localIOException3) {
                    }
                }
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException localIOException4) {
                    }
            }
            try {
                is.close();
            } catch (IOException localIOException6) {
            }

        } else if (isGZipEncoding(req)) {
            Wrapper wrapper = new Wrapper(resp);
            chain.doFilter(request, wrapper);
            byte[] gzipData = gzip(wrapper.getResponseData());
            resp.addHeader("Content-Encoding", "gzip");
            resp.setContentLength(gzipData.length);
            ServletOutputStream output = response.getOutputStream();
            output.write(gzipData);
            output.flush();
        } else {
            chain.doFilter(request, response);
        }
    }

    public void cacheResource(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        uri = uri.substring(uri.lastIndexOf(".") + 1);

        long date = 0L;

        if (uri.equalsIgnoreCase("jpg")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("gif")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("css")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("js")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        res.setDateHeader("Expires", date);
    }

    private static boolean isGZipEncoding(HttpServletRequest request) {
        boolean flag = false;
        String encoding = request.getHeader("Accept-Encoding");
        if ((encoding != null) && (encoding.indexOf("gzip") != -1) && (request.getRequestURI().indexOf(".do") == -1) && (request.getRequestURI().indexOf(".jsp") == -1)) {
            flag = true;
        }
        return flag;
    }

    private byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            output.write(data);
        } catch (IOException localIOException) {
            try {
                output.close();
            } catch (IOException localIOException1) {
            }
        } finally {
            try {
                output.close();
            } catch (IOException localIOException2) {
            }
        }
        return byteOutput.toByteArray();
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
