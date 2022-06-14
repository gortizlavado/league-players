package es.gortizlavado.league.app.entity.hibernate.generator;

import es.gortizlavado.league.app.entity.Player;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class UseIdOrGenerate extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        if (obj == null) throw new HibernateException("trying to generate when object is null");

        if ((((Player) obj).getId()) != null) {
            return ((Player) obj).getId();
        } else {
            return super.generate(s, obj);
        }
    }
}
