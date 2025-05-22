# PumpFunDummyPlugin

![image](https://github.com/user-attachments/assets/fa97f34c-8084-43f7-84f8-5b7478a2c1df)


PumpFunDummyPlugin is a lightweight Minecraft plugin that brings pump.fun token deployment directly into your server chat.



## Features

- **Instant token deployment**  
  `/deploytoken <TICKER> <Name>`  
  Sends both the player and the entire server a message:  
  ```
  [PumpFun] <PlayerName>, your token '<TICKER>' is deployed! Check it here: https://pump.fun/coin/4FnHaqk48Q2ZzW5FewsRQsqq1N561oqvV9hBVVD7pump
  ```
- **Permission-based access**  
  By default available to all, can be restricted via the `pumpfun.deploytoken` permission.
- **Simple installation**  
  Drop the JAR into your `plugins/` folder and restart the server.

## Use Cases

- Demonstrating token deployment workflows without leaving the game.  
- Interactive tutorials or events centered around pump.fun.  
- Automating in-game announcements for newly “launched” tokens.

## Installation

1. Download the latest `PumpFunDummyPlugin.jar`.  
2. Place it into your server’s `plugins/` directory.  
3. Restart your Paper/Spigot server.  
4. (Optional) Grant permission:  
   ```
   /permission grant <player> pumpfun.deploytoken
   ```
5. Run the test command:  
   ```
   /deploytoken test test
   ```

## Quickstart

1. Install & restart.  
2. In chat, type:  
   ```
   /deploytoken test test
   ```
3. Enjoy the instant token link!

## Demo Video

Watch a quick demo on YouTube:  
https://www.youtube.com/watch?v=_2iWarK1f44
