@echo off
start java\bin\javaw -Xmx4G -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar lib\aibench-aibench-${aibench.version}.jar
