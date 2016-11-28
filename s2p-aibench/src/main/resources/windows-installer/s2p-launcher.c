#include <windows.h>

int main()
{
        WinExec("${jre.simplepath}\\bin\\javaw.exe  -Xmx4G -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar lib\\aibench-aibench-${aibench.version}.jar", SW_HIDE);
        return 0;
}
