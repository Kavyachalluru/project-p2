package com.example.p2.Favourite_Services;

import com.example.p2.Favourite_Services.controller.FavouriteController;
import com.example.p2.Favourite_Services.entity.Favourite;
import com.example.p2.Favourite_Services.service.FavouriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FavouriteControllerTest {

    @Autowired
    private FavouriteController favouriteController;

    @MockBean
    private FavouriteService favouriteService;

    private Favourite favourite1;
    private Favourite favourite2;

    @BeforeEach
    public void setup() {
        favourite1 = new Favourite();
        favourite1.setId(1L);
        favourite1.setBuyerId(101L);
        favourite1.setProductId(1001L);

        favourite2 = new Favourite();
        favourite2.setId(2L);
        favourite2.setBuyerId(101L);
        favourite2.setProductId(1002L);
    }

    @Test
    public void testGetFavouritesByBuyer_Success() {
        List<Favourite> favourites = Arrays.asList(favourite1, favourite2);
        Mockito.when(favouriteService.getFavouritesByBuyer(101L)).thenReturn(favourites);

        ResponseEntity<List<Favourite>> response = favouriteController.getFavouritesByBuyer(101L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(favouriteService, times(1)).getFavouritesByBuyer(101L);
    }

    @Test
    public void testGetFavouritesByBuyer_NotFound() {
        Mockito.when(favouriteService.getFavouritesByBuyer(101L)).thenReturn(Arrays.asList());

        ResponseEntity<List<Favourite>> response = favouriteController.getFavouritesByBuyer(101L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(favouriteService, times(1)).getFavouritesByBuyer(101L);
    }

    @Test
    public void testAddFavourite_Success() {
        doNothing().when(favouriteService).addFavourite(101L, 1001L);

        ResponseEntity<String> response = favouriteController.addFavourite(101L, 1001L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Favourite added successfully!", response.getBody());
        verify(favouriteService, times(1)).addFavourite(101L, 1001L);
    }

    @Test
    public void testAddFavourite_Failure() {
        doThrow(new RuntimeException("Failed to add favourite")).when(favouriteService).addFavourite(101L, 1001L);

        ResponseEntity<String> response = favouriteController.addFavourite(101L, 1001L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add favourite.", response.getBody());
        verify(favouriteService, times(1)).addFavourite(101L, 1001L);
    }

    @Test
    public void testRemoveFavourite_Success() {
        Mockito.when(favouriteService.getFavouriteById(1L)).thenReturn(Optional.of(favourite1));
        doNothing().when(favouriteService).removeFavourite(1L);

        ResponseEntity<String> response = favouriteController.removeFavourite(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Favourite removed successfully!", response.getBody());
        verify(favouriteService, times(1)).getFavouriteById(1L);
        verify(favouriteService, times(1)).removeFavourite(1L);
    }

    @Test
    public void testRemoveFavourite_NotFound() {
        Mockito.when(favouriteService.getFavouriteById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = favouriteController.removeFavourite(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Favourite not found.", response.getBody());
        verify(favouriteService, times(1)).getFavouriteById(1L);
        verify(favouriteService, times(0)).removeFavourite(1L);
    }
}