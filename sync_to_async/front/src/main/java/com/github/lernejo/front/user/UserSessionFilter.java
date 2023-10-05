package com.github.lernejo.front.user;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public record UserSessionFilter(SessionRepository sessionRepository) implements Filter {

    private static final String SESSION_ID_HEADER_NAME = "Session-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String sessionId = ((HttpServletRequest) request).getHeader(SESSION_ID_HEADER_NAME);
        if (sessionId != null) {
            UserSession userSession = sessionRepository.get(sessionId);
            if (userSession != null) {
                SessionContext.set(userSession);
            }
        }

        // Used to write headers before the body
        HttpServletResponse wrappedResponse = new HttpServletResponseWrapper((HttpServletResponse) response) {

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                UserSession userSession = SessionContext.get();
                if (userSession != null) {
                    ((HttpServletResponse) response).setHeader(SESSION_ID_HEADER_NAME, userSession.getId());
                }
                return super.getOutputStream();
            }
        };

        chain.doFilter(request, wrappedResponse);


    }
}
