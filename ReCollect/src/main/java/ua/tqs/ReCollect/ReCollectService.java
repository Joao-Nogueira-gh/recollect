package ua.tqs.ReCollect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReCollectService {

    @Autowired
    private ReCollectRepository rcRepo;

    public List<Product> getAll(){
        return rcRepo.findAll();
    }
    
}