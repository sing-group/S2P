#include <windows.h>

int main()
{
        WinExec("${jre.simplepath}\\bin\\javaw.exe -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel -jar lib\\aibench-aibench-${aibench.version}.jar", SW_HIDE);
        return 0;
}
