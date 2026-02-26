package com.inmobiliaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que arranca toda la aplicacion Spring Boot.
 *
 * @SpringBootApplication equivale a tres anotaciones juntas:
 *   @Configuration        permite definir beans de configuracion
 *   @EnableAutoConfiguration  Spring configura todo automaticamente
 *   @ComponentScan        escanea paquetes buscando componentes
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@SpringBootApplication
public class InmobiliariaBackendApplication {

    /**
     * Metodo principal que lanza la aplicacion.
     * Spring Boot levanta el servidor Tomcat embebido
     * y conecta automaticamente con PostgreSQL.
     *
     * @param args argumentos de linea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(InmobiliariaBackendApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  INMOBILIARIA BACKEND INICIADO              ");
        System.out.println("  Servidor: http://localhost:8080             ");
        System.out.println("  Spring Boot 3.4.x + PostgreSQL 17          ");
        System.out.println("==============================================");
    }
}