package com.solve_it_mvi.config;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.annotation.security.DeclareRoles;
@DeclareRoles({"admin","customer", "developer", "ops"})
@ApplicationPath("api")
public class ApplicationConfig extends Application {
}
