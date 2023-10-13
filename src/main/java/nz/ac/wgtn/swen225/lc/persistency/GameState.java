package nz.ac.wgtn.swen225.lc.persistency;

import java.util.List;
import nz.ac.wgtn.swen225.lc.domain.Chap;
import nz.ac.wgtn.swen225.lc.domain.EnemyActor;
import nz.ac.wgtn.swen225.lc.domain.TileType;

public record GameState(
    int levelNumber,
    TileType[][] mazeTiles,
    Chap chap,
    List<EnemyActor> enemies,
    List<TileType> chapInventory,
    String infoText
) {}
