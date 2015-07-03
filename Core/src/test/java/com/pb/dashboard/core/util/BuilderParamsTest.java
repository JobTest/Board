package com.pb.dashboard.core.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad
 * Date: 01.10.14
 */
public class BuilderParamsTest {

    private Map<Object, Object> params;

    @Before
    public void setUp() throws Exception {
        params = new LinkedHashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");
    }

    @After
    public void tearDown() throws Exception {
        params = null;
    }

    @Test
    public void testGetPath() throws Exception {
        String path = UrlParamBuilder.getPath(params);
        assertEquals("?key1=value1&key2=value2", path);
    }

    @Test
    public void testToString() throws Exception {
        String s = new UrlParamBuilder().toString();
        assertEquals("", s);
    }

    @Test
    public void testName() throws Exception {
        String s = new UrlParamBuilder()
                .addParam("key1", "value1")
                .addParam("key2", "value2")
                .toString();
        assertEquals("?key1=value1&key2=value2", s);
    }
}
