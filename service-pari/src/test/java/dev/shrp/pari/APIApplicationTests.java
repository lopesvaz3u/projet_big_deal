package dev.shrp.pari;

import dev.shrp.pari.services.*;
import dev.shrp.pari.entities.MatchPari;
import dev.shrp.pari.entities.Pari;
import dev.shrp.pari.repositories.MatchPariRepository;
import dev.shrp.pari.repositories.PariRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PariServiceTest {

    @Mock
    private PariRepository pariRepository;

    @Mock
    private MatchPariRepository matchPariRepository;

    @InjectMocks
    private PariService pariService;

    private Pari pari;
    private MatchPari matchPari;

    @BeforeEach
    void setUp() {
        pari = new Pari();
        pari.setId_parieur(1L);
        pari.setMise(30.0);
        pari.setCombine(false);

        matchPari = new MatchPari();
        matchPari.setIdMatch(100L);
        matchPari.setIdPari(1L);
        matchPari.setResultatParie("Terminé");
    }

    @Test
    void testCreatePariSimple() {
        when(pariRepository.save(any(Pari.class))).thenReturn(pari);
        when(matchPariRepository.save(any(MatchPari.class))).thenReturn(matchPari);

        Pari createdPari = pariService.createPariSimple(100L, 1L, "Terminé", 30.0);

        assertNotNull(createdPari);
        assertEquals(1L, createdPari.getId_parieur());
        assertEquals(30.0, createdPari.getMise());
        assertFalse(createdPari.isCombine());

        verify(pariRepository, times(1)).save(any(Pari.class));
        verify(matchPariRepository, times(1)).save(any(MatchPari.class));
    }
}
