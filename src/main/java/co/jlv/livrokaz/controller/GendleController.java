package co.jlv.livrokaz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.services.GendleService;

@RestController
@RequestMapping("/livrokaz")
@CrossOrigin("http://localhost:4200")
public class GendleController {
	private GendleService gendleService;
	
	@Autowired
	private GendleRepository gendleRepo;
	
	
	
	
	public GendleController(GendleService gendleService) {
		this.gendleService = gendleService;
	}
	
	@GetMapping("/gendles")
	public List<Gendle> getAllGendles() {
		return this.gendleService.getAllGendles();

	}
}
