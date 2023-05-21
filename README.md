# SecondTextGame
A much more advanced, open-ended text game.

The primary focus of this game was to make it easily expandable. to add a new item, area, or being you just need to write one line in a .txt file.
Syntax for adding things:

Map locations:
x-position * 10 + y-position  a_short_description.;DirectionsToGo { #ofItems ItemIDsListed } #ofbeings BeingIDsListed     //DirectionsToGo explained: the last four binary digits represent if you can travel north, east, south, or west respectively. 1 means you can go that way, 0 means you can't.
  Example:
  4950	A cobblestone hallway.;12 { 1 2 } 1 2     //is at x = 49, y = 50. can travel north or east (12 decimal = 1100 binary). There is one item here with an id of 2, there is one being with an id of 2. (if you wanted to have more items or beings, they must be in a space-separated list like so: { 2 6 7 } (two items with ids 6 & 7))

Items:
ItemID <Item name> weight type durability equipslot effectiveness     //type explained: 1 - key. 2 - weapon. 3 - armour. equipslot explained: -1:can't equip 0:head 1:torso 2:legs 3:feet 4:hands 5:weapon (NOTE: right now, keys serve no purpose)
  Examples:
  3 <worn leather boots> 8 3 35 3 5     //weighs 8, is armour, has 35 durability, equips to feet, has 5 defence.
  7 <shiny dagger> 3 2 75 5 7     //weighs 3, is a weapon, has 75 durability, equips to weapon, has 7 damage.

Beings:
BeingID <Name>health {#ofPossibleItems ChanceOf1BeingAdded ItemID1 ChanceOf2BeingAdded ItemID2 ...}
  Example:
  1 <old prisoner>3 {2 100 5 50 4}      //Has 3 health, two possible items. a 100% chance of getting itemID 5, and a 50% chance of getting itemID 4
  
What you can do:
-Create new items, locations, and beings.
-Pick up, equip, unequip, and drop items anywhere.
-Fight enemies equiped with a weapon (enemies have the defence and attack of the items they use).
-Search Beings and take their items.
-walk around different areas.
-list what you currently hold (shows your current weight, each item's weight, and what you have equipped.)

What I would like to add:
-A simple saving and loading system. (will write the player's name, item count, item information, explored tile information, item and being contained in explored tiles all with the same syntax as shown above.)
-A way to speak to beings. (making use of the dialogue system)
-An aggravation system. (beings that are passive until you attack)
-A simple healing spell that the player and some enemies can use. (possibly more spells with lasting effects)
-Making keys functional (like making a key needed to move west in the first room)
