package de.marcoedenhofer.stocktool.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * This is a generic Entity to derive from, whenever a ISIN shall be used as identifier.
 */
@MappedSuperclass
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class IsinIdentifiedEntity implements Serializable {
    @Id
    @NonNull
    private String isin;
}
