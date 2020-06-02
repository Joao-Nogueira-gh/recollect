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
class LocationServiceTest {

    @Mock
    private LocationRepository locRepo;

    @InjectMocks
    private LocationService locService;
    
    @Test
    void getLocationForAveiro_returnsAveiro() {

        given(locRepo.findByDistrictAndCounty("Aveiro", "Aveiro")).willReturn(new Location("Aveiro", "Aveiro"));

        Location expected = new Location("Aveiro", "Aveiro");

        Location actual = locService.getLocation("Aveiro", "Aveiro");

        assertEquals(expected, actual, "Locations dont match");

    }
    
}