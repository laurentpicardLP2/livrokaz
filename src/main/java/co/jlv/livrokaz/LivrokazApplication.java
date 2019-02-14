package co.jlv.livrokaz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LivrokazApplication implements CommandLineRunner {


		

	public static void main(String[] args) throws Exception, MalformedURLException, IOException, ClassNotFoundException, SQLException {
		SpringApplication.run(LivrokazApplication.class, args);
	}

	@Override
	public void run(String... args)
			throws Exception,  MalformedURLException, IOException, ClassNotFoundException, SQLException {
	}
		



}
