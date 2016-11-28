#!/bin/bash

java/bin/java -Xmx2G -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar ./lib/aibench-aibench-${aibench.version}.jar
