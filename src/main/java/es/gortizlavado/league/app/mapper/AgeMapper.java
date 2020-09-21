package es.gortizlavado.league.app.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeMapper {

    private Clock clock;

    public Integer calculateFrom(LocalDate birthday) {
        if (null == birthday) {
            return null;
        }
        Period diff = Period.between(LocalDate.now(clock), birthday);
        return Math.abs(diff.getYears());
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
