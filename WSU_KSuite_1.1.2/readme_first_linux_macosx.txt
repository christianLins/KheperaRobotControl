==========================================================================
       WSU Khepera Suite - Introduction and Quick Setup for 
                              MacOSX and Linux
                                 Spring 2007
==========================================================================

                           =======================
                           === GETTING STARTED ===
                           =======================


This archive, which you've just unarchived to your own hard drive, contains
4 executable programs.  We're going to tell you what you need to do first, how to
use these programs, and also what the rest of this stuff is.  

The programs you have here are:

	1 - The Khepera Simulator (ksim.jar)
	2 - The Khepera Remote Client (kremote.jar)
	3 - Windows Specific helper scripts
	    a) A Windows compiler script (win_compile.bat)
	    b) A Windows console opener (win_console.bat)
	4 - MacOSX and Linux Specific helper scripts
	    a) A compiler script (compile.sh)
	    b) A console opener (console.sh)

The Khepera Simulator is the program you'll use to test the controllers
you write in a simulated environment, with a simulated robot.  All you'll 
need to run the simulator is a good JDK installation (more on this later).
To run on a real robot, you'll need (obviously) a real Khepera robot and
the Khepera Remote Server OR access to someone willing to to let you connect
to their robot using the Khepera Remote Server. The Khepera Remote Client is 
the program you'll use when you want to run your controllers on a real Khepera.
Note that the simulator and the remote client are DIFFERENT programs.  You 
do not need a real robot to use the simulator.

The helper scripts can be used to get certain jobs done quickly on the specific
systems for which they are written.  We'll cover their use as needed in the 
instructions below.  
                                
                               ================
                               === CONTENTS ===
                               ================


This file contains the following sections, which you need to go through in order
to get started using the WSU Khepera Suite:

1 ...................................................... Opening a console window
2 ............................................................... Installing Java
3 ........................................................ Compiling a controller
4 ......................................................... Running the Simulator
5 ............................................................ Running the Remote
6 ................................................... Remote Client Configuration



=================================================================================
=====================                                     =======================
=====================     1 - Opening a console window    =======================
=====================                                     =======================
=================================================================================


The directory that this file is in is considered to be the 'home' directory for
the WSU Khepera Suite.  It's the directory in which you'll be doing some stuff,
like compiling your controllers, and running the Remote Client program.  Since
you'll need to do things in a console window from within this directory, we gave
you a console window opener program that'll open up a console window where you're
already in the Khepera Suite home directory.

To use this program, double-click on the icon for the console.sh (or run from a 
command line) file.  This will open a console window.  Notice that the directory 
path that's displayed at the prompt shows this directory (the last directory in 
the path will be this one, WSUKheperaSuite). 

Go ahead and close the console window now.  We'll be using it later in these
instructions.





=================================================================================
=====================                                     =======================
=====================           2 - Installing Java       =======================
=====================                                     =======================
=================================================================================

You need the Java 2 Standard Edition Software Developers' Kit, version 1.4.2 or
later.  If you already have a working Java JDK on your computer, you can skip this 
section.

(Huh?, What?)

There are 2 flavors of Java you can have installed on your computer - the JRE
and the SDK.  JRE stands for 'Java Runtime Environment,' and SDK stands for
'Software Developers' Kit.' You need the JRE to RUN Java programs, and you need
the SDK to WRITE and COMPILE them.

Most computers have the JRE installed.  Java programs are pretty common on the
web (they're called 'Applets' sometimes), and so most computers either come with
the JRE pre-installed, or your web browser might have led you through the
installation process if you didn't have it, and tried to go to a web page that
used a Java Applet.  Either way, we still need to install the SDK.


                        =================================
                        == HOW TO INSTALL THE JAVA SDK ==
                        =================================

If you are a Linux user, you're likely already capable of installing the JDK.  
Follow whatever method is appropriate for your linux distribution.  You need at
least Java 2 Standard Edition 1.4.2.  Anything newer should work too.

Apple supplies its own distribution of the JDK for MacOS X machines.  The most
simple way to get the JDK is to instal the XTools developer environment, which
is free from apple.com.  Along with the JDK, you'll get gcc/g++ and a fairly nice
IDE.  Refer to the apple developers connection for more information.


=================================================================================
=====================                                     =======================
=====================      3 - Compiling a Controller     =======================
=====================                                     =======================
=================================================================================


The source code files for your robot controllers should be kept in the source
directory.  The Java class files of your robot controllers need to be kept in the
controllers directory so the simulator and remote client programs can find them.

To compile a controller source code file into a Java class file, use the Windows
compiler script - it's called compile.sh.  When you use this program, it'll compile
your source code file that's in the source directory, and automatically move the
resulting class file to the controllers directory.

For demonstration purposes, there's an uncompiled source code file called

	basic_reactive.Java

located in the source directory.  We're going to go through the steps to compile
it now.

1) Open a console window (by double-clicking on the console.sh icon OR opening a console
   window by whatever means you like)
2) Run compile.sh, giving it the name of the controller (without the .Java extension)
   Here's what you need to type:

            compile.sh basic_reactive

3) That's it!  If Java is properly installed, you'll see a line or two, executing
   the compile command, and then you'll be back at the prompt.




=================================================================================
=====================                                     =======================
=====================       4 - Running the Simulator     =======================
=====================                                     =======================
=================================================================================

To start the Khepera Simulator, you can type the following at the prompt of a
console window (one you opened using console.bat):

              
            java -jar ksim.jar


Or, if you're using Windows, you should be able to double-click the ksim.jar icon.
Either way, wait for a moment, and the simulator program will open.




=================================================================================
=====================                                     =======================
=====================       5 - Running the Remote        =======================
=====================                                     =======================
=================================================================================

To start the Remote Client program, which you'll use to run your controllers on
the real Khepera in our lab (from the comfort of your home, or office or computer
lab), open a console window, and type the following:


           java -jar kremote.jar


Do that now.  You'll see the following lines displayed in your console window:


	-Configuration loaded-
	WSU Khepera Remote Interface
	Press CTRL-C to exit

	Please type the number for the Controller you wish to run
	0 basic_reactive


Go ahead and type

	0

(that's a zero) and press enter.  You'll see:


	Loading basic_reactive...
	Server Unavailable


Normally, you'd see an indication that your controller was running, or a timer 
counting down the time until it was your turn to run.  
	



=================================================================================
=====================                                     =======================
=====================   6 - Remote Client Configuration   =======================
=====================                                     =======================
=================================================================================

The configuration of the remote client (kremote.jar) is controlled by the file
named client.conf, which is located in the same directory as kremote.jar.  (and
everything else in the Khepera Suite.

This file is very important to the proper operation of the remote, and you should
make sure that it's contents are correct before attempting to run the remote.

The file client.conf is a text file, and you can load it in a plain text editor,
like Notepad, to edit it.  The first character on each line of text is very
important - it determines exactly what the function of that line is.  There are 3
different characters you can start a line with - the pound sign (#), an exclamation
point (!), or the 'at' sign (@).  Here's what each means:


Lines starting with # are comment lines.  They are ignored by the remote client, and
exist only to tell people who are reading the contents of client.conf something.
The file begins with a bunch of these comment lines, which explain (basically) how
the file works.

Lines starting with ! will be echoed (that means 'printed out') in the console window
when you run kremote.jar.  There's only one of these lines, at the end of the file
which is:


	!- Configuration loaded -


When you start the remote, look for this line.  It'll be displayed in the console.
You can put other things to be echoed in client.conf.  It doesn't matter what it is
(recipe for chicken soup, a dirty limerick, etc.) Just make sure that EACH LINE
begins with an exclamation point.

Lines starting with @ are the lines that actually tell the program stuff it needs
to know to run.  These lines should contain NO SPACES and be of the form

	@variableName=value

In other words, "something equals something else."  As far as the "somethings" that
you can specify, there are only a few.  Here they are:

	@CONTROLLER_TIMEOUT

	@PATH
	@IP
	@PORT
	@WEBCAM_URL


The item should have this EXACT value:

	@CONTROLLER_TIMEOUT=10

Unless someone more knowledgeable has told you to change these (i.e., a course instructor,
the author of this program), you should make sure that these 4 lines match the above 4 EXACTLY.

We'll go through the next 4 items one-by-one.

	@PATH=./controllers/

The PATH variable sets where the remote will look for your compiled class files of your robot controllers.  You shouldn't change this, because the other programs (like the simulator and the Windows compiler script) use this same directory.


	@IP=foo.bar.edu

This line should be the IP address of the robot you're trying to connect to.  If you are using this program in conjunction with a class, you should verify with your instructor, or the administrator of the live robot that you have the correct address here.

	@PORT=2600

This line is the port number on which the robot resides.  As with the IP address, you should
verify with whoever is administering the robot class/lab/whatever that this number is correct.


Unless otherwise directed by your instructor/lab admin/etc. you should make sure that 
client.conf contains the same values for the TIMEOUT variables as we showed you here.  You should ensure that you have the correct IP and PORT values for your specific situation (ask your instructor/admin/whoever), and you should leave PATH alone.

