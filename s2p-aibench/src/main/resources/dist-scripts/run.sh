#!/bin/bash

java/bin/java -Xmx4G -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar ./lib/aibench-aibench-${aibench.version}.jar
