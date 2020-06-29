package com.fatec.sga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SgaApplication {

	private static boolean isLogado = false;

	public static boolean isLogged() {
		return isLogado;
	}

	public static void LogIn() {
		isLogado = true;
	}

	public static void LogOut() {
		isLogado = false;
	}

	public static void main(String[] args) {
		SpringApplication.run(SgaApplication.class, args);
	}

}
