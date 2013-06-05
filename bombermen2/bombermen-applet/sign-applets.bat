@for /f %%i in ("%0") do @set curpath=%%~dpi 
@cd /d %curpath%

@for /r . %%X in (*.jar) do jarsigner -keystore .keystore -storepass gdxpassword %%X gdxkey