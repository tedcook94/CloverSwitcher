# CloverSwitcher
An app to quickly switch the default boot volume for Clover bootloader

# Status: Functional
As of now, the application is fully working if you are willing to build from source. I hope to spend some time finding the best way to package the application into native programs for each OS so that installing JRE and building a JAR won't be necessary.

# TO-DO:
 * Add persistence of boot entries &#10003;
 * Add EFI partition mounting &#10003;
    * Windows &#10003;
    * macOS &#10003;
    * Linux &#10003;
 * Add plist editing functionality &#10003;
    * Windows &#10003;
    * macOS &#10003;
    * Linux &#10003;
 * Package releases
    * Windows
    * macOS
    * Linux
 * (Possibly) improve permission escalaltion strategies
    * Windows
    * macOS
    * Linux
  
# Things to note about using Clover Switcher:
 * Running on Windows requires running the application as Administrator to mount/edit Clover config
 * Using on macOS or Linux will prompt for a root password multiple times during mounting/config editing
 * Has been tested on Windows 10, macOS Mojave and Ubuntu 16.04. If you are able to successfully use Clover Switcher on a different platform than these, let me know and I'll update the list!
