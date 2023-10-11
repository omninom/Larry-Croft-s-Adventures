package nz.ac.wgtn.swen225.lc.domain;

public interface DomainObserver {
    void handleEvent(EventType event, TileType tileType);
}

