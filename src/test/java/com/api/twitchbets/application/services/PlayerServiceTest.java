package com.api.twitchbets.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.api.twitchbets.domain.factories.PlayerFactory;
import com.api.twitchbets.domain.player.Player;
import com.api.twitchbets.domain.player.PlayerRepository;
import com.api.twitchbets.application.utilities.CustomPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    private static final String VALID_USERNAME = "username";
    private static final String VALID_PASSWORD = "password";

    @Autowired
    private PlayerService playerService;
    @MockitoBean
    private PlayerRepository playerRepository;
    @MockitoBean
    private PlayerFactory playerFactory;
    @MockitoBean
    private Player player;
    @MockitoBean
    private CustomPasswordEncoder customPasswordEncoder;


    @Test
    void whenCreatePlayer_thenEncodePasswordCreateAndSaveNewPlayer() {
        String encodedPassword = "encodedPassword";
        when(customPasswordEncoder.encode(VALID_PASSWORD)).thenReturn(encodedPassword);
        when(playerFactory.createNormalPlayer(VALID_USERNAME, encodedPassword)).thenReturn(player);

        playerService.createPlayer(VALID_USERNAME, VALID_PASSWORD);

        InOrder inOrder = inOrder(customPasswordEncoder, playerFactory, playerRepository);
        inOrder.verify(customPasswordEncoder).encode(VALID_PASSWORD);
        inOrder.verify(playerFactory).createNormalPlayer(VALID_USERNAME, encodedPassword);
        inOrder.verify(playerRepository).addPlayer(player);
    }

    @Test
    void whenGetPlayer_thenReturnPlayer() {
        when(playerRepository.getPlayer(VALID_USERNAME)).thenReturn(player);

        Player returnedUser = playerService.getPlayer(VALID_USERNAME);

        verify(playerRepository).getPlayer(VALID_USERNAME);
        assertEquals(player, returnedUser);
    }

    @Test
    void givenValidAmount_whenChargePlayer_thenChargePlayerAndSave() {
        final float VALID_AMOUNT = 10;
        when(playerRepository.getPlayer(VALID_USERNAME)).thenReturn(player);

        playerService.chargePlayer(VALID_USERNAME, VALID_AMOUNT);

        InOrder inOrder = inOrder(playerRepository, player);
        inOrder.verify(playerRepository).getPlayer(VALID_USERNAME);
        inOrder.verify(player).charge(VALID_AMOUNT);
        inOrder.verify(playerRepository).updatePlayer(player);
    }

    @Test
    void givenValidAmount_whenCheckIfCanPlaceBet_thenDoNothing() {
        final float VALID_AMOUNT = 10;
        when(playerRepository.getPlayer(VALID_USERNAME)).thenReturn(player);

        playerService.checkIfCanPlaceBet(VALID_USERNAME, VALID_AMOUNT);

        verify(playerRepository).getPlayer(VALID_USERNAME);
        verify(player).checkIfCanPlaceBet(VALID_AMOUNT);
    }

    @Test
    void givenInvalidAmount_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {
        final float INVALID_AMOUNT = -1;
        when(playerRepository.getPlayer(VALID_USERNAME)).thenReturn(player);
        doThrow(new IllegalArgumentException()).when(player).checkIfCanPlaceBet(INVALID_AMOUNT);

        assertThrows(IllegalArgumentException.class, () -> playerService.checkIfCanPlaceBet(VALID_USERNAME, INVALID_AMOUNT));
    }
}