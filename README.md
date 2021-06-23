# Plane Sailing Server

Server-side code for Plain/Sailing version 2.

Currently under construction - this does not do anything very useful yet, and the Plane/Sailing web interface does not talk to it.

## To Do List

* Move the rest of the "get description" and similar functionality over from Plane/Sailing v1
* Xstream based serialisation for persistence
* Make ports and IP addresses configurable
* Add loading of base station, airport and seaport data from JSON config file at runtime
* Implement APRS Receive via Direwolf - client to host:8000
* Refactor TCP client to make common between SBS and APRS
* Switch AIS to TCP client too to match? Requires new rtl_ais compile from source
* Implement JSON web server - Ensure server time is included in the JSON response so an accurate age of position data can be determined regardless of client's clock