package co.jlv.livrokaz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.repository.GendleRepository;

@Service
public class GendleServiceImpl implements GendleService {



    private GendleRepository gendleRepo;

    public GendleServiceImpl(GendleRepository gendleRepository) {
        this.gendleRepo = gendleRepository;
    }


    public List<Gendle> getAllGendles() {
   
        return this.gendleRepo.findAll();
    }
}