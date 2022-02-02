package es.gortizlavado.league.app.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@ExtendWith(MockitoExtension.class)
class AgeMapperTest {

    @InjectMocks
    AgeMapper ageMapper;

    @Mock
    Clock clock;

    @Test
    void shouldReturnAgeFromBirthday() {
        Mockito.when(clock.instant()).thenReturn(Instant.parse("2021-01-13T00:00:00.00Z"));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        Assertions.assertEquals(35, ageMapper.calculateFrom(LocalDate.of(1986, 1, 12)));

        Assertions.assertEquals(35, ageMapper.calculateFrom(LocalDate.of(1986, 1, 13)));

        Assertions.assertEquals(34, ageMapper.calculateFrom(LocalDate.of(1986, 1, 14)));
    }
}