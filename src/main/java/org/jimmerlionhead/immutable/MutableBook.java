package org.jimmerlionhead.immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MutableBook {
    private String name;
    private int edition;
    private double price;
    private String description;

}
