# SwitchControl

1. At startup, the application checks if there is a list of switches in the device database.
If there is - displays on the screen. 
If not (first run) - loads via api, saves to database and displays on screen.

2. The user can switch the lamps on/off by pressing the button in the corresponding switch line.
Changes are automatically sent to the server, saved in the database and displayed.

3. The status of the switch is displayed in the switch card.
The user can update (swipe down) the list of switches with settings from the server on their device, 
if another user made changes or added new switches.

4. When you click on the settings icon in the switch card, the brightness settings activity starts.
Each active channel has its own settings widget.
You can also change the name of the switch on this screen.
By pressing the save button new settings are sent to the server and saved in the device database.

5. When the application starts, it sends the saved settings to the server.
