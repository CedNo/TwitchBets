package com.api.twitchbets.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.api.twitchbets.domain.factories.PlayerFactory;
import com.api.twitchbets.domain.user.Player;
import com.api.twitchbets.domain.user.PlayerAttributesValidator;
import com.api.twitchbets.domain.user.PlayerRepository;

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

    @Autowired
    private PlayerService playerService;
    @MockitoBean
    private PlayerRepository playerRepository;
    @MockitoBean
    private PlayerFactory playerFactory;
    @MockitoBean
    private PlayerAttributesValidator playerAttributesValidator;
    @MockitoBean
    private Player player;

    @Test
    void whenCreatePlayer_thenValidateCreateAndSaveNewPlayer() {
        when(playerFactory.createPlayer(VALID_USERNAME)).thenReturn(player);

        playerService.createPlayer(VALID_USERNAME);

        InOrder inOrder = inOrder(playerAttributesValidator, playerFactory, playerRepository);
        inOrder.verify(playerAttributesValidator).validate(VALID_USERNAME);
        inOrder.verify(playerFactory).createPlayer(VALID_USERNAME);
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