package com.github.leandrobove.mswallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MsWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsWalletApplication.class, args);
	}

}
