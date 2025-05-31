package org.bebra.enums;

/**
 * @author max_pri
 */
public enum BetStatus {
    WIN("win"),
    LOSE("lose"),
    TBD("tbd");

    public final String value;

    BetStatus(String value) {
        this.value = value;
    }
}
