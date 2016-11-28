![Logo](s2p.png) S2P [![license](https://img.shields.io/badge/LICENSE-GPLv3-blue.svg)]()
=================================
S2P is an open source application for fast and easy processing of 2D-gel and MALDI-based mass spectrometry protein data. Please, visit the [official web page](http://sing.ei.uvigo.es/s2p) of the project for downloads and support.

Modules
-------
This maven project is comprised of the following modules:
* core: contains the default implementation API.
* gui: contains several GUI components.
* aibench: contains a GUI application based on AIBench framework ([AIBench](http://www.aibench.org/)).

Building S2P
----
The final application can be built using the `mvn install` command. This command builds the three modules and generates the AIBench based application at `s2p-aibench/target`.

In order to create the Windows/Linux portable versions and the Windows executable installers, two profiles can be specified: `createDistributables` and `createInstallers`, respectively. Both portable versions and installers are self-contained since they contain an embedded Java Runtime Environment to run the application.

### Building the portable versions
Windows/Linux portable versions can be built using the `mvn install -PcreateDistributables` command. This command generates the following four portable versions of S2P at `s2p-aibench/target`: Linux 32-bit, Linux 64-bit, Windows 32-bit and Windows 64-bit.

### Building the Windows installers
NSIS-based Windows installers can be built using the `mvn install -PcreateInstallers` command. This command generates the 32 and 64-bit versions at `s2p-aibench/target/dist32` and `s2p-aibench/target/dist`, respectively. Note that this profile is created to run under Linux environments with `mingw-w64` and `wine` installed (it relies on `i686-w64-mingw32-windres` and `i686-w64-mingw32-gcc` to create some application files packed by NSIS).

Team
----
This project is an idea and is developed by:
* Hugo López-Fernández [SING Group](http://sing.ei.uvigo.es)
* José Eduardo Araújo [Bioscope Group](http://www.bioscopegroup.org/)
* José Luis Capelo Martínez [Bioscope Group](http://www.bioscopegroup.org/)
* Miguel Reboiro-Jato [SING Group](http://sing.ei.uvigo.es)
* Daniel Glez-Peña [SING Group](http://sing.ei.uvigo.es)
* Florentino Fdez-Riverola [SING Group](http://sing.ei.uvigo.es)