package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.DuplicateRoleException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.PlayerAlreadyExistsException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.TeamLimitExceededException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Nationality;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.User;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TestPlayerServiceUtils {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private NationalityRepository nationalityRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerServiceUtils playerServiceUtils;

    private Player mockPlayer;
    private Team mockTeam;
    private Nationality mockNationality;
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockPlayer = new Player();
        mockPlayer.setId(1L);
        mockPlayer.setNickName("TestPlayer");

        mockTeam = new Team();
        mockTeam.setTeamName("TestTeam");

        mockNationality = new Nationality();
        mockNationality.setCountryName("TestCountry");

        mockUser = new User();
        mockUser.setPlayer(mockPlayer);
    }

    @Test
    void findById_PlayerExists_ReturnsPlayer() {
        // arrange
        when(playerRepository.findById(1L)).thenReturn(Optional.of(mockPlayer));

        // act
        Player result = playerServiceUtils.findById(1L);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void findById_PlayerDoesNotExist_ThrowsException() {
        // arrange
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> playerServiceUtils.findById(1L));
        assertEquals("Player not found with id number 1", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void findByNickName_PlayerExists_ReturnsPlayer() {
        // arrange
        when(playerRepository.findByNickName("TestPlayer")).thenReturn(Optional.of(mockPlayer));

        // act
        Player result = playerServiceUtils.findByNickName("TestPlayer");

        // assert
        assertNotNull(result);
        assertEquals("TestPlayer", result.getNickName());
        verify(playerRepository, times(1)).findByNickName("TestPlayer");
    }

    @Test
    void findByNickName_PlayerDoesNotExist_ThrowsException() {
        // arrange
        when(playerRepository.findByNickName("TestPlayer")).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> playerServiceUtils.findByNickName("TestPlayer"));
        assertEquals("Player not found with id number -1", exception.getMessage());
        verify(playerRepository, times(1)).findByNickName("TestPlayer");
    }

    @Test
    void findByUser_UserHasPlayer_ReturnsPlayer() {
        // arrange
        User mockUser = new User();
        mockUser.setPlayer(mockPlayer);
        when(playerRepository.findByUser(mockUser)).thenReturn(Optional.of(mockPlayer));

        // act
        Player result = playerServiceUtils.findByUser(mockUser);

        // assert
        assertNotNull(result);
        assertEquals("TestPlayer", result.getNickName());
        verify(playerRepository, times(1)).findByUser(mockUser);
    }

    @Test
    void findByUser_UserDoesNotHavePlayer_ThrowsException() {
        // arrange
        User mockUser = new User();
        when(playerRepository.findByUser(mockUser)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> playerServiceUtils.findByUser(mockUser));
        assertEquals("Player not found with id number -1", exception.getMessage());
        verify(playerRepository, times(1)).findByUser(mockUser);
    }

    @Test
    void findByCountryName_NationalityExists_ReturnsNationality() {
        // arrange
        when(nationalityRepository.findByCountryName("TestCountry")).thenReturn(Optional.of(mockNationality));

        // act
        Nationality result = playerServiceUtils.findByCountryName("TestCountry");

        // assert
        assertNotNull(result);
        assertEquals("TestCountry", result.getCountryName());
        verify(nationalityRepository, times(1)).findByCountryName("TestCountry");
    }

    @Test
    void findByCountryName_NationalityDoesNotExist_ThrowsException() {
        // arrange
        when(nationalityRepository.findByCountryName("TestCountry")).thenReturn(Optional.empty());

        // act & assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> playerServiceUtils.findByCountryName("TestCountry"));
        assertEquals("Nationality not found", exception.getMessage());
        verify(nationalityRepository, times(1)).findByCountryName("TestCountry");
    }

    @Test
    void isAllowedToJoinTeam_ValidTeamSize_ReturnsTrue() {
        // arrange
        List<Player> players = List.of(mockPlayer);

        // act
        boolean result = playerServiceUtils.isAllowedToJoinTeam(players);

        // assert
        assertTrue(result);
    }

    @Test
    void isAllowedToJoinTeam_ExceedsTeamSize_ThrowsException() {
        // arrange
        List<Player> players = List.of(new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player());

        // act & assert
        TeamLimitExceededException exception = assertThrows(TeamLimitExceededException.class, () -> playerServiceUtils.isAllowedToJoinTeam(players));
        assertEquals("Only 6 members are allowed to join each team.", exception.getMessage());
    }

    @Test
    void getTeamByPlayer_PlayerHasTeam_ReturnsTeam() {
        // arrange
        mockPlayer.setTeam(mockTeam);
        when(teamRepository.findByTeamName("TestTeam")).thenReturn(Optional.of(mockTeam));

        // act
        Team result = playerServiceUtils.getTeamByPlayer(mockPlayer);

        // assert
        assertNotNull(result);
        assertEquals("TestTeam", result.getTeamName());
        verify(teamRepository, times(1)).findByTeamName("TestTeam");
    }

    @Test
    void getTeamByPlayer_PlayerHasNoTeam_ReturnsNull() {
        // arrange
        mockPlayer.setTeam(null);

        // act
        Team result = playerServiceUtils.getTeamByPlayer(mockPlayer);

        // assert
        assertNull(result);
        verify(teamRepository, never()).findByTeamName(anyString());
    }

    @Test
    void hasRegisteredPlayer_UserHasPlayer_ThrowsException() {
        // arrange
        mockUser.setPlayer(mockPlayer);

        // act & assert
        PlayerAlreadyExistsException exception = assertThrows(PlayerAlreadyExistsException.class, () -> playerServiceUtils.hasRegisteredPlayer(mockUser));
        assertEquals("User already has a player assigned.", exception.getMessage());
    }

    @Test
    void hasRegisteredPlayer_UserDoesNotHavePlayer_ReturnsFalse() {
        // arrange
        mockUser.setPlayer(null);

        // act
        boolean result = playerServiceUtils.hasRegisteredPlayer(mockUser);

        // assert
        assertFalse(result);
    }

    @Test
    void isRoleTaken_RoleNotTaken_ReturnsFalse() {
        // arrange
        mockPlayer.setIngameRole(EIngameRoles.IGL);
        mockTeam.getPlayersInTeam().clear();

        // act
        boolean result = playerServiceUtils.isRoleTaken(mockPlayer, mockTeam);

        // assert
        assertFalse(result);
    }

    @Test
    void isRoleTaken_RoleTaken_ThrowsException() {
        // arrange
        Player anotherPlayer = new Player();
        anotherPlayer.setId(2L);
        anotherPlayer.setIngameRole(EIngameRoles.IGL);
        mockTeam.getPlayersInTeam().add(anotherPlayer);
        mockPlayer.setIngameRole(EIngameRoles.IGL);

        // act & assert
        DuplicateRoleException exception = assertThrows(DuplicateRoleException.class, () -> playerServiceUtils.isRoleTaken(mockPlayer, mockTeam));
        assertEquals("This role is already taken.", exception.getMessage());
    }
}
