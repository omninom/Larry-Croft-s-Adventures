# Larry Croft’s Adventures
A creative clone of the (first level of the) 1989 Atari game Chips Challenge.

![image](https://github.com/omninom/Larry-Croft-s-Adventures/assets/40103480/bc11e868-f40c-4e3a-89ad-120f76c768dc)

## Playing the Game
Start the game by running nz.ac.wgtn.swen225.lc.app.Main from Eclipse.
Use the arrow keys to move Larry/Chap, and use Ctrl-1 and Ctrl-2 to load the two levels.

## Levels
Levels are stored under the `src/main/resources/levels/` directory.
Level data is contained `level<number>.json` with any custom actor behaviour in a `.jar` archive of the same name.

## Implementation details
- Target Java 17 (OpenJDK)
- Gradle build tools and standard directory structure are used

## Integration Day (2023-09-27)
- [breakpoints](breakpoints.txt)
