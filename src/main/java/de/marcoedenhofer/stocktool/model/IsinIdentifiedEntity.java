package de.marcoedenhofer.stocktool.model;

import org.springframework.lang.NonNull;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * This is a generic Entity to derive from, whenever a ISIN shall be used as identifier.
 */
@MappedSuperclass
public abstract class IsinIdentifiedEntity implements Serializable {
    @Id
    @NonNull
    private String isin;

    @NonNull
    public String getIsin() {
        return isin;
    }

    public void setIsin(@NonNull String isin) {
        this.isin = isin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsinIdentifiedEntity that = (IsinIdentifiedEntity) o;
        return getIsin().equals(that.getIsin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsin());
    }
}
