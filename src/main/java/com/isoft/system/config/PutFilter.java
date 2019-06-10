package com.isoft.system.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * 解决spring boot无法获取put请求值的问题
 */
@Component
public class PutFilter extends HttpPutFormContentFilter {
}