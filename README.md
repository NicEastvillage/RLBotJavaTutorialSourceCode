# RLBot Java Tutorial Source Code

<a href="https://www.youtube.com/playlist?list=PL0Chr9HhL2DQijgJ4b4ho4pBpxFfHXP0m" style="size=2.2em">TUTORIAL</a>

This repository contains the source code for Eastvillage's RLBot Java tutorial. The tutorial covers the basics of RLBot how to make your first bot.

If you have questions, feedback, or comments on the tutorial, hit me up on Discord, `Eastvillage#2628`. You can find me on the [RLBot Discord server](https://discord.gg/bS98QEt).

***NOTE:** Please do not submit the tutorial bot to tournaments or claim it as your own unless you have made significant changes to it! ([Community guidelines](https://github.com/RLBot/RLBot/wiki/Community-Guidelines#the-fork-rule))*

**Important links:**
- [RLBot website](https://www.rlbot.org/)
- [JavaExampleBot](https://github.com/RLBot/RLBotJavaExample)
- [RLBot wiki](https://github.com/RLBot/RLBot/wiki), and notable sub-sites:
  - [Input and output](https://github.com/RLBot/RLBot/wiki/Input-and-Output-Data-(current)), i.e. the content of the GameTickPacket and the ControllerOutput
  - [Useful Game Values](https://github.com/RLBot/RLBot/wiki/Useful-Game-Values), like field dimensions, ball size, max speeds, and much more
  - [Bot appearance](https://github.com/RLBot/RLBot/wiki/Bot-Customization)
  - [Ball path prediction](https://github.com/RLBot/RLBot/wiki/Ball-Path-Prediction)
  - [Debug rendering](https://github.com/RLBot/RLBot/wiki/Rendering)
  - [Game-state manipulation](https://github.com/RLBot/RLBot/wiki/Manipulating-Game-State)
- [RLBot framework](https://github.com/RLBot/RLBot)
- [chip's math notes](https://samuelpmish.github.io/notes/RocketLeague/)
- [RLBotTraining](https://www.youtube.com/playlist?list=PL6LKXu1RlPdxh9vxmG1y2sghQwK47_gCH). Custom exercises (unit tests) for bots

## Episodes

**Episode 1** - Setup and Renaming

- Quick framework explaination
- IDE suggestion
- Downloading the [JavaExampleBot](https://github.com/RLBot/RLBotJavaExample)
- Renaming relavant files, including bot name, gradle, config, java bat path, etc
- Showing off [RLBot's wiki](https://github.com/RLBot/RLBot/wiki)

**Episode 2** - RLBot, Vec3, and Doubles

- How the bot is created and called
- ATBA/JavaExampleBot logic explanation
- Why vectors are important (and inverted coordinate system)
- Replacing `Vector3` with `Vec3`
- Fixing `CarOrientation` as a consequence of the weird coordinate system
- Insert double/float casting in the right places

**Episode 3** - DataPackets and State Machines

- Small explanation and cleanup of input and output data
- Adding bot to `DataPacket`
- Introduction to state machines
- Making abstract `State` class
- Making general `MoveToPointState` with `targetPoint` and `targetSpeed`
- Making `AtbaState` and `CollectBoostState` and switching between them

**Episode 4** - Kickoffs and Local Coordinates

- Adding `KickoffState` (no dodge)
- Making `MoveToPointState` use local coordinates to get down from wall
- Making `MoveToPointState` only boost when going towards the target

**Episode 5** - Dodges

- Talk about how RLBot works in frames/ticks and how to make sequential maneuvers
- Making a `Manuever` interface
- Making a dodge for `KickoffState` and `MoveToPointState`

**Episode 6** - Rendering and Ball Prediction

- Deep dive into the renderers
- Making our own `SmartRenderer` with more functions
- Drawing ball prediction
- Using ball prediction to find where and when to hit the ball

### Planned Episodes

*Note: These are just ideas for episodes and their order may not be exactly as below.*

**Episode ?** - Better Movement

- Introducing handbraking and turn radius (and "can to faster" option maybe?)
- Renaming `AtbaState` to `ChaseBallState` since it is not just Atba anymore

**Episode ?** - Simple Strategy and shoot maneuver

- Making it only chase the ball when on the correct side of the ball and retreat to own goal otherwise
- Adding a simple `ShotManeuver`

**Episode ?** - Utility Systems

- Explaination of utility systems and why state machines doesn't scale
- Implementing a simple utility system

**Episode ?** - Aerials

- Building aerial controller
- Making a recover manuever
- Making an aerial manuever
