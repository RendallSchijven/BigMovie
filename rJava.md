1. Start RStudio and execute install.packages("rJava")

2. System variables:

    Create a R_HOME system variable with value C:\Program Files\R\R-3.4.2 (your R install folder)

    Add the following to PATH variable:
    
    C:\Program Files\R\R-3.4.2\bin\i386;C:\Users\USERNAME\Documents\R\win-library\3.4\rJava\jri

3. IntelliJ:

    3.1
        Right click on project folder and press F4. Go to modules > dependencies
        
        Add the JRI.jar JRIEngine.jar and REngine.jar 
        (Located in C:\Users\USERNAME\Documents\R\win-library\3.1\rJava\jri)
        as dependencies.
     
    3.2
        Set the VM options (located in Run/debug configurations (left of the run button))
        to -Djava.library.path=C:\Users\USERNAME\Documents\R\win-library\3.1\rJava\jri\i386
        
4. Start RStudio and execute library(rJava)
