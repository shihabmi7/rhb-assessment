package com.shihab.rhbaccess.external;

public record ExternalApiResult(String url, int status, int contentLength) {
}
