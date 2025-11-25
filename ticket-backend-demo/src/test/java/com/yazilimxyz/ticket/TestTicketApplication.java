package com.yazilimxyz.ticket;

import org.springframework.boot.SpringApplication;

import com.yazilimxyz.ticket.TicketApplication;

public class TestTicketApplication {

	public static void main(String[] args) {
		SpringApplication.from(TicketApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
