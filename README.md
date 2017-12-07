![Logo](s2p.png) S2P [![doi](https://img.shields.io/badge/DOI-10.1016%2Fj.cmpb.2017.11.024-blue.svg)](https://doi.org/10.1016/j.cmpb.2017.11.024) [![license](https://img.shields.io/badge/LICENSE-GPLv3-blue.svg)]() [![Build Status](https://travis-ci.org/sing-group/S2P.svg?branch=master)](https://travis-ci.org/sing-group/S2P)
=================================
S2P is an open source application for fast and easy processing of 2D-gel and MALDI-based mass spectrometry protein data. Please, visit the [official web page](http://www.sing-group.org/s2p) of the project for downloads and support.

Modules
-------
This maven project is comprised of the following modules:
* core: contains the default implementation API.
* gui: contains several GUI components.
* aibench: contains a GUI application based on AIBench framework ([AIBench](http://www.aibench.org/)).

Building S2P
------------
The final application can be built using the `mvn install` command. This command builds the three modules and generates the AIBench based application at `s2p-aibench/targets`.

In order to create the Windows/Linux portable versions and the Windows executable installers, two profiles can be specified: `createDistributables` and `createInstallers`, respectively. Both portable versions and installers are self-contained since they contain an embedded Java Runtime Environment to run the application.

### Building the portable versions
Windows/Linux portable versions can be built using the `mvn install -PcreateDistributables` command. This command generates the following four portable versions of S2P at `s2p-aibench/target/builds`: Linux 32-bit, Linux 64-bit, Windows 32-bit and Windows 64-bit.

### Building the Windows installers
NSIS-based Windows installers can be built using the `mvn install -PcreateInstallers` command. This command generates the 32 and 64-bit versions at `s2p-aibench/target/builds`. Note that this profile is created to run under Linux environments with `mingw-w64` and `wine` installed (it relies on `i686-w64-mingw32-windres` and `i686-w64-mingw32-gcc` to create some application files packed by NSIS).

Team
----
This project is an idea and is developed by:
* Hugo López-Fernández [SING Group](http://www.sing-group.org)
* José Eduardo Araújo [Bioscope Group](http://www.bioscopegroup.org/)
* José Luis Capelo Martínez [Bioscope Group](http://www.bioscopegroup.org/)
* Miguel Reboiro-Jato [SING Group](http://www.sing-group.org)
* Daniel Glez-Peña [SING Group](http://www.sing-group.org)
* Florentino Fdez-Riverola [SING Group](http://www.sing-group.org)

Citing
------
If you are using S2P, please, cite us:
> H. López-Fernández; J.E. Araújo; S. Jorge; D. Glez-Peña; M. Reboiro-Jato; F. Fdez-Riverola; H.M. Santos; J.L. Capelo-Martínez (2018) [S2P: A Software Tool to Quickly Carry Out Reproducible Biomedical Research Projects Involving 2D-Gel and MALDI-TOF MS Protein Data](https://doi.org/10.1016/j.cmpb.2017.11.024). Computer Methods and Programs in Biomedicine. Volume 155, pp. 1-9. ISSN: 0169-2607
>
> H. López-Fernández; J.E. Araújo; D. Glez-Peña; M. Reboiro-Jato; F. Fdez-Riverola; J.L. Capelo-Martínez (2017) [S2P: a desktop application for fast and easy processing of 2D-gel and MALDI-based mass spectrometry protein data](http://dx.doi.org/10.1007/978-3-319-60816-7_1). 11th International Conference on Practical Applications of Computational Biology & Bioinformatics. PACBB 2017. Advances in Intelligent Systems and Computing, vol 616, pp 1-8. Springer, Cham
