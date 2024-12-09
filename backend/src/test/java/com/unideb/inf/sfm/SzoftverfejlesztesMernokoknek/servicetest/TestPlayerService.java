//package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.servicetest;
//
//
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.PlayerMapper;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.*;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EGender;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.AuthServiceUtils;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
//import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.PlayerServiceUtils;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TestPlayerService {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestPlayerService.class);
//
//    @Mock
//    private PlayerServiceUtils playerServiceUtils;
//
//    @Mock
//    private PlayerMapper playerMapper;
//
//    @Mock
//    private AuthServiceUtils authServiceUtils;
//
//    @Mock
//    private NationalityRepository nationalityRepository;
//
//    @Mock
//    private PlayerRepository playerRepository;
//
//    @Mock
//    private EventServiceUtils eventServiceUtils;
//
//    @Mock
//    private EventRepository eventRepository;
//
//    @Mock
//    private Team team;
//
//    @InjectMocks
//    private PlayerService playerService;
//
//    private Player mockPlayer;
//    private PlayerDTO mockPlayerDTO;
//    private User mockUser;
//
//    @BeforeAll
//    static void setupBeforeAll() {
//        logger.info("Starting all tests in PlayerServiceTest...");
//    }
//
//    @BeforeEach
//    public void setUp(TestInfo testInfo) {
//        logger.info("Starting test: {}", testInfo.getDisplayName());
//
//        MockitoAnnotations.openMocks(this);
//
//        mockUser = User.builder()
//                .username("user")
//                .build();
//
//        mockPlayer = Player.builder()
//                .id(1L)
//                .firstName("Jane")
//                .lastName("Doe")
//                .nickName("janedoe")
//                .ingameRole(EIngameRoles.IGL)
//                .dateOfBirth(LocalDate.of(2000, 9, 12))
//                .gender(EGender.FEMALE)
//                .user(mockUser)
//                .build();
//
//        mockPlayerDTO = new PlayerDTO();
//        mockPlayerDTO.setFirstName("Jane");
//        mockPlayerDTO.setLastName("Doe");
//        mockPlayerDTO.setNickName("janedoe");
//        mockPlayerDTO.setIngameRole(EIngameRoles.IGL);
//        mockPlayerDTO.setDateOfBirth(LocalDate.of(2000, 9, 12));
//        mockPlayerDTO.setGender(EGender.FEMALE);
//        mockPlayerDTO.setUsername("user");
//    }
//
//    @AfterAll
//    static void teardownAfterAll() {
//        logger.info("All tests in PlayerServiceTest completed.");
//    }
//
//    @AfterEach
//    void teardownAfterEach(TestInfo testInfo) {
//        logger.info("Test '{}' completed.", testInfo.getDisplayName());
//    }
//
//    // ---------------------------------------------------------------------------
//
//    @Test
//    public void getPlayerByNickName_ExistingNickName_ReturnsPlayerDTO() {
//        // arrange
//        String nickName = "janedoe";
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(mockPlayer);
//        when(playerMapper.toDTO(mockPlayer)).thenReturn(mockPlayerDTO);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByNickName(nickName);
//
//        // assert
//        assertNotNull(result);
//        assertEquals(nickName, result.getNickName());
//
//        verify(playerServiceUtils, times(1)).findByNickName(nickName);
//        verify(playerMapper, times(1)).toDTO(mockPlayer);
//    }
//
//    @Test
//    public void getPlayerByNickName_NonExistingNickName_ReturnsNull() {
//        // arrange
//        String nickName = "nonexistent";
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(null);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByNickName(nickName);
//
//        // assert
//        assertNull(result);
//        verify(playerServiceUtils).findByNickName(nickName);
//        verify(playerServiceUtils, times(1)).findByNickName(nickName);
//    }
//
//    @Test
//    public void getPlayerByNickName_BlankNickName_ReturnsNull() {
//        // arrange
//        String nickName = "";
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(null);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByNickName(nickName);
//
//        // assert
//        assertNull(result);
//        verify(playerServiceUtils).findByNickName(nickName);
//        verify(playerServiceUtils, times(1)).findByNickName(nickName);
//    }
//
//    // ---------------------------------------------------------------------------
//
//    @Test
//    public void getPlayerByUsername_ExistingUsername_ReturnsPlayerDTO() {
//        // arrange
//        String username = "user";
//
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(mockUser);
//        when(playerServiceUtils.findByUser(mockUser)).thenReturn(mockPlayer);
//        when(playerMapper.toDTO(mockPlayer)).thenReturn(mockPlayerDTO);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByUsername(username);
//
//        // assert
//        assertNotNull(result);
//        assertEquals(mockPlayerDTO, result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).findByUser(mockUser);
//        verify(playerMapper, times(1)).toDTO(mockPlayer);
//    }
//
//    @Test
//    public void getPlayerByUsername_NonExistingUsername_ReturnsNull() {
//        // arrange
//        String username = "nonexistent";
//
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(null);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByUsername(username);
//
//        // assert
//        assertNull(result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).findByUser(null);
//        verify(playerMapper, times(1)).toDTO(null);
//    }
//
//    @Test
//    public void getPlayerByUsername_BlankUsername_ReturnsNull() {
//        // arrange
//        String username = "";
//
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(null);
//
//        // act
//        PlayerDTO result = playerService.getPlayerByUsername(username);
//
//        // assert
//        assertNull(result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).findByUser(null);
//        verify(playerMapper, times(1)).toDTO(null);
//    }
//
//    // ---------------------------------------------------------------------------
//
//    @Test
//    public void addPlayer_ValidConditions_ReturnsSavedPlayer() {
//        // arrange
//        String username = "testUser";
//        Player player = new Player();
//        Nationality nationality = new Nationality(2L, "ValidCountry", "ValidDemonym", "VD");
//        Team team = new Team();
//        User user = new User();
//        user.setUsername(username);
//
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(user);
//        when(playerServiceUtils.hasRegisteredPlayer(user)).thenReturn(false);
//        when(playerServiceUtils.getTeamByPlayer(player)).thenReturn(team);
//        when(playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam())).thenReturn(false);
//        when(playerServiceUtils.isRoleTaken(player, team)).thenReturn(false);
//        when(nationalityRepository.findByDemonym("ValidDemonym")).thenReturn(Optional.of(nationality));
//        when(playerRepository.save(player)).thenReturn(player);
//
//        player.setNationality(nationality);
//
//        // act
//        Player result = playerService.addPlayer(player, username);
//
//        // assert
//        assertNotNull(result);
//        assertEquals(player, result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).getTeamByPlayer(player);
//        verify(nationalityRepository, times(1)).findByDemonym("ValidDemonym");
//        verify(playerRepository, times(1)).save(player);
//    }
//
//    @Test
//    public void addPlayer_UserAlreadyHasPlayer_ReturnsNull() {
//        // arrange
//        String username = "testUser";
//        Player player = new Player();
//
//        User user = new User();
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(user);
//        when(playerServiceUtils.hasRegisteredPlayer(user)).thenReturn(true);
//
//        // act
//        Player result = playerService.addPlayer(player, username);
//
//        // assert
//        assertNull(result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).hasRegisteredPlayer(user);
//    }
//
//    @Test
//    public void addPlayer_NotAllowedToJoinTeamOrRoleTaken_ReturnsNull() {
//        // arrange
//        String username = "testUser";
//        Player player = new Player();
//        Team team = new Team();
//
//        User user = new User();
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(user);
//        when(playerServiceUtils.getTeamByPlayer(player)).thenReturn(team);
//        when(playerServiceUtils.hasRegisteredPlayer(user)).thenReturn(false);
//        when(playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam())).thenReturn(true);
//
//        // act
//        Player result = playerService.addPlayer(player, username);
//
//        // assert
//        assertNull(result);
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(playerServiceUtils, times(1)).getTeamByPlayer(player);
//        verify(playerServiceUtils, times(1)).isNotAllowedToJoinTeam(team.getPlayersInTeam());
//    }
//
//    @Test
//    public void addPlayer_NationalityNotFound_ThrowsException() {
//        // arrange
//        String username = "testUser";
//        Player player = new Player();
//        player.setNationality(new Nationality(2L, "UnknownCountry", "UnknownDemonym", "UD"));
//
//        User user = new User();
//        when(authServiceUtils.findUserByUsername(username)).thenReturn(user);
//        when(playerServiceUtils.hasRegisteredPlayer(user)).thenReturn(false);
//        when(playerServiceUtils.getTeamByPlayer(player)).thenReturn(null);
//        when(nationalityRepository.findByDemonym("UnknownDemonym")).thenReturn(Optional.empty());
//
//        // act & assert
//        assertThrows(IllegalArgumentException.class, () -> playerService.addPlayer(player, username));
//        verify(authServiceUtils, times(1)).findUserByUsername(username);
//        verify(nationalityRepository, times(1)).findByDemonym("UnknownDemonym");
//    }
//
//    // ---------------------------------------------------------------------------
//
//    @Test
//    public void addPlayerToEvent_ValidInputs_VerifyRepositoryInteractions() {
//        // arrange
//        String nickName = "testPlayer";
//        String eventName = "testEvent";
//
//        Player player = new Player();
//        Event event = new Event();
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(player);
//        when(eventServiceUtils.findByEventName(eventName)).thenReturn(event);
//
//        // act
//        playerService.addPlayerToEvent(nickName, eventName);
//
//        // assert
//        verify(eventRepository, times(1)).save(event);
//        verify(playerRepository, times(1)).save(player);
//    }
//
//    @Test
//    public void addPlayerToEvent_ValidInputs_AddsPlayerToEvent() {
//        // arrange
//        String nickName = "testPlayer";
//        String eventName = "testEvent";
//
//        Player player = new Player();
//        Event event = new Event();
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(player);
//        when(eventServiceUtils.findByEventName(eventName)).thenReturn(event);
//        when(eventRepository.save(event)).thenReturn(event);
//        when(playerRepository.save(player)).thenReturn(player);
//
//        // act
//        String result = playerService.addPlayerToEvent(nickName, eventName);
//
//        // assert
//        assertEquals("Player added to event successfully.", result);
//        assertTrue(event.getPlayers().contains(player));
//        assertTrue(player.getEvents().contains(event));
//        verify(playerServiceUtils, times(1)).findByNickName(nickName);
//        verify(eventServiceUtils, times(1)).findByEventName(eventName);
//        verify(eventRepository, times(1)).save(event);
//        verify(playerRepository, times(1)).save(player);
//    }
//
//    // ---------------------------------------------------------------------------
//
//    @Test
//    void updatePlayer_SuccessfulUpdate() {
//        // arrange
//        String nickName = "existingPlayer";
//        Player existingPlayer = new Player();
//        existingPlayer.setId(1L);
//        existingPlayer.setNickName(nickName);
//
//        Player updatedPlayer = new Player();
//        updatedPlayer.setNickName("updatedPlayer");
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(existingPlayer);
//        when(playerServiceUtils.getTeamByPlayer(existingPlayer)).thenReturn(null);
//        when(playerRepository.save(existingPlayer)).thenReturn(existingPlayer);
//
//        // act
//        Player result = playerService.updatePlayer(nickName, updatedPlayer);
//
//        // assert
//        assertNotNull(result);
//        assertEquals("updatedPlayer", result.getNickName());
//        verify(playerServiceUtils, times(1)).findByNickName(nickName);
//        verify(playerRepository, times(1)).save(existingPlayer);
//    }
//
//    @Test
//    void updatePlayer_TeamPresent_AllowedToJoin() {
//        // arrange
//        String nickName = "playerWithTeam";
//        Player existingPlayer = new Player();
//        existingPlayer.setId(1L);
//        existingPlayer.setNickName(nickName);
//
//        Player updatedPlayer = new Player();
//        updatedPlayer.setNickName("updatedPlayer");
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(existingPlayer);
//        when(playerServiceUtils.getTeamByPlayer(existingPlayer)).thenReturn(team);
//        when(playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam())).thenReturn(false);
//        when(playerServiceUtils.isRoleTaken(existingPlayer, team)).thenReturn(false);
//        when(playerRepository.save(existingPlayer)).thenReturn(existingPlayer);
//
//        // act
//        Player result = playerService.updatePlayer(nickName, updatedPlayer);
//
//        // assert
//        assertNotNull(result);
//        assertEquals("updatedPlayer", result.getNickName());
//        verify(playerServiceUtils, times(1)).getTeamByPlayer(existingPlayer);
//        verify(playerRepository, times(1)).save(existingPlayer);
//    }
//
//    @Test
//    void updatePlayer_TeamPresent_NotAllowedToJoin() {
//        // arrange
//        String nickName = "playerNotAllowed";
//        Player existingPlayer = new Player();
//        existingPlayer.setId(1L);
//        existingPlayer.setNickName(nickName);
//
//        Player updatedPlayer = new Player();
//        updatedPlayer.setNickName("updatedPlayer");
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(existingPlayer);
//        when(playerServiceUtils.getTeamByPlayer(existingPlayer)).thenReturn(team);
//        when(playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam())).thenReturn(true);
//
//        // act
//        Player result = playerService.updatePlayer(nickName, updatedPlayer);
//
//        // assert
//        assertNull(result);
//        verify(playerRepository, never()).save(existingPlayer);
//    }
//
//    @Test
//    void updatePlayer_TeamPresent_RoleConflict() {
//        // arrange
//        String nickName = "playerWithRoleConflict";
//        Player existingPlayer = new Player();
//        existingPlayer.setId(1L);
//        existingPlayer.setNickName(nickName);
//
//        Player updatedPlayer = new Player();
//        updatedPlayer.setNickName("updatedPlayer");
//
//        when(playerServiceUtils.findByNickName(nickName)).thenReturn(existingPlayer);
//        when(playerServiceUtils.getTeamByPlayer(existingPlayer)).thenReturn(team);
//        when(playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam())).thenReturn(false);
//        when(playerServiceUtils.isRoleTaken(existingPlayer, team)).thenReturn(true);
//
//        // act
//        Player result = playerService.updatePlayer(nickName, updatedPlayer);
//
//        // assert
//        assertNull(result);
//        verify(playerRepository, never()).save(existingPlayer);
//    }
//}
