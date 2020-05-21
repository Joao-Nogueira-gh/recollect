package ua.tqs.ReCollect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository rcRepo;

    @InjectMocks
    private LocationService sutRCservice;
    
    @Test
    public void getLocationForAveiro_returnsAveiro() {

        given(rcRepo.findByDistrictAndCounty("Aveiro", "Aveiro")).willReturn(new Location("Aveiro", "Aveiro"));

        Location expected = new Location("Aveiro", "Aveiro");

        Location actual = sutRCservice.getLocation("Aveiro", "Aveiro");

        assertEquals(expected, actual, "Locations dont match");

    }
    
}